package com.capstone.jejutoon.config.auth;

import com.capstone.jejutoon.common.service.RedisService;
import com.capstone.jejutoon.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokenGenerator {

    private static final String BEARER_TYPE = "Bearer";

    // access token: 14일, refresh token: 14일
    // TODO: access token 유효 기간 줄여야 함
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public AuthToken generate(String loginId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String accessToken = jwtProvider.accessTokenGenerate(loginId, accessTokenExpiredAt);
        String refreshToken = jwtProvider.refreshTokenGenerate(refreshTokenExpiredAt);
        redisService.saveRefreshToken(loginId, refreshToken, REFRESH_TOKEN_EXPIRE_TIME);

        return AuthToken.of(BEARER_TYPE, accessToken, refreshToken);
    }
}
