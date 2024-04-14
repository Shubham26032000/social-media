package com.shubham.socialmedia.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubham.socialmedia.entity.FriendShip;
import com.shubham.socialmedia.helper.UserService;
import com.shubham.socialmedia.model.FollowerFollowingDto;
import com.shubham.socialmedia.model.FollowersFollowingList;
import com.shubham.socialmedia.model.User;
import com.shubham.socialmedia.repository.FriendShipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FriendShipService {

    private final static Logger logger = LoggerFactory.getLogger(FriendShipService.class);

    @Autowired
    private FriendShipRepository friendShipRepository;

    @Autowired
    private KafkaTemplate<String, FollowerFollowingDto> userKafkaTemple;

    private List<User> usersFollowersList;

    @Autowired
    private KafkaTemplate<String,List<Long>> followerKafkaTemplate;

    @Autowired
    private UserService userService;

    public boolean createFrienship(FriendShip friendShip) {
        try {
            friendShip.setStatus("PENDING");
            friendShipRepository.save(friendShip);
            logger.info("FriendShip is created...!");
        }catch (Exception e){
            logger.info("Error while creating friendship {}",e.getMessage());
            return false;
        }

        return true;
    }

    public boolean updateFriendshipStatus(Long userId, Long friendId, boolean status) {
        logger.info("INSIDE UPDATE FRIENDSHIP STATUS");
        FriendShip friendShip = friendShipRepository.findByUserIdAndFollowerId(userId,friendId);
        if (friendShip != null && friendShip.getFriendShipId() != null){
            if(status){
                try {
                    friendShip.setStatus("ACCEPTED");
                    updateUserFollowerAndFollowingCound(userId, friendId);
                }catch (Exception e){
                    logger.error(e.getMessage());
                    return false;
                }
            }else {
                friendShip.setStatus("REJECTED");
            }
            friendShipRepository.save(friendShip);
            return true;
        }else {
            return false;
        }
    }

    private void updateUserFollowerAndFollowingCound(Long userId, Long friendId) {
        FollowerFollowingDto followerFollowingDto = FollowerFollowingDto.builder()
                .userId(userId)
                .friendId(friendId)
                .build();
        logger.info("SENDING TOPIC");
        userKafkaTemple.send("update-follower-following",followerFollowingDto);
    }

    public List<User> getFollowers(Long userId) {
        List<Long> followersIds = friendShipRepository.findFollowerIdByUserId(userId);
        List<User> userList = userService.getUsers(followersIds);
        return userList;
    }

    public List<User> getFollowings(Long followerId) {
        List<Long> followersIds = friendShipRepository.findUserIdByFollowerId(followerId);
        List<User> userList = userService.getUsers(followersIds);
        return userList;
    }

}
