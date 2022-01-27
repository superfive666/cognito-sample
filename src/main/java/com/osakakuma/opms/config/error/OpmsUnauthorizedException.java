package com.osakakuma.opms.config.error;

public class OpmsUnauthorizedException extends RuntimeException {
    public OpmsUnauthorizedException(String message) {
        super(message);
    }
}
