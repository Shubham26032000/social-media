package com.shubham.socialmedia.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FollowerFollowingDto {
    private long userId;
    private long friendId;
}
