package com.shubham.socialmedia.model;

import com.shubham.socialmedia.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private List<CommentModel> commentList;
    private PostDto post;
}
