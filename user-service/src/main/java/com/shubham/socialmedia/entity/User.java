package com.shubham.socialmedia.entity;

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
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
