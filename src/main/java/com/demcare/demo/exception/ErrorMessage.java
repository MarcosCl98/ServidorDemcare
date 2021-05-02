package com.demcare.demo.exception;

public enum ErrorMessage {
    MISSING_PARAMETER ("Falta el parámetro {0}");

    private final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
