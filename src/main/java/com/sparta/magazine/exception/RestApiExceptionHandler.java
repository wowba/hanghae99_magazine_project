package com.sparta.magazine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<RestApiException> handleApiRequestException(IllegalArgumentException ex) {

        RestApiException restApiException = new RestApiException();
        restApiException.setMsg(ex.getMessage());
        restApiException.setResult("fail");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }
}