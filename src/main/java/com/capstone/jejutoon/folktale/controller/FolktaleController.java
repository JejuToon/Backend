package com.capstone.jejutoon.folktale.controller;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.folktale.dto.response.FolktaleDetailDto;
import com.capstone.jejutoon.folktale.dto.response.FolktaleListDto;
import com.capstone.jejutoon.folktale.dto.response.FolktaleOverviewDto;
import com.capstone.jejutoon.folktale.service.FolktaleDetailService;
import com.capstone.jejutoon.folktale.service.FolktaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FolktaleController {

    private final FolktaleService folktaleService;
    private final FolktaleDetailService folktaleDetailService;

    @GetMapping("/folktale/{folktaleId}")
    ResponseEntity<FolktaleOverviewDto> getFolktaleOverview(
            @PathVariable("folktaleId") Long folktaleId
    ) {
        FolktaleOverviewDto response = folktaleService.getFolktaleOverview(folktaleId);

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

    @GetMapping("/folktale/nearby")
    ResponseEntity<PageResponse<FolktaleListDto>> getNearByFolktaleList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam("lat") Double latitude,
            @RequestParam("long") Double longitude
    ) {
        PageResponse<FolktaleListDto> response = folktaleService.getNearByFolktaleList(page, latitude, longitude);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/folktale-detail/{folktaleDetailId}")
    ResponseEntity<FolktaleDetailDto> getFolktaleScenario(
            @PathVariable("folktaleDetailId") Long folktaleDetailId
    ) {
        FolktaleDetailDto response = folktaleDetailService.getFolktaleDetail(folktaleDetailId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/folktale-detail/{folktaleDetailId}")
    ResponseEntity<Void> chooseFolktaleScenario(
            @PathVariable("folktaleDetailId") Long folktaleDetailId,
            @RequestParam("choice") String choice
    ) {
        folktaleDetailService.chooseFolktaleScenario(folktaleDetailId, choice);

        return ResponseEntity.ok().build();
    }
}
