package com.capstone.jejutoon.customizedFolktale.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChoiceForRedis {

    private Long id;
    private String prompt;
}
