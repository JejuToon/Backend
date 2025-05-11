package com.capstone.jejutoon.member.converter;

import com.capstone.jejutoon.auth.service.KakaoServiceImpl.KakaoUser;
import com.capstone.jejutoon.auth.service.KakaoServiceImpl.KakaoUser.KakaoAccount.Profile;
import com.capstone.jejutoon.member.domain.Member;

public class MemberConverter {

    public static Member toEntity(KakaoUser kakaoUser) {
        Profile profile = kakaoUser.getKakaoAccount().getProfile();

        return Member.builder()
                .name(profile.getNickname())
                .loginId("kakao_" + kakaoUser.getId())
                .imageUrl(profile.getProfileImageUrl())
                .build();
    }
}
