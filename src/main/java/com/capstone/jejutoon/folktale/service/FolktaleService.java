package com.capstone.jejutoon.folktale.service;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.folktale.dto.response.FolktaleList;

public interface FolktaleService {

    PageResponse<FolktaleList> getAllFolktales(int page);
}
