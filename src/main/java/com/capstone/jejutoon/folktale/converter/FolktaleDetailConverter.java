package com.capstone.jejutoon.folktale.converter;

import com.capstone.jejutoon.folktale.domain.FolktaleDetail;
import com.capstone.jejutoon.folktale.dto.response.FolktaleScenarioDto;

import java.util.List;

public class FolktaleDetailConverter {

    public static FolktaleScenarioDto toFolktaleRealDetailDto(
            FolktaleDetail folktaleDetail, String question, List<String> choices
    ) {
        return FolktaleScenarioDto.builder()
                .id(folktaleDetail.getId())
                .content(folktaleDetail.getContent())
                .imageUrl(folktaleDetail.getImageUrl())
                .question(question)
                .choices(choices)
                .build();
    }
}
