package com.capstone.jejutoon.common.service;

import com.capstone.jejutoon.customizedFolktale.dto.response.ChoiceForRedis;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private String getRefreshTokenKey(String id) {
        return "refreshToken:" + id;
    }

    // Refresh Token 저장
    public void saveRefreshToken(String id, String refreshToken, long duration) {
        redisTemplate.opsForValue().set(getRefreshTokenKey(id), refreshToken, Duration.ofMillis(duration));
    }

    // Refresh Token 조회
    public String getRefreshToken(String id) {
        return redisTemplate.opsForValue().get(getRefreshTokenKey(id));
    }

    // Refresh Token 삭제
    public void deleteRefreshToken(String id) {
        redisTemplate.delete(getRefreshTokenKey(id));
    }

    public void saveChoice(Long memberFolktaleId, ChoiceForRedis choice) {
        String redisKey = "memberFolktale:" + memberFolktaleId;
        try {
            String choiceJson = objectMapper.writeValueAsString(choice);
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            setOps.add(redisKey, choiceJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Choice", e);
        }
    }

    public String getChoicePrompt(Long memberFolktaleId) {
        String redisKey = "memberFolktale:" + memberFolktaleId;
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        Set<String> values = setOps.members(redisKey);

        if (values.isEmpty()) {
            return "";
        }

        List<String> prompts = new ArrayList<>();

        for (String json : values) {
            try {
                ChoiceForRedis choice = objectMapper.readValue(json, ChoiceForRedis.class);
                prompts.add(choice.getPrompt());
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to deserialize Choice", e);
            }
        }

        return String.join(" ", prompts);
    }

    public String getVoiceUrls(Long folktaleDetailId) {
        String redisKey = "folktaleDetail:" + folktaleDetailId;
        return redisTemplate.opsForValue().get(redisKey);
    }
}
