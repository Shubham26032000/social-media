package com.shubham.socialmedia.service;

import com.shubham.socialmedia.entity.User;
import com.shubham.socialmedia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    public boolean saveUser(User user){
        try{
            userRepository.save(user);
            logger.info("User saved..!");
        }catch (Exception e){
            logger.info(e.getMessage());
            return false;
        }
        return true;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public List<User> getAllUserById(List<Long> userIds) {

        List<User> users = userIds.stream().map(userId -> userRepository.findById(userId).orElseThrow()).collect(Collectors.toList());
        return users;
    }

    public boolean updateUser(User user) {
        User user1 = userRepository.findById(user.getUserId()).orElseThrow();
        user1.setEmail(user.getEmail());
        user1.setProfilePicUrl(user.getProfilePicUrl());
        user1.setUsername(user.getUsername());
        userRepository.save(user1);
        return true;
    }
}
