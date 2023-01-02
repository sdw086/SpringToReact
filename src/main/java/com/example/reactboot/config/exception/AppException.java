package com.example.reactboot.config.exception;

public class AppException extends Exception {
    public AppException() {
        super("unspecified");
    }

    public AppException(String message) {
        super(message);
    }
}
