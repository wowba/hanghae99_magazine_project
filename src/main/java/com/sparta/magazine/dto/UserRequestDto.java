package com.sparta.magazine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    private String email;

    private String username;

    private String password;

    private String passwordCheck;
}
