package com.shubham.socialmedia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long userId;
    private String username;
    //   private String firstName;
//   private String lastName;
    private String email;
    private String profilePicUrl;
    private long followers;
    private long following;
    private long posts;

}
