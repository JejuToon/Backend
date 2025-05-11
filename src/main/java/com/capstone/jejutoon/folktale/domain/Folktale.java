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
    Long id;

    @Column(nullable = false, length = 30)
    String title;
}
