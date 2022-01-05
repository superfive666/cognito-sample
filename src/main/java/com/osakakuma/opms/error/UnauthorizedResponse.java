package com.osakakuma.opms.error;

public record UnauthorizedResponse(
    int code,
    String message
) { }
