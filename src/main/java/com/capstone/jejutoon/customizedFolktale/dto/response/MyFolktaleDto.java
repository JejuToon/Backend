package com.capstone.jejutoon.customizedFolktale.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyFolktaleDto {

    private Long id;
    private String title;
    private String summary;
    private String characterImageUrl;
}
