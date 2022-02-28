package com.sparta.magazine.validator;


import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.sparta.magazine.exception.ErrorCode.LOGIN_PASSWORD_NOT_MATCH;
import static com.sparta.magazine.exception.ErrorCode.LOGIN_USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class LoginValidator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User validateLoginInput(Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new ErrorCodeException(LOGIN_USER_NOT_FOUND));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new ErrorCodeException(LOGIN_PASSWORD_NOT_MATCH);
        }
        return member;
    }
}
