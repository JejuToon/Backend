package com.capstone.jejutoon.folktale.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choiceId")
    private Long id;

    private String answer;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 2048)
    private String imageUrl;

    private String prompt;

    private Long nextFolktaleDetailId;

    @JoinColumn(name = "folktaleDetailId")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FolktaleDetail folktaleDetail;
}
