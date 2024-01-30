package com.capstone.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    USERNAME_DUPLICATED(HttpStatus.CONFLICT),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED),
    PASSWORD_CHECK_FAILS(HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_TRIAL(HttpStatus.UNAUTHORIZED);

    private final HttpStatus httpStatus;
}
