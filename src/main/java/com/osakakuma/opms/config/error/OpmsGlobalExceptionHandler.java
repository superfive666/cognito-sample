package com.osakakuma.opms.config.error;

import com.osakakuma.opms.common.model.BaseResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.Optional;

@ControllerAdvice
public class OpmsGlobalExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OpmsUnauthorizedException.class)
    public ResponseEntity<BaseResponse<Void>> unauthorized(OpmsUnauthorizedException e) {
        return BaseResponse.unauthorized(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OpmsValidationException.class)
    public ResponseEntity<BaseResponse<Void>> validationError(OpmsValidationException e) {
        return BaseResponse.validationError(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResponse<Void>> validationError(BindException e) {
        return BaseResponse.validationError(Optional.ofNullable(e.getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage).orElse(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(OpmsException.class)
    public ResponseEntity<BaseResponse<Void>> internalServerError(Exception e) {
        return BaseResponse.error(e.getMessage(), Arrays.toString(e.getStackTrace()));
    }
}
