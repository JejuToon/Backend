package com.capstone.jejutoon.customizedFolktale.converter;

import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.customizedFolktale.dto.response.MyFolktaleDto;
import com.capstone.jejutoon.folktale.domain.Folktale;
import com.capstone.jejutoon.member.domain.Member;

public class MemberFolktaleConverter {

    public static MemberFolktale toMemberFolktaleEntity(
            Folktale folktale, Member member, String imageUrl
    ) {
        return MemberFolktale.builder()
                .folktale(folktale)
                .member(member)
                .characterImageUrl(imageUrl)
                .score(null)
                .build();
    }

    public static MyFolktaleDto toMyFolktaleDto(
            MemberFolktale memberFolktale
    ) {
        return MyFolktaleDto.builder()
                .id(memberFolktale.getId())
                .title(memberFolktale.getFolktale().getTitle())
                .summary(memberFolktale.getFolktale().getSummary())
                .characterImageUrl(memberFolktale.getCharacterImageUrl())
                .build();
    }
}
