package com.shubham.socialmedia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
