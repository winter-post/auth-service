package com.devwinter.authservice.presentation.dto;

import com.devwinter.authservice.domain.exception.UserErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.MultiValueMap;

import static com.devwinter.authservice.presentation.exceptionhandler.GlobalExceptionHandler.ARGUMENT_NOT_VALID_STRING;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private String code;
    private String message;
    private T body;

    public static BaseResponse<?> error(String code, String message) {
        return BaseResponse.builder()
                           .code(code)
                           .message(message)
                           .build();
    }

    public static <T> BaseResponse<?> error(String code, String message, T body) {
        return BaseResponse.builder()
                           .code(code)
                           .message(message)
                            .body(body)
                           .build();
    }

    public static BaseResponse<?> error(UserErrorCode userErrorCode) {
        return BaseResponse.builder()
                           .code(userErrorCode.getHttpStatus().toString())
                           .message(userErrorCode.getMessage())
                           .build();
    }

    public static BaseResponse<MultiValueMap<String, String>> error(MultiValueMap<String, String> errors) {
        return BaseResponse.<MultiValueMap<String, String>>builder()
                           .code(BAD_REQUEST.toString())
                           .message(ARGUMENT_NOT_VALID_STRING)
                           .body(errors)
                           .build();
    }

    public static <T> BaseResponse<T> success(T response, String message) {
        return BaseResponse.<T>builder()
                           .code("success")
                           .message(message)
                           .body(response)
                           .build();
    }
}
