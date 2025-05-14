package com.capstone.jejutoon.folktale.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FolktaleScenarioDto {

    private Long id;
    private String content;
    private String imageUrl;
    private String question;
    private List<String> choices;
}
