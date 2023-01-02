package com.example.reactboot.config.exception;

public class BadRequestException extends AppException {
    public BadRequestException() {
        super("Bad Request");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
