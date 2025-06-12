package com.capstone.jejutoon.generator;

import com.capstone.jejutoon.common.service.S3Service;
import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.customizedFolktale.repository.MemberFolktaleRepository;
import com.capstone.jejutoon.exception.customizedFolkrtale.MemberFolktaleNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ImageGenerationService {

    private final WebClient webClient;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private MemberFolktaleRepository memberFolktaleRepository;

    @Value("${api.openai.api-key}")
    private String OPENAI_API_KEY;

    private static final String PATH = "characters/";

    public ImageGenerationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.openai.com/v1/images/edits")
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(config -> config.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                        .build())
                .build();
    }

    public Mono<String> createImage(String basePrompt, String choicePrompt) {
        String imageUrl = "https://jejutoon-storage.s3.ap-northeast-2.amazonaws.com/characters/설문대할망+소스이미지.png";
        String prompt = basePrompt + " " + choicePrompt;

        // 1. 이미지 다운로드 (동기 처리, 개선 여지 있음)
        byte[] imageBytes;
        try {
            imageBytes = new RestTemplate().getForObject(imageUrl, byte[].class);
        } catch (Exception e) {
            return Mono.error(new RuntimeException("이미지 다운로드 실패", e));
        }

        // 2. 이미지 part 생성
        ByteArrayResource imageResource = new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return "image.png";
            }
        };

        HttpHeaders imageHeaders = new HttpHeaders();
        imageHeaders.setContentType(MediaType.IMAGE_PNG);
        HttpEntity<ByteArrayResource> imagePart = new HttpEntity<>(imageResource, imageHeaders);

        // 3. Multipart 데이터 구성
        MultiValueMap<String, Object> multipartData = new LinkedMultiValueMap<>();
        multipartData.add("image", imagePart);
        multipartData.add("prompt", prompt);
        multipartData.add("model", "gpt-image-1");
        multipartData.add("background", "transparent");
        multipartData.add("quality", "medium");
        multipartData.add("size", "1024x1024");

        // 4. WebClient 전송
        return webClient.post()
                .uri("https://api.openai.com/v1/images/edits") // 또는 적절한 endpoint
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + OPENAI_API_KEY)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartData))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> {
                                    System.err.println("OpenAI 에러 응답: " + body);
                                    return Mono.error(new RuntimeException("API 호출 실패: " + body));
                                })
                )
                .bodyToMono(String.class);
    }

    public String imageUploadFromResponse(String responseJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseJson);

            String base64Image = root.path("data").get(0).path("b64_json").asText();

            return s3Service.uploadBase64Image(base64Image, PATH, "character.png");

        } catch (JsonProcessingException e) {
            throw new RuntimeException("OpenAI 응답 파싱 실패", e);
        }
    }

    @Async("imageGenerationExecutor")
    public void processImageGenerationAsync(Long memberFolktaleId, String basePrompt, String choicePrompt) {
        try {
            // 이미지 생성 API 호출
            String response = createImage(basePrompt, choicePrompt)
                    .block();

            // 이미지 업로드
            String imageUrl = imageUploadFromResponse(response);

            // DB 업데이트 (새로운 트랜잭션에서 실행)
            updateCharacterImageUrl(memberFolktaleId, imageUrl);

        } catch (Exception e) {
            System.err.println("이미지 생성 중 오류 발생: " + e.getMessage());
            // 필요시 에러 처리 로직 추가 (예: 재시도, 알림 등)
        }
    }

    @Transactional
    public void updateCharacterImageUrl(Long memberFolktaleId, String imageUrl) {
        MemberFolktale memberFolktale = memberFolktaleRepository.findById(memberFolktaleId)
                .orElseThrow(() -> new MemberFolktaleNotFoundException("", memberFolktaleId));

        memberFolktale.updateCharacterImageUrl(imageUrl);
        memberFolktaleRepository.save(memberFolktale);
    }
}
