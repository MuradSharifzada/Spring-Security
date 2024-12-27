package com.spring.exception;

public class UsernameAlreadyTakenException extends RuntimeException {
    private final String message;

    public UsernameAlreadyTakenException(String message) {
        super(message);
        this.message = message;
    }
}
