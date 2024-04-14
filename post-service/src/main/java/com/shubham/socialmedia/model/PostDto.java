package com.shubham.socialmedia.model;

import com.shubham.socialmedia.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Post post;

    private UserDto user;
}
