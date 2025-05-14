package com.capstone.jejutoon.folktale.controller;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.folktale.dto.response.FolktaleDetailDto;
import com.capstone.jejutoon.folktale.dto.response.FolktaleListDto;
import com.capstone.jejutoon.folktale.service.FolktaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FolktaleController {

    private final FolktaleService folktaleService;

    @GetMapping("/folktale/{folktaleId}")
    ResponseEntity<FolktaleDetailDto> getFolktaleDetail(
            @PathVariable("folktaleId") Long folktaleId
    ) {
        FolktaleDetailDto response = folktaleService.getFolktaleDetail(folktaleId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/folktale")
    ResponseEntity<PageResponse<FolktaleListDto>> getFolktaleList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "category", defaultValue = "") String category
    ) {
        PageResponse<FolktaleListDto> response = folktaleService.getFolktaleList(page, category);

        return ResponseEntity.ok(response);
    }
}
