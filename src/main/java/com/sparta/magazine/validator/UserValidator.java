package com.sparta.magazine.validator;

import com.sparta.magazine.dto.UserRequestDto;
import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sparta.magazine.exception.ErrorCode.*;
import static com.sparta.magazine.exception.ErrorCode.PASSWORD_COINCIDE;

@Component // 선언하지 않으면 사용할 수 없다!!!!!
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUserInput(UserRequestDto userRequestDto) {
        // 유저네임 유효성 확인
        if (!Pattern.matches("^[a-zA-Z0-9]{3,20}$", userRequestDto.getUsername())) {
            throw new ErrorCodeException(USERNAME_VALIDATE);
        }

        // 유저네임 중복 확인
        Optional<User> findUsername = userRepository.findByUsername(userRequestDto.getUsername());
        if (findUsername.isPresent()) {
            throw new ErrorCodeException(USERNAME_DUPLICATE);
        }

        // 이메일 중복 확인
        Optional<User> findEmail = userRepository.findByEmail(userRequestDto.getEmail());
        if (findEmail.isPresent()) {
            throw new ErrorCodeException(EMAIL_DUPLICATE);
        }

        // 비밀번호 닉네임 포함 확인
        if (userRequestDto.getPassword().contains(userRequestDto.getUsername())) {
            throw new ErrorCodeException(PASSWORD_INCLUDE_USERNAME);
        }

        // 비밀번호 길이 확인
        if (userRequestDto.getPassword().length() < 4) {
            throw new ErrorCodeException(PASSWORD_LENGTH);
        }

        // 비밀번호 일치 확인
        if (!Objects.equals(userRequestDto.getPassword(), userRequestDto.getPasswordCheck())) {
            throw new ErrorCodeException(PASSWORD_COINCIDE);
        }

    }
}
