package com.capstone.jejutoon.exception.customizedFolkrtale;

import com.capstone.jejutoon.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public class ChoiceNotFoundException extends BaseException {
    public ChoiceNotFoundException(Long choiceId) {
        super(
                "Choice not found with id: " + choiceId,
                "CHOICE_NOT_FOUND",
                HttpStatus.NOT_FOUND
        );
    }
}
