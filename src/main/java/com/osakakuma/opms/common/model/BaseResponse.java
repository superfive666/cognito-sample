package com.osakakuma.opms.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<E> {
    private int code;
    private String message;
    private String debugMessage;
    private String status;
    private E result;

    public static <T> ResponseEntity<BaseResponse<T>> success(T result) {
        var body =  BaseResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .status("success")
                .result(result)
                .build();
        return ResponseEntity.ok(body);
    }

    public static ResponseEntity<BaseResponse<Void>> validationError(String message) {
        var body = BaseResponse.<Void>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status("validation_error")
                .message(message)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<BaseResponse<Void>> unauthorized(String message) {
        var body = BaseResponse.<Void>builder()
                .code(HttpStatus.FORBIDDEN.value())
                .status("unauthorized")
                .message(message)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    public static ResponseEntity<BaseResponse<Void>> error(String message, String debugMessage) {
        var body = BaseResponse.<Void>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status("server_error")
                .message(message)
                .debugMessage(debugMessage)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
