package com.shubham.socialmedia.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FriendShip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long friendShipId;

    private Long userId;

    private Long followerId;

    private String status;

}
