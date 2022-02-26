package com.sparta.magazine.service;

import com.sparta.magazine.dto.UserRequestDto;
import com.sparta.magazine.dto.UserResponseDto;
import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.jwt.JwtTokenProvider;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sparta.magazine.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    
    // 비밀번호 암호화
    private final PasswordEncoder passwordEncoder;
    // 유저 저장소
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public void createUser(UserRequestDto userRequestDto) {

        // 유저네임 유효성 확인
        if(!Pattern.matches("^[a-zA-Z0-9]{3,20}$", userRequestDto.getUsername())) {
            throw new ErrorCodeException(USERNAME_VALIDATE);
        }

        // 비밀번호 닉네임 포함 확인
        if(userRequestDto.getPassword().contains(userRequestDto.getUsername())) {
            throw new ErrorCodeException(PASSWORD_INCLUDE_USERNAME);
        }
        
        // 비밀번호 길이 확인
        if(userRequestDto.getPassword().length() < 4) {
            throw new ErrorCodeException(PASSWORD_LENGTH);
        }
        
        // 비밀번호 일치 확인
        if(!Objects.equals(userRequestDto.getPassword(), userRequestDto.getPasswordCheck())) {
            throw new ErrorCodeException(PASSWORD_COINCIDE);
        }
        
        // 비밀번호 암호화
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        
        // 유저 생성
        User user = User.builder()
                .email(userRequestDto.getEmail())
                .username(userRequestDto.getUsername())
                .password(password)
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build();

        // 이메일 중복 확인
        emailIsExist(user);
        // 유저네임 중복 확인
        usernameIsExist(user);

        // 유저 저장하기
        userRepository.save(user);
    }

    // 이메일 중복 확인
    private void emailIsExist(User user) {
        Optional<User> findEmail = userRepository.findByEmail(user.getEmail());
        if(findEmail.isPresent()){
            throw new ErrorCodeException(EMAIL_DUPLICATE);
        }
    }

    // 유저네임 중복 확인
    private void usernameIsExist(User user) {
        Optional<User> findUsername = userRepository.findByUsername(user.getUsername());
        if(findUsername.isPresent()){
            throw new ErrorCodeException(USERNAME_DUPLICATE);
        }
    }

    // 로그인
    public UserResponseDto loginUser(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new ErrorCodeException(LOGIN_USER_NOT_FOUND));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new ErrorCodeException(LOGIN_PASSWORD_NOT_MATCH);
        }
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
