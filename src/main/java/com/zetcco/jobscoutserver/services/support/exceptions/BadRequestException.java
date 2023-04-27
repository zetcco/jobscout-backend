package com.zetcco.jobscoutserver.services.support.exceptions;

public class BadRequestException extends RuntimeException {
    
    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }
}