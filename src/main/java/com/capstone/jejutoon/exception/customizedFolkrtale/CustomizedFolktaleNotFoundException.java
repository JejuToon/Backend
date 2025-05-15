package com.capstone.jejutoon.exception.customizedFolkrtale;

import com.capstone.jejutoon.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public class CustomizedFolktaleNotFoundException extends BaseException {
    public CustomizedFolktaleNotFoundException(Long customizedDetailId) {
        super(
                "CustomizedDetail not found with id: " + customizedDetailId,
                "CUSTOMIZED_DETAIL_NOT_FOUND",
                HttpStatus.NOT_FOUND
        );
    }
}
