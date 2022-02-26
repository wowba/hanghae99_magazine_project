package com.sparta.magazine.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_FORBIDDEN);

//        System.out.println("TESTING");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Map<String, Object> data = new HashMap<>();
//
//        data.put("result", "fail");
//        data.put("msg", "토큰이 만료되었거나 적절하지 않습니다.");
//
//        response.setContentType("text/html; charset=UTF-8"); // 보낼 때 한글 인코딩
//        response.setCharacterEncoding("UTF-8");
//        ServletOutputStream out = response.getOutputStream();
//        out.write(objectMapper.writeValueAsString(data).getBytes("UTF-8"));
    }
}
