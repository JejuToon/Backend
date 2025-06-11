package com.capstone.jejutoon.customizedFolktale.controller;

import com.capstone.jejutoon.customizedFolktale.dto.response.CreatedMemberFolktaleDto;
import com.capstone.jejutoon.customizedFolktale.service.MemberFolktaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CustomizedFolktaleController {

//    private final CustomizedDetailService customizedDetailService;
    private final MemberFolktaleService memberFolktaleService;


//    @RequestMapping("/customized-folktale/{customizedDetailId}")
//    ResponseEntity<CustomizedFolktaleDto> getCustomizedDetail(
//            @PathVariable("customizedDetailId") Long customizedDetailId
//    ) {
//        CustomizedFolktaleDto response = customizedDetailService.getCustomizedDetail(customizedDetailId);
//
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/folktale/{folktaleId}")
    ResponseEntity<CreatedMemberFolktaleDto> createMemberFolktale(
            @PathVariable("folktaleId") Long folktaleId
    ) {
        CreatedMemberFolktaleDto response = memberFolktaleService.createMemberFolktale(folktaleId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/my-folktale/{memberFolktaleId}")
    ResponseEntity<Void> completeMemberFolktale(
            @PathVariable("memberFolktaleId") Long memberFolktaleId
    ) {
        memberFolktaleService.createMyCharacterImage(memberFolktaleId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/member-folktale/{memberFolktaleId}")
    ResponseEntity<Void> chooseFolktaleScenario(
            @PathVariable("memberFolktaleId") Long memberFolktaleId,
            @RequestParam("choiceId") Long choiceId
    ) {
        memberFolktaleService.chooseFolktaleScenario(memberFolktaleId, choiceId);

        return ResponseEntity.ok().build();
    }
}
