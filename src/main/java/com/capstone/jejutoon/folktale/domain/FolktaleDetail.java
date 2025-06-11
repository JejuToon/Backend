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
public class FolktaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folktaleDetailId")
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 2048)
    private String imageUrl;

    @JoinColumn(name = "folktaleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Folktale folktale;
}
