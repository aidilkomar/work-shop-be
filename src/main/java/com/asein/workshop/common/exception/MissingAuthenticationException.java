package com.asein.workshop.common.exception;

public class MissingAuthenticationException extends RuntimeException {
    public MissingAuthenticationException(String message) {
        super(message);
    }
}
