package com.capstone.jejutoon.customizedFolktale.service;

import com.capstone.jejutoon.common.service.S3Service;
import com.capstone.jejutoon.config.auth.PrincipalDetailsService;
import com.capstone.jejutoon.customizedFolktale.converter.MemberFolktaleConverter;
import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.customizedFolktale.repository.MemberFolktaleRepository;
import com.capstone.jejutoon.exception.folktale.FolktaleNotFoundException;
import com.capstone.jejutoon.exception.member.MemberNotFoundException;
import com.capstone.jejutoon.folktale.domain.Folktale;
import com.capstone.jejutoon.folktale.repository.FolktaleRepository;
import com.capstone.jejutoon.member.domain.Member;
import com.capstone.jejutoon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberFolktaleServiceImpl implements MemberFolktaleService {

    private final S3Service s3Service;
    private final MemberFolktaleRepository memberFolktaleRepository;
    private final FolktaleRepository folktaleRepository;
    private final MemberRepository memberRepository;

    private static final String PATH = "characters/";

    @Override
    @Transactional
    public void createMemberFolktale(Long folktaleId, MultipartFile image) {
        Folktale folktale = folktaleRepository.findById(folktaleId)
                .orElseThrow(() -> new FolktaleNotFoundException(folktaleId));

        Long memberId = PrincipalDetailsService.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        String imageUrl = s3Service.uploadImage(image, PATH);

        MemberFolktale memberFolktale = MemberFolktaleConverter.toMemberFolktaleEntity(folktale, member, imageUrl);
        memberFolktaleRepository.save(memberFolktale);
    }
}
