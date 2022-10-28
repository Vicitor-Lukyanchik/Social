package com.social.controller.exception;

public class PasswordRepeatException extends RuntimeException {

    public PasswordRepeatException(String message) {
        super(message);
    }
}
