package com.sparta.magazine.api;

import com.sparta.magazine.dto.UserRequestDto;
import com.sparta.magazine.model.responseEntity.Success;
import com.sparta.magazine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    // 유저 생성하기 (JSON)
    @PostMapping("/api/register")
    public ResponseEntity<Success> createUser(@RequestBody UserRequestDto userRequestDto){
        System.out.println(userRequestDto);
        userService.createUser(userRequestDto);
        return new ResponseEntity<>(new Success("success", "회원 가입 성공하였습니다."), HttpStatus.OK);
    }

//    // 유저 생성하기 (Form DATA)
//    @PostMapping("/api/register")
//    public ResponseEntity<Success> createUser(UserRequestDto userRequestDto){
//        userService.createUser(userRequestDto);
//        return new ResponseEntity<>(new Success("success", "회원 가입 성공하였습니다."), HttpStatus.OK);
//    }

    // 유저 삭제하기(연관관계 테스트용 기능 / 좋아요 <- 게시판 <- 유저)
    @DeleteMapping("/api/register/{id}")
    public ResponseEntity<Success> deleteUser(@PathVariable Long id){

        userService.deleteUser(id);
        return new ResponseEntity<>(new Success("success", "회원 삭제 완료."), HttpStatus.OK);
    }
}