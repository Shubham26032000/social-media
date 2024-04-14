package com.shubham.socialmedia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    private Long postId;

    private Long userId;

    private String imageUrl;
    private Long timeStamp;
    //stores userid
    private Long likes;

    //stored comment id
    private Long comments;

}
