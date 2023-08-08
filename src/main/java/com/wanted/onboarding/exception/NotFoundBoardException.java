package com.wanted.onboarding.exception;

public class NotFoundBoardException extends RuntimeException {
    public NotFoundBoardException(String message) {
        super(message);
    }
}