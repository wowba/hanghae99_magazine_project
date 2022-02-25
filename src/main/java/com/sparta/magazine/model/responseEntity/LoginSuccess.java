package com.sparta.magazine.model.responseEntity;

import com.sparta.magazine.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginSuccess {
    private String result;
    private String msg;
    private UserResponseDto userData;
}
