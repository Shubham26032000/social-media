package com.shubham.socialmedia.model;

import com.shubham.socialmedia.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentModel {
    private Comment comment;
    private User user;
}
