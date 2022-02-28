package com.sparta.magazine.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public class ErrorCodeException extends RuntimeException  {
    private final ErrorCode errorCode;
}
