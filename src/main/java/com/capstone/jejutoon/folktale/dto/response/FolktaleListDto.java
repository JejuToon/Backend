package com.capstone.jejutoon.folktale.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FolktaleListDto {

    private Long id;
    private String title;
    private List<LocationDto> location;
    private List<String> categories;
    private String description;
    private String thumbnail;
    private double score;
}
