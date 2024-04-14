package com.shubham.socialmedia.controller;

import com.shubham.socialmedia.entity.LikeEntity;
import com.shubham.socialmedia.model.LikesDtoResponse;
import com.shubham.socialmedia.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class LikeController {


    @Autowired
    private LikeService likeService;

    @PostMapping("/like/")
    public boolean likeUnLike(@RequestBody LikeEntity likeEntity){
        boolean status = likeService.saveLike(likeEntity);
        return status;
    }
    @PutMapping("/like/updateLike/{postId}")
    public boolean likeUnLike(@PathVariable long postId,@RequestParam long userId){
        boolean status = likeService.updateLike(postId,userId);
        return status;
    }
    @GetMapping("/like/{postId}")
    public LikesDtoResponse getLikesOfPost(@PathVariable Long postId){
        LikesDtoResponse likesDtoResponse = likeService.getLikesOfPosts(postId);
        return likesDtoResponse;
    }
}
