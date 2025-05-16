package com.capstone.jejutoon.folktale.service;

import com.capstone.jejutoon.folktale.dto.response.FolktaleDetailDto;

public interface FolktaleDetailService {

    FolktaleDetailDto getFolktaleDetail(Long folktaleDetailId);
    void chooseFolktaleScenario(Long folktaleDetailId, String choice);
}
