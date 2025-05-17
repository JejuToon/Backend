package com.capstone.jejutoon.customizedFolktale.service;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.customizedFolktale.dto.response.MyFolktaleDto;
import org.springframework.web.multipart.MultipartFile;

public interface MemberFolktaleService {

    void createMemberFolktale(Long folktaleId, MultipartFile image);
    PageResponse<MyFolktaleDto> getMyFolktales(int page);
}
