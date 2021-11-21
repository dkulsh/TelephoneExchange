package com.impact.backend.exception;

public class PhoneDetailsNotFoundException extends Exception{

    private String message;

    public PhoneDetailsNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
