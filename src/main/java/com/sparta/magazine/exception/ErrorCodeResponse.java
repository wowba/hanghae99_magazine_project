package com.sparta.magazine.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

// 실제로 유저에게 보낼 응답 포맷.
@Getter
@Builder
public class ErrorCodeResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String errorCode;
    private final String message;

    public static ResponseEntity<ErrorCodeResponse> toResponseEntity(ErrorCode errorCode) {

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorCodeResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .errorCode(errorCode.getErrorCode())
                        .message(errorCode.getErrorMessage())
                        .build()
                );
    }
}
