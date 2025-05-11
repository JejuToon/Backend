package com.capstone.jejutoon.exception.member;

import com.capstone.jejutoon.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends BaseException {
    public MemberNotFoundException(Object memberId) {
        super("Member not found with id: " + memberId, "MEMBER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
