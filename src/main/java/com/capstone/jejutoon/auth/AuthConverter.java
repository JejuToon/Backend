package com.capstone.jejutoon.auth;

import com.capstone.jejutoon.auth.dto.response.LoginResponse;
import com.capstone.jejutoon.config.auth.AuthToken;
import com.capstone.jejutoon.config.auth.AuthTokenGenerator;
import com.capstone.jejutoon.member.domain.Member;

public class AuthConverter {

    public static LoginResponse toLoginRespose(Member member, AuthToken token) {
        return LoginResponse.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .name(member.getName())
                .profileImageUrl(member.getImageUrl())
                .token(token)
                .build();
    }
}
