package com.shubham.socialmedia.controller;

import com.shubham.socialmedia.entity.FriendShip;
import com.shubham.socialmedia.model.User;
import com.shubham.socialmedia.service.FriendShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FriendShipController {

    @Autowired
    private FriendShipService friendShipService;

    @PostMapping("/createFriendship")
    public boolean createFriendship(@RequestBody FriendShip friendShip){
        return  friendShipService.createFrienship(friendShip);
    }

    @PostMapping("/updateFriendship")
    public boolean updateFriendship(@RequestParam("userId") Long userId,@RequestParam("friendId") Long friendId, @RequestParam("status") boolean status){
        return  friendShipService.updateFriendshipStatus(userId,friendId,status);
    }

    @GetMapping("/followers")
    public List<User> getFollowers(@RequestParam("userId") Long userId){
        List<User> followersList = friendShipService.getFollowers(userId);

        return followersList;
    }

    @GetMapping("/followings")
    public List<User> getFollowings(@RequestParam("userId") Long userId){
        List<User> followersList = friendShipService.getFollowings(userId);

        return followersList;
    }
}
