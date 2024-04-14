package com.shubham.socialmedia.controller;

import com.shubham.socialmedia.entity.User;
import com.shubham.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.LabelUI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public boolean createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable Long userId){
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
