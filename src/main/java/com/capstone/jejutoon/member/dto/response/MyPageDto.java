package com.capstone.jejutoon.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyPageDto {

    private Long id;
    private String name;
    private String imageUrl;
}
