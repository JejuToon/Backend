package com.capstone.jejutoon.folktale.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FolktaleDetailDto {

    Long id;
    String title;
    List<LocationDto> location;
    List<String> categories;
    List<Long> folktaleDetailIds;
}
