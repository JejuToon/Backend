package com.capstone.jejutoon.folktale.service;

import com.capstone.jejutoon.customizedFolktale.domain.CustomizedDetail;
import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.customizedFolktale.repository.CustomizedDetailRepository;
import com.capstone.jejutoon.exception.customizedFolkrtale.MemberFolktaleNotFoundException;
import com.capstone.jejutoon.exception.folktale.FolktaleDetailNotFoundException;
import com.capstone.jejutoon.folktale.converter.FolktaleDetailConverter;
import com.capstone.jejutoon.folktale.domain.Choice;
import com.capstone.jejutoon.folktale.domain.FolktaleDetail;
import com.capstone.jejutoon.folktale.dto.response.FolktaleDetailDto;
import com.capstone.jejutoon.folktale.repository.ChoiceRepository;
import com.capstone.jejutoon.folktale.repository.FolktaleDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolktaleDetailServiceImpl implements FolktaleDetailService {

    private final FolktaleDetailRepository folktaleDetailRepository;
    private final CustomizedDetailRepository customizedDetailRepository;
    private final ChoiceRepository choiceRepository;

    @Override
    @Transactional(readOnly = true)
    public FolktaleDetailDto getFolktaleDetail(Long folktaleDetailId) {
        FolktaleDetail folktaleDetail = folktaleDetailRepository.findById(folktaleDetailId)
                .orElseThrow(() -> new FolktaleDetailNotFoundException(folktaleDetailId));

        List<Choice> choices = choiceRepository.findChoicesByFolktaleDetailId(folktaleDetail.getId());

        return FolktaleDetailConverter.toFolktaleDetailDto(folktaleDetail, choices);
    }

    @Override
    @Transactional
    public void chooseFolktaleScenario(Long folktaleDetailId, String choice) {
        FolktaleDetail folktaleDetail = folktaleDetailRepository.findById(folktaleDetailId)
                .orElseThrow(() -> new FolktaleDetailNotFoundException(folktaleDetailId));

        MemberFolktale memberFolktale = folktaleDetailRepository.findMemberFolktaleByFolktaleDetailId(folktaleDetail.getId())
                .orElseThrow(() -> new MemberFolktaleNotFoundException("folktaleDetail", folktaleDetail.getId()));

        // TODO: 이미지 생성 api 연결 필요.

        CustomizedDetail customizedDetail = FolktaleDetailConverter
                .toCustomizedDetailEntity(memberFolktale, "", "exampleImage.com");

        customizedDetailRepository.save(customizedDetail);
    }
}
