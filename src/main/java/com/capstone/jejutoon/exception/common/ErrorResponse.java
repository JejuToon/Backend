package com.capstone.jejutoon.exception.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String message;
    private final String errorCode;
}
