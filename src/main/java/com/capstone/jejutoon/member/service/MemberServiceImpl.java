package com.capstone.jejutoon.member.service;

import com.capstone.jejutoon.config.auth.PrincipalDetailsService;
import com.capstone.jejutoon.exception.member.MemberNotFoundException;
import com.capstone.jejutoon.member.converter.MemberConverter;
import com.capstone.jejutoon.member.domain.Member;
import com.capstone.jejutoon.member.dto.response.MyPageDto;
import com.capstone.jejutoon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public MyPageDto getMyPage() {
        Long memberId = PrincipalDetailsService.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        return MemberConverter.toMyPageDto(member);
    }
}
