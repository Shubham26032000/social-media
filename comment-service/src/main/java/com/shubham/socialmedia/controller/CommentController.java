package com.shubham.socialmedia.controller;

import com.shubham.socialmedia.entity.Comment;
import com.shubham.socialmedia.model.CommentDto;
import com.shubham.socialmedia.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public boolean comment(@RequestBody Comment comment){
        return commentService.saveComment(comment);
    }

    @GetMapping("/comment/{postId}")
    public CommentDto getPostComments(@PathVariable long postId){
        try {
            CommentDto commentDto = commentService.getCommentsOfPost(postId);
            return commentDto;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
