package com.shubham.socialmedia.model;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
