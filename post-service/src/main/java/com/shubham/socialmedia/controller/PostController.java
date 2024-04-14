package com.shubham.socialmedia.controller;

import com.shubham.socialmedia.entity.Post;
import com.shubham.socialmedia.model.PostDto;
import com.shubham.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;
    @PostMapping("/savePost")
    public boolean savePost(@RequestBody Post post){
        return  postService.save(post);
    }

    @PutMapping("/post/update")
    public boolean updatePost(@RequestBody Post post){
        boolean status = postService.updatePost(post);
        return status;
    }

    @GetMapping("/post/{postid}")
    public PostDto getPost(@PathVariable("postid") long postId){
        try {
            PostDto postDto = postService.getPostByPostId(postId);
            return postDto;
        }catch (Exception e){
            return null;
        }
    }


}
