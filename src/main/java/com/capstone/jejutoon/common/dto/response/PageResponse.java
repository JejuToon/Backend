package com.capstone.jejutoon.common.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse<T> {

    private List<T> contents;
    private PageMeta meta;

    public PageResponse(List<T> contents, Page<?> page) {
        this.contents = contents;
        this.meta = PageMeta.builder()
                .page(page)
                .build();
    }
}
