package com.capstone.jejutoon.common.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageMeta {

    private Integer listSize;
    private Integer totalPage;
    private Long totalElements;
    private boolean isFirst;
    private boolean isLast;

    @Builder
    public PageMeta(Page<?> page) {
        this.listSize = page.getSize();
        this.totalPage = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
    }
}
