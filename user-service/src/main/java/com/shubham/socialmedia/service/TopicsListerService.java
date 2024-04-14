package com.shubham.socialmedia.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubham.socialmedia.entity.User;
import com.shubham.socialmedia.model.FollowerFollowingDto;
import com.shubham.socialmedia.model.FollowersFollowingList;
import com.shubham.socialmedia.model.PostDto;
import com.shubham.socialmedia.repository.UserRepository;
import org.apache.catalina.valves.AbstractAccessLogValve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicsListerService {

    private static final Logger logger = LoggerFactory.getLogger(TopicsListerService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String,User> userKafkaTemplate;
    @KafkaListener(topics = "update-follower-following",groupId = "friendship-group")
    public void updateFollowerFollowing(String event){
        logger.info("Into update-follower-following : {}",TopicsListerService.class.getName());
        try {
            FollowerFollowingDto followerFollowingDto = new ObjectMapper().readValue(event,FollowerFollowingDto.class);
            Optional<User> userOptional = userRepository.findById(followerFollowingDto.getUserId());
            Optional<User> friendOption = userRepository.findById(followerFollowingDto.getFriendId());

            userOptional.ifPresent(user -> {
                user.setFollowers(user.getFollowers()+1);
                userRepository.save(user);
            });

            friendOption.ifPresent(user ->{
                user.setFollowing(user.getFollowing()+1);
                userRepository.save(user);
            });



        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "make-post",groupId = "post-group")
    public void listenPostTopic(String event){
        try {
            PostDto postDto = new ObjectMapper().readValue(event,PostDto.class);
            User user = userRepository.findById(postDto.getUserId()).orElseThrow();
            if (postDto.isSuccess())
                user.setPosts(user.getPosts()+1);
            else
                user.setPosts(user.getPosts()-1);
            userRepository.save(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
