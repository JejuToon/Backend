package com.capstone.jejutoon.exception.s3;

import com.capstone.jejutoon.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public class FailedUploadException extends BaseException {
    public FailedUploadException() {
        super("Wrong input or error with reading file", "FAILED_TO_UPLOAD_FILE", HttpStatus.BAD_REQUEST);
    }
}
