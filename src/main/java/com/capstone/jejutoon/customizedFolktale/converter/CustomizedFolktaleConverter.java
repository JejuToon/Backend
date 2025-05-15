package com.capstone.jejutoon.customizedFolktale.converter;

import com.capstone.jejutoon.customizedFolktale.domain.CustomizedDetail;
import com.capstone.jejutoon.customizedFolktale.dto.response.CustomizedFolktaleDto;

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
}
