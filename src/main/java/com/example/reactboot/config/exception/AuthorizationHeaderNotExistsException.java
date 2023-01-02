package com.example.reactboot.config.exception;

public class AuthorizationHeaderNotExistsException extends RuntimeException{

    private static final long serialVersionUID = 4858506469476160448L;

    public AuthorizationHeaderNotExistsException() {
        super("토큰 인증 후 진행 부탁드립니다.");
    }
}
