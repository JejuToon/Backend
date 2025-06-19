package com.capstone.jejutoon.customizedFolktale.converter;

import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.customizedFolktale.dto.response.CreatedMemberFolktaleDto;
import com.capstone.jejutoon.customizedFolktale.dto.response.MyFolktaleDto;
import com.capstone.jejutoon.folktale.domain.Folktale;
import com.capstone.jejutoon.member.domain.Member;

public class MemberFolktaleConverter {

    private static final String DEFAULT_IMAGE = "https://jejutoon-storage.s3.ap-northeast-2.amazonaws.com/loading.png";

    public static MemberFolktale toMemberFolktaleEntity(
            Folktale folktale, Member member
    ) {
        return MemberFolktale.builder()
                .folktale(folktale)
                .member(member)
                .characterImageUrl(DEFAULT_IMAGE)
                .score(null)
                .build();
    }

    public static MyFolktaleDto toMyFolktaleDto(
            MemberFolktale memberFolktale
    ) {
        return MyFolktaleDto.builder()
                .folktaleId(memberFolktale.getFolktale().getId())
                .memberFolktaleId(memberFolktale.getId())
                .title(memberFolktale.getFolktale().getTitle())
                .summary(memberFolktale.getFolktale().getSummary())
                .characterImageUrl(memberFolktale.getCharacterImageUrl())
                .score(memberFolktale.getScore() == null ? 0 : memberFolktale.getScore())
                .build();
    }

    public static CreatedMemberFolktaleDto toCreatedMemberFolktaleDto(
            MemberFolktale memberFolktale
    ) {
        return CreatedMemberFolktaleDto.builder()
                .id(memberFolktale.getId())
                .build();
    }
}
