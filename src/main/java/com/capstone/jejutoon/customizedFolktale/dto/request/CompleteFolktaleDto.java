package com.capstone.jejutoon.customizedFolktale.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CompleteFolktaleDto {

    private Integer score;
    private List<Long> choiceIds;
}
