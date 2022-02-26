package com.sparta.magazine.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ErrorCodeExceptionHandler extends ResponseEntityExceptionHandler {

    // ErrorCode 에서 만든 커스텀 에러들을 보낼 수 있다! 이거 너무좋다.
    @ExceptionHandler( value = { ErrorCodeException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(ErrorCodeException e) {
        log.error("Error", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}