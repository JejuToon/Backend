package com.capstone.jejutoon.folktale.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Folktale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folktaleId")
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(length = 2048)
    private String summary;

    @Column(length = 2048)
    private String description;

    @Column(length = 2048)
    private String prompt;

    @Column(length = 2048)
    private String thumbnail;
}
