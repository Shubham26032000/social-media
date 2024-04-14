package com.shubham.socialmedia.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubham.socialmedia.entity.Post;
import com.shubham.socialmedia.helper.UserService;
import com.shubham.socialmedia.model.PostDto;
import com.shubham.socialmedia.model.PostUserDto;
import com.shubham.socialmedia.model.UserDto;
import com.shubham.socialmedia.repository.PostRepository;
import org.hibernate.event.spi.PostDeleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private KafkaTemplate<String, PostUserDto> updatePostCountKafkaTemplate;

    @Autowired
    private UserService userClient;

    public boolean save(Post post) {
        try {
            postRepository.save(post);
            logger.info("Post saved ..!");
            PostUserDto postUserDto = PostUserDto.builder()
                            .userId(post.getUserId())
                                    .success(true)
                                            .build();
            updatePostCountKafkaTemplate.send("make-post",postUserDto);

        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deletePost(long postId){
        try{
            Post post = postRepository.findById(postId).get();
            postRepository.deleteById(postId);
            logger.info("Post has been deleted..!");
            PostUserDto postUserDto = PostUserDto.builder()
                    .userId(post.getUserId())
                    .success(false)
                    .build();
            updatePostCountKafkaTemplate.send("make-post",postUserDto);
        }catch (Exception e){
            logger.info(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updatePost(Post post){
        try{
            Post postToUpdate = postRepository.findById(post.getPostId()).orElseThrow();
            if (post.getImageUrl() != null && !post.getImageUrl().isEmpty())
                postToUpdate.setImageUrl(post.getImageUrl());
            postRepository.save(postToUpdate);
            return true;
        }catch (Exception e){
            logger.info(e.getMessage());
            return false;
        }
    }


    public PostDto getPostByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        UserDto userDto = userClient.getUser(post.getUserId());
        PostDto postDto = PostDto.builder()
                .post(post)
                .user(userDto)
                .build();
        return postDto;
    }
}
