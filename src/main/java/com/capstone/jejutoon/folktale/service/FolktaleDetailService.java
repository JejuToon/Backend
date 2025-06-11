package com.capstone.jejutoon.folktale.service;

import com.capstone.jejutoon.folktale.dto.response.ChoiceScenarioDto;
import com.capstone.jejutoon.folktale.dto.response.FolktaleDetailDto;

public interface FolktaleDetailService {

    FolktaleDetailDto getFolktaleDetail(Long folktaleDetailId);
    ChoiceScenarioDto chooseFolktaleScenario(Long folktaleDetailId, Long choiceId);
}
