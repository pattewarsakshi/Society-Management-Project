package com.society.exception;

public class VisitorAlreadyInsideException extends RuntimeException {
    public VisitorAlreadyInsideException(String message) {
        super(message);
    }
}
