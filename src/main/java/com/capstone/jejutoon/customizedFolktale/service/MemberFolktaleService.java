package com.capstone.jejutoon.customizedFolktale.service;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.customizedFolktale.dto.response.CreatedMemberFolktaleDto;
import com.capstone.jejutoon.customizedFolktale.dto.response.MyFolktaleDto;
import com.capstone.jejutoon.folktale.dto.response.ChoiceScenarioDto;

public interface MemberFolktaleService {

    CreatedMemberFolktaleDto createMemberFolktale(Long folktaleId);
    PageResponse<MyFolktaleDto> getMyFolktales(int page);
    void createMyCharacterImage(Long memberFolktaleId, Integer score);
    ChoiceScenarioDto chooseFolktaleScenario(Long memberFolktaleId, Long choiceId);
    void addFolktaleImagePrompt(Long memberFolktaleId, String prompt);
}
