package com.capstone.jejutoon.exception.s3;

import com.capstone.jejutoon.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public class S3AccessDeniedException extends BaseException {
    public S3AccessDeniedException() {
        super("Failed to authorize with S3", "S3_ACCESS_DENIED", HttpStatus.FORBIDDEN);
    }
}
