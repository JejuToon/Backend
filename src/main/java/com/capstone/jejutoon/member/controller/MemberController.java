package com.capstone.jejutoon.member.controller;

import com.capstone.jejutoon.member.dto.response.MyPageDto;
import com.capstone.jejutoon.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    ResponseEntity<MyPageDto> getMyPage() {
        MyPageDto response = memberService.getMyPage();

        return ResponseEntity.ok(response);
    }
}
