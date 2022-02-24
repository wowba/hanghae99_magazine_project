package com.sparta.magazine.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.magazine.dto.UserResponseDto;
import com.sparta.magazine.security.UserDetailsImpl;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetailsImpl userDetails = (UserDetailsImpl)principal;

        String email = userDetails.getUser().getEmail();

        String username = userDetails.getUser().getUsername();

        Long userId = userDetails.getUser().getId();
        String strUserId = Long.toString(userId);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> data = new HashMap<>();

        // session 넣어주기
//        Object session = request.getSession();
        HttpSession session = request.getSession();
//        Object session = authentication.getDetails();

        Cookie cookieEmail = new Cookie("email", email);
        Cookie cookieUsername = new Cookie("username", username);
        Cookie cookieUserId = new Cookie("userId", strUserId);

        response.addCookie(cookieEmail);
        response.addCookie(cookieUserId);
        response.addCookie(cookieUsername);

        UserResponseDto userResponseDto = UserResponseDto.builder()
                                            .email(email)
                                            .userId(userId)
                                            .username(username)
                                            .build();

        data.put("result", "success");
        data.put("msg", "로그인 성공");
        data.put("userData", userResponseDto);

        response.setContentType("text/html; charset=UTF-8"); // 보낼 때 한글 인코딩
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.write(objectMapper.writeValueAsString(data).getBytes("UTF-8"));
    }
}