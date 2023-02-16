package com.spring.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyUsedValueException extends RuntimeException {

    public AlreadyUsedValueException(String message) {
        super(message);
    }
    
}