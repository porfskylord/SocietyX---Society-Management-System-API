package com.lordscave.societyxapi.core.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
