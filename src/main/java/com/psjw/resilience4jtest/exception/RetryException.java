package com.psjw.resilience4jtest.exception;

public class RetryException extends RuntimeException {
    public RetryException(String message) {
        super(message);
    }
}