package com.example.reactboot.config;

import com.example.reactboot.common.response.JsonResponse;
import com.example.reactboot.config.exception.AuthorizationHeaderNotExistsException;
import com.example.reactboot.config.exception.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(TokenExpiredException.class)
    protected ResponseEntity<JsonResponse> handleMethodArgumentNotValidException(TokenExpiredException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        final JsonResponse response = new JsonResponse(status.value(), e.getMessage());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(AuthorizationHeaderNotExistsException.class)
    protected ResponseEntity<JsonResponse> handleMethodArgumentNotValidException(AuthorizationHeaderNotExistsException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        final JsonResponse response = new JsonResponse(status.value(), e.getMessage());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<JsonResponse> handleAccessDeniedException(AccessDeniedException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        final JsonResponse response = new JsonResponse(status.value(), e.getMessage());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<JsonResponse> handleException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final JsonResponse response = new JsonResponse(status.value(), e.getMessage());

        return ResponseEntity.status(status).body(response);
    }
}
