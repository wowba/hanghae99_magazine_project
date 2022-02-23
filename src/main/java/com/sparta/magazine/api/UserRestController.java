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

//    // 유저 생성하기 (JSON)
//    @PostMapping("/api/register")
//    public ResponseEntity<UserSuccess> createUser(@RequestBody UserRequestDto userRequestDto){
//        userService.createUser(userRequestDto);
//        return new ResponseEntity<>(new UserSuccess("success", "회원 가입 성공하였습니다."), HttpStatus.OK);
//    }

    // 유저 생성하기 (Form DATA)
    @PostMapping("/api/register")
    public ResponseEntity<Success> createUser(UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
        return new ResponseEntity<>(new Success("success", "회원 가입 성공하였습니다."), HttpStatus.OK);
    }

    // 로그인페이지 이동시 로그인 유무 판별 (prinicpal 활용)
//    @GetMapping ("/user/login")
//    public ResponseEntity<Success> loginUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if(!(userDetails == null)){
//            throw new IllegalArgumentException("이미 로그인을 한 사용자입니다.");
//        }
//        return null; // 로그인을 하지 않았다면 문제가 없다.
//    }

    // 로그인 성공 후 페이지 이동시 프론트에게 데이터 전달 (prinicipal 활용)
//    @PostMapping("/")
//    public ResponseEntity<LoginSuccess> sendUserData(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (!(userDetails == null)) {
//            UserResponseDto userData = UserResponseDto.builder()
//                .userId(userDetails.getUser().getId())
//                .username(userDetails.getUser().getUsername())
//                .email(userDetails.getUser().getEmail())
//                .build();
//            return new ResponseEntity<>(new LoginSuccess("success", "로그인 성공하였습니다.", userData), HttpStatus.OK);}
//        return null; // 로그인을 하지 않았다면 보내는 데이터가 없다.
//    }

    // 유저 삭제하기(연관관계 테스트용 기능 / 좋아요 <- 게시판 <- 유저)
    @DeleteMapping("/api/register/{id}")
    public ResponseEntity<Success> deleteUser(@PathVariable Long id){

        // 로그인 유무 체크
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.toString());
        if (Objects.equals(principal.toString(), "anonymousUser")){
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }

        userService.deleteUser(id);
        return new ResponseEntity<>(new Success("success", "회원 삭제 완료."), HttpStatus.OK);
    }
}