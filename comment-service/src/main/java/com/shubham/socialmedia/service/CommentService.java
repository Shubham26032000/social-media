package com.shubham.socialmedia.service;

import com.shubham.socialmedia.entity.Comment;
import com.shubham.socialmedia.helper.PostClient;
import com.shubham.socialmedia.helper.UserClient;
import com.shubham.socialmedia.model.CommentDto;
import com.shubham.socialmedia.model.CommentModel;
import com.shubham.socialmedia.model.PostDto;
import com.shubham.socialmedia.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostClient postClient;

    @Autowired
    private UserClient userClient;
    @Autowired
    private KafkaTemplate<String,Long> postKafkaTemplate;
    public boolean saveComment(Comment comment) {
        try {
            commentRepository.save(comment);
            System.out.println("SEDNING TOPIC");
            postKafkaTemplate.send("update-comment-post",comment.getPostId());
        }catch (Exception e){
            System.out.println("ERROR");
            logger.info("Error while saveing comment : {}",e.getMessage());
            return false;
        }

        return true;
    }

    public CommentDto getCommentsOfPost(long postId) {
        PostDto postDto = postClient.getPost(postId);
        if(postDto == null)
            throw new RuntimeException("Post not found");
        List<Comment> commentList = commentRepository.findByPostId(postId);
        List<CommentModel> commentModelList = commentList.stream().map(comment -> CommentModel.builder()
                .comment(comment)
                .user(userClient.getUser(comment.getUserId()))
                .build()).collect(Collectors.toList());

        CommentDto commentDto = CommentDto.builder()
                .post(postDto)
                .commentList(commentModelList)
                .build();
        return commentDto;

    }
}
