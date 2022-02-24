package com.sparta.magazine.api;

import com.sparta.magazine.dto.UserRequestDto;
import com.sparta.magazine.jwt.JwtTokenProvider;
import com.sparta.magazine.model.User;
import com.sparta.magazine.model.responseEntity.LoginSuccess;
import com.sparta.magazine.model.responseEntity.Success;
import com.sparta.magazine.repository.UserRepository;
import com.sparta.magazine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 유저 생성하기 (JSON)
    @PostMapping("/api/register")
    public ResponseEntity<Success> createUser(@RequestBody UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
        return new ResponseEntity<>(new Success("success", "회원 가입 성공하였습니다."), HttpStatus.OK);
    }

//    // 유저 생성하기 (Form DATA)
//    @PostMapping("/api/register")
//    public ResponseEntity<Success> createUser(UserRequestDto userRequestDto){
//        userService.createUser(userRequestDto);
//        return new ResponseEntity<>(new Success("success", "회원 가입 성공하였습니다."), HttpStatus.OK);
//    }

    // 로그인
    @PostMapping("/api/login")
    public ResponseEntity<LoginSuccess> loginUser(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("username"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
        return new ResponseEntity<>(new LoginSuccess("success", "로그인 성공", token), HttpStatus.OK);
    }


    // 유저 삭제하기(연관관계 테스트용 기능 / 좋아요 <- 게시판 <- 유저)
    @DeleteMapping("/api/register/{id}")
    public ResponseEntity<Success> deleteUser(@PathVariable Long id){

        userService.deleteUser(id);
        return new ResponseEntity<>(new Success("success", "회원 삭제 완료."), HttpStatus.OK);
    }
}