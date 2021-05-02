package com.demcare.demo.exception;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class RestException extends RuntimeException {

    private HttpStatus httpStatus;
    private String[] args;

    public RestException(HttpStatus httpStatus, ErrorMessage errorMessage, String[] args) {
        super(errorMessage.getMessage());
        this.httpStatus = httpStatus;
        this.args = args;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(super.getMessage(), args);
    }
}
