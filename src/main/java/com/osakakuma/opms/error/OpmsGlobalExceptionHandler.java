package com.osakakuma.opms.error;

import com.osakakuma.opms.util.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ControllerAdvice
public class OpmsGlobalExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OpmsUnauthorizedException.class)
    public ResponseEntity<BaseResponse<Void>> unauthorized(OpmsUnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BaseResponse.unauthorized(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(OpmsException.class)
    public ResponseEntity<BaseResponse<Void>> internalServerError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error(e.getMessage(), Arrays.toString(e.getStackTrace())));
    }
}
