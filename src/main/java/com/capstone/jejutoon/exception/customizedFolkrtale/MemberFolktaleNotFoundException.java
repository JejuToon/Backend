package com.capstone.jejutoon.exception.customizedFolkrtale;

import com.capstone.jejutoon.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public class MemberFolktaleNotFoundException extends BaseException {
    public MemberFolktaleNotFoundException(String message, Long folktaleDetailId) {
        super(
                "MemberFolktale not found with " + message + " id: " + folktaleDetailId,
                "MEMBER_FOLKTALE_NOT_FOUND",
                HttpStatus.NOT_FOUND
        );
    }
}
