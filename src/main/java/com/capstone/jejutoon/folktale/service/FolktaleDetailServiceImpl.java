package com.capstone.jejutoon.folktale.service;

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
}
