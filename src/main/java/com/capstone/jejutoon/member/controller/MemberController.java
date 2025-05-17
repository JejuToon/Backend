package com.capstone.jejutoon.member.controller;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.customizedFolktale.dto.response.MyFolktaleDto;
import com.capstone.jejutoon.customizedFolktale.service.MemberFolktaleService;
import com.capstone.jejutoon.member.dto.response.MyPageDto;
import com.capstone.jejutoon.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberFolktaleService memberFolktaleService;

    @GetMapping("/me")
    ResponseEntity<MyPageDto> getMyPage() {
        MyPageDto response = memberService.getMyPage();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/folktale")
    ResponseEntity<PageResponse<MyFolktaleDto>> getMyFolktales(
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) {
        PageResponse<MyFolktaleDto> response = memberFolktaleService.getMyFolktales(page);

        return ResponseEntity.ok(response);
    }
}
