package com.capstone.jejutoon.folktale.service;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.folktale.dto.response.FolktaleDetailDto;
import com.capstone.jejutoon.folktale.dto.response.FolktaleListDto;

public interface FolktaleService {

    PageResponse<FolktaleListDto> getAllFolktales(int page);
    FolktaleDetailDto getFolktaleDetail(Long folktaleId);
}
