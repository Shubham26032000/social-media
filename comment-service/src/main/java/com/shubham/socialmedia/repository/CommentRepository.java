package com.shubham.socialmedia.repository;

import com.shubham.socialmedia.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long postId);
}
