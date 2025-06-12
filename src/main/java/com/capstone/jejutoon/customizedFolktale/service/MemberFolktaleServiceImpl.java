package com.capstone.jejutoon.customizedFolktale.service;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.common.service.RedisService;
import com.capstone.jejutoon.config.auth.PrincipalDetailsService;
import com.capstone.jejutoon.customizedFolktale.converter.CustomizedFolktaleConverter;
import com.capstone.jejutoon.customizedFolktale.converter.MemberFolktaleConverter;
import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.customizedFolktale.dto.request.CompleteFolktaleDto;
import com.capstone.jejutoon.customizedFolktale.dto.response.ChoiceForRedis;
import com.capstone.jejutoon.customizedFolktale.dto.response.CreatedMemberFolktaleDto;
import com.capstone.jejutoon.customizedFolktale.dto.response.MyFolktaleDto;
import com.capstone.jejutoon.customizedFolktale.repository.MemberFolktaleRepository;
import com.capstone.jejutoon.exception.customizedFolkrtale.ChoiceNotFoundException;
import com.capstone.jejutoon.exception.customizedFolkrtale.MemberFolktaleNotFoundException;
import com.capstone.jejutoon.exception.folktale.FolktaleNotFoundException;
import com.capstone.jejutoon.exception.member.MemberNotFoundException;
import com.capstone.jejutoon.folktale.converter.FolktaleDetailConverter;
import com.capstone.jejutoon.folktale.domain.Choice;
import com.capstone.jejutoon.folktale.domain.Folktale;
import com.capstone.jejutoon.folktale.dto.response.ChoiceScenarioDto;
import com.capstone.jejutoon.folktale.repository.ChoiceRepository;
import com.capstone.jejutoon.folktale.repository.FolktaleRepository;
import com.capstone.jejutoon.generator.ImageGenerationService;
import com.capstone.jejutoon.member.domain.Member;
import com.capstone.jejutoon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberFolktaleServiceImpl implements MemberFolktaleService {

    private final RedisService redisService;
    private final MemberFolktaleRepository memberFolktaleRepository;
    private final FolktaleRepository folktaleRepository;
    private final MemberRepository memberRepository;
    private final ChoiceRepository choiceRepository;
    private final ImageGenerationService imageGenerationService;

    @Override
    @Transactional
    public CreatedMemberFolktaleDto createMemberFolktale(Long folktaleId) {
        Folktale folktale = folktaleRepository.findById(folktaleId)
                .orElseThrow(() -> new FolktaleNotFoundException(folktaleId));

        Long memberId = PrincipalDetailsService.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        MemberFolktale memberFolktale = MemberFolktaleConverter.toMemberFolktaleEntity(folktale, member);
        MemberFolktale saved = memberFolktaleRepository.save(memberFolktale);

        return MemberFolktaleConverter.toCreatedMemberFolktaleDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MyFolktaleDto> getMyFolktales(int page) {
        Long memberId = PrincipalDetailsService.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(
                Sort.Order.desc("createdAt")
        ));

        Page<MemberFolktale> memberFolktales = memberFolktaleRepository.findByMemberId(member.getId(), pageable);

        List<MyFolktaleDto> myFolktales = memberFolktales
                .map(MemberFolktaleConverter::toMyFolktaleDto)
                .toList();

        return new PageResponse<>(myFolktales, memberFolktales);
    }

    @Override
    @Transactional
    public void createMyCharacterImage(Long memberFolktaleId, CompleteFolktaleDto request) {
        MemberFolktale memberFolktale = memberFolktaleRepository.findById(memberFolktaleId)
                .orElseThrow(() -> new MemberFolktaleNotFoundException("memberFolktale", memberFolktaleId));

        memberFolktale.updateScore(request.getScore());
        request.getChoiceIds()
                .forEach(choiceId -> chooseFolktaleScenario(memberFolktaleId, choiceId));

        String choicePrompt = redisService.getChoicePrompt(memberFolktale.getId());
        String basePrompt = memberFolktaleRepository.findPromptById(memberFolktale.getId());

        imageGenerationService.processImageGenerationAsync(memberFolktaleId, basePrompt, choicePrompt);
    }

    @Override
    @Transactional
    public ChoiceScenarioDto chooseFolktaleScenario(Long memberFolktaleId, Long choiceId) {
        MemberFolktale memberFolktale = memberFolktaleRepository.findById(memberFolktaleId)
                .orElseThrow(() -> new MemberFolktaleNotFoundException("memberFolktale", memberFolktaleId));

        Choice choice = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new ChoiceNotFoundException(choiceId));

        // TODO: MemberFolktaleId로 조회할 수 있는 레디스 데이터 삽입(choiceId, 최종 이미지 생성 시 사용할 프롬프트)
        ChoiceForRedis choiceForRedis = CustomizedFolktaleConverter.toChoiceForRedis(choice);
        redisService.saveChoice(memberFolktale.getId(), choiceForRedis);

        return FolktaleDetailConverter.toChoiceScenarioDto(choice);
    }

    @Override
    @Transactional
    public void addFolktaleImagePrompt(Long memberFolktaleId, String prompt) {
        MemberFolktale memberFolktale = memberFolktaleRepository.findById(memberFolktaleId)
                .orElseThrow(() -> new MemberFolktaleNotFoundException("memberFolktale", memberFolktaleId));


    }
}
