package com.shubham.socialmedia.repository;

import com.shubham.socialmedia.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity,Long> {
    LikeEntity findByPostIdAndUserId(long postId, long userId);

    List<LikeEntity> findByPostId(Long postId);
}
