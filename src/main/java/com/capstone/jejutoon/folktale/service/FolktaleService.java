package com.capstone.jejutoon.folktale.service;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.folktale.dto.response.FolktaleListDto;
import com.capstone.jejutoon.folktale.dto.response.FolktaleOverviewDto;

public interface FolktaleService {

    FolktaleOverviewDto getFolktaleOverview(Long folktaleId);
    PageResponse<FolktaleListDto> getFolktaleList(int page);
    PageResponse<FolktaleListDto> getFolktaleListByCategory(int page, String category);
    PageResponse<FolktaleListDto> getNearByFolktaleList(int page, Double latitude, Double longitude);
    PageResponse<FolktaleListDto> getFolktaleListByTitle(int page, String title);
}
