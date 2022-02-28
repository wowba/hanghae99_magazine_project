package com.sparta.magazine.service;

import com.sparta.magazine.dto.UserRequestDto;
import com.sparta.magazine.dto.UserResponseDto;
import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.jwt.JwtTokenProvider;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.UserRepository;
import com.sparta.magazine.validator.LoginValidator;
import com.sparta.magazine.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Map;

import static com.sparta.magazine.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    
    // 비밀번호 암호화
    private final PasswordEncoder passwordEncoder;
    // 유저 저장소
    private final UserRepository userRepository;
    // 유효성 검사
    private final UserValidator userValidator;
    private final LoginValidator loginValidator;
    // 토큰 생성기
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public void createUser(UserRequestDto userRequestDto) {
        
        // 유효성 검사
        userValidator.validateUserInput(userRequestDto);

        // 비밀번호 암호화
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        
        // 유저 생성
        User user = User.builder()
                .email(userRequestDto.getEmail())
                .username(userRequestDto.getUsername())
                .password(password)
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build();

        // 유저 저장하기
        userRepository.save(user);
    }

    // 로그인
    public UserResponseDto loginUser(@RequestBody Map<String, String> user) {
        
        // 유효성 검사
        User member = loginValidator.validateLoginInput(user);

        UserResponseDto userResponseDto = UserResponseDto.builder()
                                .email(member.getEmail())
                                .username(member.getUsername())
                                .userId(member.getId())
                                .token(jwtTokenProvider.createToken(member.getUsername(), member.getRoles()))
                                .build();
        return userResponseDto;
    }

    // 회원 삭제
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
