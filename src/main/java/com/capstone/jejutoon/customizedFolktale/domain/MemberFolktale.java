package com.capstone.jejutoon.customizedFolktale.domain;

import com.capstone.jejutoon.folktale.domain.Folktale;
import com.capstone.jejutoon.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberFolktale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberFolktaleId")
    Long id;

    @JoinColumn(name = "folktaleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Folktale folktale;

    @JoinColumn(name = "memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Member member;

    @Column(nullable = false, length = 2048)
    String characterImageUrl;

    Integer score;
}
