package com.capstone.jejutoon.folktale.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LocationDto {

    private double latitude;
    private double longitude;
}
