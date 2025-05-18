package com.capstone.jejutoon.folktale.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FolktaleOverviewDto {

    private Long id;
    private String title;
    private List<LocationDto> location;
    private List<String> categories;
    private String description;
    private String summary;
    private String characterInfo;
    private String thumbnail;
    private double score;
    private List<Long> folktaleDetailIds;
}
