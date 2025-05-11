package com.capstone.jejutoon.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    Long id;

    @Column(unique = true)
    String loginId;
    String name;

    @Column(length = 2048)
    String imageUrl;
}
