package com.society.exception;

public class VisitorAlreadyExitedException extends RuntimeException {

    public VisitorAlreadyExitedException(String message) {
        super(message);
    }
}

