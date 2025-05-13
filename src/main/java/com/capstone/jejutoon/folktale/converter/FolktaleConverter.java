package com.capstone.jejutoon.folktale.converter;

import com.capstone.jejutoon.folktale.domain.Folktale;
import com.capstone.jejutoon.folktale.domain.Location;
import com.capstone.jejutoon.folktale.dto.response.FolktaleList;
import com.capstone.jejutoon.folktale.dto.response.LocationDto;

import java.util.List;

public class FolktaleConverter {

    public static LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    public static FolktaleList toFolktaleBase(
            Folktale folktale, List<Location> locations, List<String> categories
    ) {
        List<LocationDto> locationDtos = locations.stream().map(FolktaleConverter::toLocationDto).toList();

        return FolktaleList.builder()
                .id(folktale.getId())
                .title(folktale.getTitle())
                .location(locationDtos)
                .categories(categories)
                .build();
    }
}
