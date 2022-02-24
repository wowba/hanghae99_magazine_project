package com.sparta.magazine.service;

import com.sparta.magazine.dto.UserRequestDto;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    
    // 비밀번호 암호화
    private final PasswordEncoder passwordEncoder;
    // 유저 저장소
    private final UserRepository userRepository;

    // 회원가입
    @Transactional
    public void createUser(UserRequestDto userRequestDto) {

        // 유저네임 유효성 확인
        if(!Pattern.matches("^[a-zA-Z0-9]{3,20}$", userRequestDto.getUsername())) {
            throw new IllegalArgumentException("유저네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성해야 합니다.");
        }

        // 비밀번호 유효성 확인
        if(userRequestDto.getPassword().contains(userRequestDto.getUsername())) {
            throw new IllegalArgumentException("비밀번호는 닉네임을 포함하지 못합니다.");
        }

        if(userRequestDto.getPassword().length() < 4) {
            throw new IllegalArgumentException("비밀번호는 최소 4자 이상입니다.");
        }

        if(!Objects.equals(userRequestDto.getPassword(), userRequestDto.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
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
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    // 유저네임 중복 확인
    private void usernameIsExist(User user) {
        Optional<User> findUsername = userRepository.findByUsername(user.getUsername());
        if(findUsername.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }
    
    // 회원 삭제
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
