package com.sparta.magazine.security;

import com.sparta.magazine.exception.CustomAccessDeniedHandler;
import com.sparta.magazine.exception.CustomAuthenticationEntryPoint;
import com.sparta.magazine.jwt.JwtAuthenticationFilter;
import com.sparta.magazine.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 서버가 기동할때 설정 시작.
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**")
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**")
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**");
    }

    // 토근 생성 및 제공자 DI.
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean // 비밀번호 암호화 해서 저장하는 Bean 등록하기.
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    // authenticationManager를 Bean 등록하기.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // JWT 강의 -start
        http
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
                    .csrf().disable() // csrf 보안 토큰 disable처리.
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
                .and()
                    // CORS 설정 파일은 WebConfig!!
                    .cors()
                .and()
//                     403, 401 에러 핸들링 테스트중
//                    .exceptionHandling()
//                    .authenticationEntryPoint(customAuthenticationEntryPoint) // 401 에러 핸들러
//                    .accessDeniedHandler(customAccessDeniedHandler) // 403 에러 핸들러 <- 동작하지 않는다?
//                .and()
                
                    .authorizeRequests() // 요청에 대한 사용권한 체크
//                    .antMatchers("/admin/**").hasRole("ADMIN")

                    // 게시판 가져오는 기능만 허가하기
                    .antMatchers(HttpMethod.GET, "/api/board").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/board/{id}").permitAll()
                    .antMatchers("/api/board/**").hasRole("USER")

                    // 토큰 로그아웃 이슈가 존재한다...

                    .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

                    // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }
}