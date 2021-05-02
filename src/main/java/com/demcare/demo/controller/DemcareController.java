package com.demcare.demo.controller;

import com.demcare.demo.exception.ErrorMessage;
import com.demcare.demo.exception.RestException;
import org.springframework.http.HttpStatus;

public abstract class DemcareController {
    public void validateRequiredParam(Object param, String paramName) {
        if (param == null || (param instanceof String && ((String) param).length() == 0)) {
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.MISSING_PARAMETER, new String[]{paramName});
        }
    }
}
