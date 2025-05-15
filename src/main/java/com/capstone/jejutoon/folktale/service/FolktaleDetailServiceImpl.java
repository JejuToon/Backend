package com.capstone.jejutoon.folktale.service;

import com.capstone.jejutoon.customizedFolktale.domain.CustomizedDetail;
import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.customizedFolktale.repository.CustomizedDetailRepository;
import com.capstone.jejutoon.exception.customizedFolkrtale.MemberFolktaleNotFoundException;
import com.capstone.jejutoon.exception.folktale.FolktaleDetailNotFoundException;
import com.capstone.jejutoon.folktale.converter.FolktaleDetailConverter;
import com.capstone.jejutoon.folktale.domain.FolktaleDetail;
import com.capstone.jejutoon.folktale.domain.Scenario;
import com.capstone.jejutoon.folktale.dto.response.FolktaleScenarioDto;
import com.capstone.jejutoon.folktale.repository.FolktaleDetailRepository;
import com.capstone.jejutoon.folktale.repository.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolktaleDetailServiceImpl implements FolktaleDetailService {

    private final FolktaleDetailRepository folktaleDetailRepository;
    private final ScenarioRepository scenarioRepository;
    private final CustomizedDetailRepository customizedDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public FolktaleScenarioDto getFolktaleDetail(Long folktaleDetailId) {
        FolktaleDetail folktaleDetail = folktaleDetailRepository.findById(folktaleDetailId)
                .orElseThrow(() -> new FolktaleDetailNotFoundException(folktaleDetailId));

        Scenario scenario = scenarioRepository.findByFolktaleDetailId(folktaleDetail.getId())
                .orElse(null);
        if (scenario != null) {
            List<String> choices = scenarioRepository.findChoicesById(scenario.getId());
            return FolktaleDetailConverter.toFolktaleRealDetailDto(folktaleDetail, scenario.getQuestion(), choices);
        }

        return FolktaleDetailConverter.toFolktaleRealDetailDto(folktaleDetail, null, null);
    }

    @Override
    @Transactional
    public void chooseFolktaleScenario(Long folktaleDetailId, String choice) {
        FolktaleDetail folktaleDetail = folktaleDetailRepository.findById(folktaleDetailId)
                .orElseThrow(() -> new FolktaleDetailNotFoundException(folktaleDetailId));

        String question = scenarioRepository.findQuestionByFolktaleDetailId(folktaleDetail.getId());

        String customizedContent = (folktaleDetail.getContent() + question).replace("???", choice);

        MemberFolktale memberFolktale = folktaleDetailRepository.findMemberFolktaleByFolktaleDetailId(folktaleDetail.getId())
                .orElseThrow(() -> new MemberFolktaleNotFoundException("folktaleDetail", folktaleDetail.getId()));

        // TODO: 이미지 생성 api 연결 필요.

        CustomizedDetail customizedDetail = FolktaleDetailConverter
                .toCustomizedDetailEntity(memberFolktale, customizedContent, "exampleImage.com");

        customizedDetailRepository.save(customizedDetail);
    }
}
