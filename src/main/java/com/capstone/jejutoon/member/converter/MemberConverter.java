package com.capstone.jejutoon.member.converter;

import com.capstone.jejutoon.auth.service.KakaoServiceImpl.KakaoUser;
import com.capstone.jejutoon.auth.service.KakaoServiceImpl.KakaoUser.KakaoAccount.Profile;
import com.capstone.jejutoon.member.domain.Member;
import com.capstone.jejutoon.member.dto.response.MyPageDto;

public class MemberConverter {

    public static Member toEntity(KakaoUser kakaoUser) {
        Profile profile = kakaoUser.getKakaoAccount().getProfile();

        return Member.builder()
                .name(profile.getNickname())
                .loginId("kakao_" + kakaoUser.getId())
                .imageUrl(profile.getProfileImageUrl())
                .build();
    }

    public static MyPageDto toMyPageDto(Member member) {
        return MyPageDto.builder()
                .id(member.getId())
                .name(member.getName())
                .imageUrl(member.getImageUrl())
                .build();
    }
}
