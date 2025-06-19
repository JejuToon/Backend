package com.capstone.jejutoon.folktale.service;

import com.capstone.jejutoon.common.service.RedisService;
import com.capstone.jejutoon.customizedFolktale.converter.CustomizedFolktaleConverter;
import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.customizedFolktale.dto.response.ChoiceForRedis;
import com.capstone.jejutoon.exception.customizedFolkrtale.ChoiceNotFoundException;
import com.capstone.jejutoon.exception.customizedFolkrtale.MemberFolktaleNotFoundException;
import com.capstone.jejutoon.exception.folktale.FolktaleDetailNotFoundException;
import com.capstone.jejutoon.folktale.converter.FolktaleDetailConverter;
import com.capstone.jejutoon.folktale.domain.Choice;
import com.capstone.jejutoon.folktale.domain.FolktaleDetail;
import com.capstone.jejutoon.folktale.dto.response.ChoiceScenarioDto;
import com.capstone.jejutoon.folktale.dto.response.FolktaleDetailDto;
import com.capstone.jejutoon.folktale.repository.ChoiceRepository;
import com.capstone.jejutoon.folktale.repository.FolktaleDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolktaleDetailServiceImpl implements FolktaleDetailService {

    private final FolktaleDetailRepository folktaleDetailRepository;
    private final ChoiceRepository choiceRepository;
    private final RedisService redisService;

    private final String AYA = "aya";
    private final String CEDRIC = "cedric";
    private final String WATSON = "watson";

    @Override
    @Transactional(readOnly = true)
    public FolktaleDetailDto getFolktaleDetail(Long folktaleDetailId, String voiceType) {
        FolktaleDetail folktaleDetail = folktaleDetailRepository.findById(folktaleDetailId)
                .orElseThrow(() -> new FolktaleDetailNotFoundException(folktaleDetailId));

        String voiceUrl = getVoiceUrls(folktaleDetailId, voiceType);

        List<Choice> choices = choiceRepository.findChoicesByFolktaleDetailId(folktaleDetail.getId());

        return FolktaleDetailConverter.toFolktaleDetailDto(folktaleDetail, choices, voiceUrl);
    }

    @Override
    @Transactional
    public ChoiceScenarioDto chooseFolktaleScenario(Long folktaleDetailId, Long choiceId) {
        MemberFolktale memberFolktale = folktaleDetailRepository.findMemberFolktaleByFolktaleDetailId(folktaleDetailId)
                .orElseThrow(() -> new MemberFolktaleNotFoundException("folktaleDetail", folktaleDetailId));

        Choice choice = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new ChoiceNotFoundException(choiceId));

        System.out.println(choice.getPrompt());

        // TODO: MemberFolktaleId로 조회할 수 있는 레디스 데이터 삽입(choiceId, 최종 이미지 생성 시 사용할 프롬프트)
        ChoiceForRedis choiceForRedis = CustomizedFolktaleConverter.toChoiceForRedis(choice);
        redisService.saveChoice(memberFolktale.getId(), choiceForRedis);

        return FolktaleDetailConverter.toChoiceScenarioDto(choice);
    }

    private String getVoiceUrls(Long folktaleDetailId, String voiceType) {
        String voiceUrls = redisService.getVoiceUrls(folktaleDetailId);
        if (voiceType.equals(AYA)) {
            return voiceUrls.split(",")[0]; // AYA 음성 URL
        } else if (voiceType.equals(CEDRIC)) {
            return voiceUrls.split(",")[1]; // CEDRIC 음성 URL
        } else if (voiceType.equals(WATSON)) {
            return voiceUrls.split(",")[2]; // WATSON 음성 URL
        } else {
            throw new IllegalArgumentException("Invalid voice type: " + voiceType);
        }
    }
}
