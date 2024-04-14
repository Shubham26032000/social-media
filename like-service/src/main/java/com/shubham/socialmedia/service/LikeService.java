package com.shubham.socialmedia.service;

import com.shubham.socialmedia.entity.LikeEntity;
import com.shubham.socialmedia.helper.PostClient;
import com.shubham.socialmedia.helper.UserClient;
import com.shubham.socialmedia.model.LikeDto;
import com.shubham.socialmedia.model.LikesDtoResponse;
import com.shubham.socialmedia.model.PostDto;
import com.shubham.socialmedia.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private KafkaTemplate<String,Long> postKafkaTemplate;

    @Autowired
    private PostClient postClient;

    @Autowired
    private UserClient userClient;

    public boolean saveLike(LikeEntity likeEntity) {
        LikeEntity likeEntity1 = likeRepository.save(likeEntity);

        boolean status = likeEntity1 != null;
        if (status)
            postKafkaTemplate.send("like-add", likeEntity.getPostId());
        return status;

    }

    public boolean updateLike(long postId, long userId) {
        LikeEntity likeEntity = likeRepository.findByPostIdAndUserId(postId,userId);

        if (likeEntity == null)
            return false;
        if (likeEntity.getUserId() == userId)
            likeRepository.delete(likeEntity);
        postKafkaTemplate.send("like-remove",postId);
        return true;
    }

    public LikesDtoResponse getLikesOfPosts(Long postId) {
        PostDto post = postClient.getPost(postId);
        List<LikeEntity> likeEntityList = likeRepository.findByPostId(postId);
        List<LikeDto> likeDtoList = likeEntityList.stream().map(likeEntity -> LikeDto.builder().user(userClient.getUser(likeEntity.getUserId()))
                .likeEntity(likeEntity)
                .build()).collect(Collectors.toList());
        return LikesDtoResponse.builder()
                .likes(likeDtoList)
                .likes(likeDtoList)
                .post(post)
                .build();

    }
}
