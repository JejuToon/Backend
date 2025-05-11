package com.capstone.jejutoon.auth.controller;

import com.capstone.jejutoon.auth.dto.response.LoginResponse;
import com.capstone.jejutoon.auth.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;

    @PostMapping("/kakao")
    public LoginResponse kakaoLogin(
            @RequestParam("authorizationCode") String authorizationCode,
            HttpServletRequest httpRequest
    ) {
        return kakaoService.kakaoLogin(authorizationCode, httpRequest);
    }
}
