package com.capstone.jejutoon.generator;

public class ImageGenerationConverter {

    public static ImageGenerationRequest toRequest(
            String basePrompt, String choicePrompt
    ) {
        return ImageGenerationRequest.builder()
                .image("https://jejutoon-storage.s3.ap-northeast-2.amazonaws.com/characters/%EC%84%A4%EB%AC%B8%EB%8C%80%ED%95%A0%EB%A7%9D+%EC%86%8C%EC%8A%A4%EC%9D%B4%EB%AF%B8%EC%A7%80.png")
                .prompt(basePrompt + choicePrompt)
                .background("transparent")
                .model("gpt-image-1")
                .quality("medium")
                .size("1024x1024")
                .build();
    }
}
