package com.devwinter.authservice.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

    USER_NOT_FOUND(UNAUTHORIZED, "회원이 존재하지 않습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
