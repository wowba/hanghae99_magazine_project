package com.sparta.magazine.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CORS

//  (Cross-Origin Resource Sharing,CORS) 란 다른 출처의 자원을 공유할 수 있도록 설정하는 권한 체제를 말합니다.
//
//  따라서 CORS를 설정해주지 않거나 제대로 설정하지 않은 경우, 원하는대로 리소스를 공유하지 못하게 됩니다.

//  SOP
//  SOP는 같은 Origin에만 요청을 보낼 수 있게 제한하는 보안 정책을 의미한다.
//  즉 같은 호스트, 같은 포트, 같은 프로토콜 에서만 접근이 가능한 것이다.
//
//  스프링 부트는 아무런 설정을 하지 않으면 SOP 정책을 따르게 된다.
//
//  백엔드인 스프링 부트는 http://localhost:8080 을 사용하였고,
//  프론트는 http://localhost:3000 을 사용하였다.
//  -> 두 Origin 간에 프로토콜, 포트, 호스트가 같아야 SOP 정책을 만족시키는데, Origin이 달라 해당 정책을 만족시키지 못하기 때문에 서버측에서 CORS를 이용하여야 한다.

// CORS 방지 / 이후 프론트와 협업하면서 수정하자!
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","DELETE","PUT");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
