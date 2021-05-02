package com.demcare.demo.config;

import com.demcare.demo.exception.RestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestException> exceptionHandler(RestException ex){
        return new ResponseEntity<>(ex, ex.getHttpStatus());
    }
}
