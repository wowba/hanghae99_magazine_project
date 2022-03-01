package com.sparta.magazine.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;

    private String email;

    private String username;

    private String token;

    @Builder
    public UserResponseDto(Long userId, String email, String username, String token){
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.token = token;
    }
}
