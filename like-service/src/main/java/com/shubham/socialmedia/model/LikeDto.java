package com.shubham.socialmedia.model;

import com.shubham.socialmedia.entity.LikeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeDto {
    private LikeEntity likeEntity;
    private User user;

}
