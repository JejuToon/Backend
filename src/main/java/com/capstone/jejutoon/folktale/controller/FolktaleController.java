package com.capstone.jejutoon.folktale.controller;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.folktale.dto.response.FolktaleList;
import com.capstone.jejutoon.folktale.service.FolktaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FolktaleController {

    private final FolktaleService folktaleService;

    @GetMapping("/folktale")
    ResponseEntity<PageResponse<FolktaleList>> getAllFolktale(
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) {
        PageResponse<FolktaleList> response = folktaleService.getAllFolktales(page);

        return ResponseEntity.ok(response);
    }
}
