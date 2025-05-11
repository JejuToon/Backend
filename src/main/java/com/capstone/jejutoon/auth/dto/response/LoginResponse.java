package com.capstone.jejutoon.auth.dto.response;

import com.capstone.jejutoon.config.auth.AuthToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String loginId;
    private String name;
    private String profileImageUrl;
    private AuthToken token;
}
