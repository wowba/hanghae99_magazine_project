package com.sparta.magazine.controller_for_test;

import com.sparta.magazine.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class UserController {

    // 테스트를 위한 뷰 컨트롤러.
    // 프론트와 협업시 아래와 같이 로그인 페이지, 회원가입 페이지 접근을 막으면 되지 않을까요?

    // 로그인 페이지
    @GetMapping("/user/login")
    public String login(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!(userDetails == null)) {
            throw new IllegalArgumentException("이미 로그인이 되어있습니다.");
        }
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!(userDetails == null)) {
            throw new IllegalArgumentException("이미 로그인이 되어있습니다.");
            }
        return "signup";
        }
    }

