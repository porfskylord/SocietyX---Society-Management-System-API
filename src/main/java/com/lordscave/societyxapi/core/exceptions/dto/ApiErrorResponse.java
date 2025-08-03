package com.lordscave.societyxapi.core.exceptions.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@lombok.Data
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldErrorDetails> validationErrors;

    public ApiErrorResponse(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    public ApiErrorResponse(HttpStatus status, String message, String path, List<FieldErrorDetails> validationErrors) {
        this(status, message, path);
        this.validationErrors = validationErrors;
    }

    public static class FieldErrorDetails {
        private String field;
        private String message;

        public FieldErrorDetails(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }

}


