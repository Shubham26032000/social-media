package com.shubham.socialmedia.repository;

import com.shubham.socialmedia.entity.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendShipRepository extends JpaRepository<FriendShip,Long> {

    FriendShip findByUserIdAndFollowerId(Long userId, Long followerId);

    @Query("SELECT f.followerId FROM FriendShip f WHERE f.userId = :userId")
    List<Long> findFollowerIdByUserId(@Param("userId") Long userId);

    @Query("SELECT f.userId FROM FriendShip f WHERE f.followerId = :followerId")
    List<Long> findUserIdByFollowerId(@Param("followerId") Long userId);
}
