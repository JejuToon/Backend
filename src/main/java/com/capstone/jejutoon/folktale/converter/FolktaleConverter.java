package com.capstone.jejutoon.folktale.converter;

import com.capstone.jejutoon.folktale.domain.Folktale;
import com.capstone.jejutoon.folktale.domain.Location;
import com.capstone.jejutoon.folktale.dto.response.FolktaleListDto;
import com.capstone.jejutoon.folktale.dto.response.FolktaleOverviewDto;
import com.capstone.jejutoon.folktale.dto.response.LocationDto;

import java.util.List;

public class FolktaleConverter {

    public static LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    public static FolktaleListDto toFolktaleListDto(
            Folktale folktale, List<Location> locations, List<String> categories, double score
    ) {
        List<LocationDto> locationDtos = locations.stream().map(FolktaleConverter::toLocationDto).toList();

        return FolktaleListDto.builder()
                .id(folktale.getId())
                .title(folktale.getTitle())
                .location(locationDtos)
                .categories(categories)
                .description(folktale.getDescription())
                .thumbnail(folktale.getThumbnail())
                .score(score)
                .build();
    }

    public static FolktaleOverviewDto toFolktaleOverviewDto(
            Folktale folktale, List<Location> locations, List<String> categories, List<Long> folktaleDetailIds, Double score
    ) {
        List<LocationDto> locationDtoList = locations.stream()
                .map(FolktaleConverter::toLocationDto).toList();

        return FolktaleOverviewDto.builder()
                .id(folktale.getId())
                .title(folktale.getTitle())
                .location(locationDtoList)
                .categories(categories)
                .description(folktale.getDescription())
                .summary(folktale.getSummary())
                .story(folktale.getStory())
                .thumbnail(folktale.getThumbnail())
                .score(score)
                .folktaleDetailIds(folktaleDetailIds)
                .build();
    }
}
