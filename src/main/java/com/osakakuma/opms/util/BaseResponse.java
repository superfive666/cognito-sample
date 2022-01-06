package com.osakakuma.opms.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

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

    public static <T> BaseResponse<T> success(T result) {
        return BaseResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .status("success")
                .result(result)
                .build();
    }

    public static <T> BaseResponse<T> unauthorized(String message) {
        return BaseResponse.<T>builder()
                .code(HttpStatus.FORBIDDEN.value())
                .status("unauthorized")
                .message(message)
                .build();
    }

    public static <T> BaseResponse<T> error(String message, String debugMessage) {
        return BaseResponse.<T>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status("server_error")
                .message(message)
                .debugMessage(debugMessage)
                .build();
    }
}
