package com.capstone.jejutoon.customizedFolktale.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomizedDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customizedDetailId")
    Long id;

    @Lob
    @Column(columnDefinition = "TEXT")
    String content;

    @Column(length = 2048)
    String imageUrl;

    @JoinColumn(name = "memberFolktaleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    MemberFolktale memberFolktale;
}
