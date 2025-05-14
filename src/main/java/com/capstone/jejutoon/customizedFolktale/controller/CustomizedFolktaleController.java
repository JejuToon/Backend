package com.capstone.jejutoon.customizedFolktale.controller;

import com.capstone.jejutoon.customizedFolktale.dto.response.CustomizedFolktaleDto;
import com.capstone.jejutoon.customizedFolktale.service.CustomizedDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customized-folktale")
public class CustomizedFolktaleController {

    private final CustomizedDetailService customizedDetailService;

    @RequestMapping("/{customizedDetailId}")
    ResponseEntity<CustomizedFolktaleDto> getCustomizedDetail(
            @PathVariable("customizedDetailId") Long customizedDetailId
    ) {
        CustomizedFolktaleDto response = customizedDetailService.getCustomizedDetail(customizedDetailId);

        return ResponseEntity.ok(response);
    }
}
