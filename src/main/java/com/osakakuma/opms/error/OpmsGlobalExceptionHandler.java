package com.osakakuma.opms.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ControllerAdvice
public class OpmsGlobalExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OpmsUnauthorizedException.class)
    public UnauthorizedResponse unauthorized(Exception e) {
        return new UnauthorizedResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(OpmsException.class)
    public ErrorResponse internalServerError(Exception e) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(), Arrays.toString(e.getStackTrace()));
    }
}
