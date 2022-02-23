package com.sparta.magazine.controller_for_test;

import com.sparta.magazine.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 정상적인 로그인이 되었는지 테스트를 위한 뷰 컨트롤러.
    // 프론트와 협업시에는 아래와 같이 데이터를 전달하지 않을테니..
    // 모르겠네용
    
    // 메인 페이지
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        model.addAttribute("username", userDetails.getUsername());
        return "index";
    }
}
