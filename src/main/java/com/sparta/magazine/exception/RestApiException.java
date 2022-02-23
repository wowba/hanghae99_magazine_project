package com.sparta.magazine.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestApiException {
    private String msg;
    private String result;
}

