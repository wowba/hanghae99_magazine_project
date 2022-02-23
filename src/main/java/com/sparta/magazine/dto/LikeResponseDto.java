package com.sparta.magazine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponseDto {

    private Long userId;

    public LikeResponseDto(Long userId){
        this.userId = userId;
    }
}
