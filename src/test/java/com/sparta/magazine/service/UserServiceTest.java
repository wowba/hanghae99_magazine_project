package com.sparta.magazine.service;

import com.sparta.magazine.dto.UserRequestDto;
import com.sparta.magazine.jwt.JwtTokenProvider;
import com.sparta.magazine.repository.UserRepository;
import com.sparta.magazine.validator.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
// Mockito는 편하게 가짜 객체를 만들고 가짜함수를 호출해 줄 수 있지만
// 그 결과에 대해서 명시해줘야 한다. 단위테스트에만 사용!
class UserServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    UserValidator userValidator;

    @Mock
    JwtTokenProvider jwtTokenProvider;

//    @Nested
//    @DisplayName("유저 생성하기 - 성공")
//    class CreateUser {
//    }
}