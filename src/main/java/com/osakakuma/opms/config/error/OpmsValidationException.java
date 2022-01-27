package com.osakakuma.opms.config.error;

public class OpmsValidationException extends RuntimeException {
    public OpmsValidationException(String message) {
        super(message);
    }
}
