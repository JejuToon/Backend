package com.capstone.jejutoon.customizedFolktale.converter;

import com.capstone.jejutoon.customizedFolktale.domain.CustomizedDetail;
import com.capstone.jejutoon.customizedFolktale.dto.response.ChoiceForRedis;
import com.capstone.jejutoon.customizedFolktale.dto.response.CustomizedFolktaleDto;
import com.capstone.jejutoon.folktale.domain.Choice;

public class CustomizedFolktaleConverter {

    public static CustomizedFolktaleDto toCustomizedFolktaleDto(
            CustomizedDetail customizedDetail
    ) {
        return CustomizedFolktaleDto.builder()
                .id(customizedDetail.getId())
                .content(customizedDetail.getContent())
                .imageUrl(customizedDetail.getImageUrl())
                .build();
    }

    public static ChoiceForRedis toChoiceForRedis(
            Choice choice
    ) {
        return ChoiceForRedis.builder()
                .id(choice.getId())
                .prompt(choice.getPrompt())
                .build();
    }
}
