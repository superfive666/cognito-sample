package com.osakakuma.opms.error;

public class OpmsUnauthorizedException extends RuntimeException {
    public OpmsUnauthorizedException(String message) {
        super(message);
    }
}
