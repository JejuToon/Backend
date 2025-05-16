package com.capstone.jejutoon.customizedFolktale.service;

import org.springframework.web.multipart.MultipartFile;

public interface MemberFolktaleService {

    void createMemberFolktale(Long folktaleId, MultipartFile image);
}
