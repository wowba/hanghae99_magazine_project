package com.sparta.magazine.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 Bad Request
    USERNAME_VALIDATE(HttpStatus.BAD_REQUEST, "400_1", "유저네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성해야 합니다." )

    // 404 Not Found



    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;
}
