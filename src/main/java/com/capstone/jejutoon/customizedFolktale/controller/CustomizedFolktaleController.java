package com.capstone.jejutoon.customizedFolktale.controller;

import com.capstone.jejutoon.customizedFolktale.dto.response.CustomizedFolktaleDto;
import com.capstone.jejutoon.customizedFolktale.service.CustomizedDetailService;
import com.capstone.jejutoon.customizedFolktale.service.MemberFolktaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CustomizedFolktaleController {

    private final CustomizedDetailService customizedDetailService;
    private final MemberFolktaleService memberFolktaleService;

    @RequestMapping("/customized-folktale/{customizedDetailId}")
    ResponseEntity<CustomizedFolktaleDto> getCustomizedDetail(
            @PathVariable("customizedDetailId") Long customizedDetailId
    ) {
        CustomizedFolktaleDto response = customizedDetailService.getCustomizedDetail(customizedDetailId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/folktale/{folktaleId}")
    ResponseEntity<Void> createMemberFolktale(
            @PathVariable("folktaleId") Long folktaleId,
            @RequestPart("character") MultipartFile character
    ) {
        memberFolktaleService.createMemberFolktale(folktaleId, character);

        return ResponseEntity.ok().build();
    }
}
