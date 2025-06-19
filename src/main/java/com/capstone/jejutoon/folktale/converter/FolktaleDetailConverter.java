package com.capstone.jejutoon.folktale.converter;

import com.capstone.jejutoon.customizedFolktale.domain.CustomizedDetail;
import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.folktale.domain.Choice;
import com.capstone.jejutoon.folktale.domain.FolktaleDetail;
import com.capstone.jejutoon.folktale.dto.response.ChoiceDto;
import com.capstone.jejutoon.folktale.dto.response.ChoiceScenarioDto;
import com.capstone.jejutoon.folktale.dto.response.FolktaleDetailDto;

import java.util.List;

public class FolktaleDetailConverter {

    public static FolktaleDetailDto toFolktaleDetailDto(
            FolktaleDetail folktaleDetail, List<Choice> choices, String voiceUrl
    ) {
        List<ChoiceDto> choiceDtoList = choices.isEmpty()
                ? null
                : choices.stream()
                .map(FolktaleDetailConverter::toChoiceDto)
                .toList();

        return FolktaleDetailDto.builder()
                .id(folktaleDetail.getId())
                .content(folktaleDetail.getContent())
                .imageUrl(folktaleDetail.getImageUrl())
                .voiceUrl(voiceUrl)
                .choices(choiceDtoList)
                .build();
    }

    public static ChoiceScenarioDto toChoiceScenarioDto(
            Choice choice
    ) {
        return ChoiceScenarioDto.builder()
                .id(choice.getId())
                .content(choice.getContent())
                .imageUrl(choice.getImageUrl())
                .build();
    }

    public static CustomizedDetail toCustomizedDetailEntity(
            MemberFolktale memberFolktale, String content, String imageUrl
    ) {
        return CustomizedDetail.builder()
                .memberFolktale(memberFolktale)
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }

    public static ChoiceDto toChoiceDto(Choice choice) {
        return ChoiceDto.builder()
                .id(choice.getId())
                .answer(choice.getAnswer())
                .build();
    }
}
