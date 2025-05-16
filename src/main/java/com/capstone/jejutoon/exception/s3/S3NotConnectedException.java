package com.capstone.jejutoon.exception.s3;

import com.capstone.jejutoon.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public class S3NotConnectedException extends BaseException {
    public S3NotConnectedException() {
        super("Failed to connect S3", "S3_NOT_CONNECTED", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
