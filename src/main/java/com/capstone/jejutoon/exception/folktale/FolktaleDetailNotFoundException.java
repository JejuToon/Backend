package com.capstone.jejutoon.exception.folktale;

import com.capstone.jejutoon.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public class FolktaleDetailNotFoundException extends BaseException {
    public FolktaleDetailNotFoundException(Long folktaleDetailId) {
        super(
                "FolktaleDetail not found with id: " + folktaleDetailId,
                "FOLKTALE_DETAIL_NOT_FOUND",
                HttpStatus.NOT_FOUND
        );
    }
}
