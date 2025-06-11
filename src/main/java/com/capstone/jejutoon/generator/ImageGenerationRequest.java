package com.capstone.jejutoon.generator;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageGenerationRequest {

    private String image;
    private String prompt;
    private String background;
    private String model;
//    private String output_format;
    private String quality;
    private String size;
}
