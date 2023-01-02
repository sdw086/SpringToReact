package com.example.reactboot.config.exception;

public class TokenExpiredException extends RuntimeException{

    private static final long serialVersionUID = 2036611901536356933L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public TokenExpiredException() {
        super("토큰이 만료되었습니다.");
    }
}
