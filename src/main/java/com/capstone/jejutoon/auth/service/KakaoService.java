package com.capstone.jejutoon.auth.service;

import com.capstone.jejutoon.auth.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface KakaoService {
    LoginResponse kakaoLogin(String authorizationCode, HttpServletRequest httpRequest);
}
