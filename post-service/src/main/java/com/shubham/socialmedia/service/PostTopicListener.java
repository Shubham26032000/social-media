package com.shubham.socialmedia.service;

import com.shubham.socialmedia.entity.Post;
import com.shubham.socialmedia.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PostTopicListener {

    @Autowired
    private PostRepository postRepository;

    @KafkaListener(topics = "update-comment-post",groupId = "comment-group")
    public void updateCommentListener(Long postId){
        Post post  = postRepository.findById(postId).orElseThrow();
        if (post.getComments() == null)
            post.setComments(1L);
        else
            post.setComments(post.getComments()+1);
        postRepository.save(post);
    }

    @KafkaListener(topics = "like-add",groupId = "like-group")
    public void addLike(Long postId){
        Post post = postRepository.findById(postId).orElseThrow();
        if (post.getLikes() == null)
            post.setLikes(1L);
        else
            post.setLikes(post.getLikes()+1);
        postRepository.save(post);
    }

    @KafkaListener(topics = "like-remove",groupId = "like-group")
    public void removePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow();
            post.setLikes(post.getLikes()-1);
        postRepository.save(post);
    }
}
