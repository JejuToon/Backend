package com.capstone.jejutoon.folktale.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChoiceScenarioDto {

    private Long id;
    private String content;
    private String imageUrl;
}
