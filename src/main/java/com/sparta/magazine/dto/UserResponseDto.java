package com.sparta.magazine.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;

    private String email;

    private String username;

    @Builder
    public UserResponseDto(Long userId, String email, String username){
        this.userId = userId;
        this.email = email;
        this.username = username;
    }
}
