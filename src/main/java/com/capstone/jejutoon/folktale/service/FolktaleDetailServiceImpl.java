package com.capstone.jejutoon.folktale.service;

import com.capstone.jejutoon.common.service.RedisService;
import com.capstone.jejutoon.customizedFolktale.converter.CustomizedFolktaleConverter;
import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.customizedFolktale.dto.response.ChoiceForRedis;
import com.capstone.jejutoon.customizedFolktale.repository.CustomizedDetailRepository;
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
    private final CustomizedDetailRepository customizedDetailRepository;
    private final ChoiceRepository choiceRepository;
    private final RedisService redisService;

    @Override
    @Transactional(readOnly = true)
    public FolktaleDetailDto getFolktaleDetail(Long folktaleDetailId) {
        FolktaleDetail folktaleDetail = folktaleDetailRepository.findById(folktaleDetailId)
                .orElseThrow(() -> new FolktaleDetailNotFoundException(folktaleDetailId));

        List<Choice> choices = choiceRepository.findChoicesByFolktaleDetailId(folktaleDetail.getId());

        return FolktaleDetailConverter.toFolktaleDetailDto(folktaleDetail, choices);
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
}
