package com.capstone.jejutoon.auth.service;

import com.capstone.jejutoon.auth.AuthConverter;
import com.capstone.jejutoon.auth.dto.response.KakaoOAuthTokenResponse;
import com.capstone.jejutoon.auth.dto.response.LoginResponse;
import com.capstone.jejutoon.config.auth.AuthToken;
import com.capstone.jejutoon.config.auth.AuthTokenGenerator;
import com.capstone.jejutoon.member.converter.MemberConverter;
import com.capstone.jejutoon.member.domain.Member;
import com.capstone.jejutoon.member.repository.MemberRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class KakaoServiceImpl implements KakaoService {

    private static final String provider = "kakao_";

    @Value("${oauth.kakao.rest-api-key}")
    private String restApiKey;

    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.kakao.client-secret}")
    private String clientSecret;

    private final MemberRepository memberRepository;
    private final AuthTokenGenerator authTokenGenerator;

    @Override
    public LoginResponse kakaoLogin(String authorizationCode, HttpServletRequest httpRequest) {
        String redirectUri = getRedirectUri(httpRequest);
        String accessToken = getAccessToken(authorizationCode, redirectUri);

        return login(accessToken);
    }

    private String getRedirectUri(HttpServletRequest httpRequest) {
        String origin = httpRequest.getHeader("Origin");

        if (origin.equals("http://localhost:5173")) {
            return "http://localhost:5173/oauth/kakao";
        } else {
            return redirectUri;
        }
    }

    private String getAccessToken(String authorizationCode, String redirectUri) {
        WebClient webClient = WebClient.builder().build();

        KakaoOAuthTokenResponse tokenResponse = webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", restApiKey)
                        .with("redirect_uri", redirectUri)
                        .with("code", authorizationCode)
                        .with("client_secret", clientSecret))
                .retrieve()
                .bodyToMono(KakaoOAuthTokenResponse.class)
                .block();

        return tokenResponse.getAccessToken();
    }

    private LoginResponse login(String accessToken) {
        KakaoUser kakaoUser = WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUser.class)
                .block();

        Member member = memberRepository.findByLoginId("kakao_" + kakaoUser.getId())
                .orElseGet(() -> {
                    Member newMember = MemberConverter.toEntity(kakaoUser);
                    return memberRepository.save(newMember);
                });

        AuthToken token = authTokenGenerator.generate(member.getLoginId());
        return AuthConverter.toLoginRespose(member, token);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoUser {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount;

        @Getter
        @AllArgsConstructor
        public static class KakaoAccount {
            @JsonProperty("profile")
            private Profile profile;

            @Getter
            @AllArgsConstructor
            public static class Profile {
                @JsonProperty("nickname")
                private String nickname;

                @JsonProperty("profile_image_url")
                private String profileImageUrl;
            }
        }
    }
}
