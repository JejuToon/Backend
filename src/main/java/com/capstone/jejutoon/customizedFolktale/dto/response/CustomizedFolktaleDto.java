package com.capstone.jejutoon.customizedFolktale.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomizedFolktaleDto {

    private Long id;
    private String content;
    private String imageUrl;
}
