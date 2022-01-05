package com.osakakuma.opms.error;

public record ErrorResponse(
        int code,
        String message,
        String debugMessage
) { }
