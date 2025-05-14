package com.capstone.jejutoon.customizedFolktale.service;

import com.capstone.jejutoon.customizedFolktale.converter.CustomizedFolktaleConverter;
import com.capstone.jejutoon.customizedFolktale.domain.CustomizedDetail;
import com.capstone.jejutoon.customizedFolktale.dto.response.CustomizedFolktaleDto;
import com.capstone.jejutoon.customizedFolktale.repository.CustomizedDetailRepository;
import com.capstone.jejutoon.exception.customizedFolkrtale.CustomizedFolktaleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomizedDetailServiceImpl implements CustomizedDetailService {

    private final CustomizedDetailRepository customizedDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public CustomizedFolktaleDto getCustomizedDetail(Long customizedDetailId) {
        CustomizedDetail customizedDetail = customizedDetailRepository.findById(customizedDetailId)
                .orElseThrow(() -> new CustomizedFolktaleNotFoundException(customizedDetailId));

        return CustomizedFolktaleConverter.toCustomizedFolktaleDto(customizedDetail);
    }
}
