package com.shubham.socialmedia.controller;

import com.shubham.socialmedia.entity.User;
import com.shubham.socialmedia.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.LabelUI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public boolean createUser(@RequestBody User user){
        logger.info("User Data : {}",user);
        boolean result = userService.saveUser(user);
        logger.info("is user saved ? {}",result);
        return result;
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable Long userId, HttpServletRequest httpServletRequest){
        logger.info("httpServletRequest.UserEmail :: {}",httpServletRequest.getHeader("X-User-Email"));
        User user = userService.findUserById(userId);

        return  user;
    }

    @PutMapping("/user/update")
    public boolean updateUser(@RequestBody User user)
    {
        boolean status = userService.updateUser(user);
        return status;
    }
    @GetMapping("/user/allUser")
    public List<User> getAllUser(@RequestParam List<Long> userIds){
        List<User> users = userService.getAllUserById(userIds);

        return users;
    }


}
