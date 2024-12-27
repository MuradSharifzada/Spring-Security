package com.spring.exception;

public class InvalidCredentialsException extends RuntimeException {

    private final String message;

    public InvalidCredentialsException(String message) {
        this.message = message;
    }
}
