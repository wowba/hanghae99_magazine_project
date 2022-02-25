package com.sparta.magazine.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Map<String, Object> data = new HashMap<>();
//
//        data.put("result", "fail");
//        data.put("msg", "로그인이 필요한 서비스입니다.");
//
//        response.setContentType("text/html; charset=UTF-8"); // 보낼 때 한글 인코딩
//        response.setCharacterEncoding("UTF-8");
//        ServletOutputStream out = response.getOutputStream();
//        out.write(objectMapper.writeValueAsString(data).getBytes("UTF-8"));
    }
}
