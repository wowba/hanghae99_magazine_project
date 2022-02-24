package com.sparta.magazine.security;

import com.sparta.magazine.exception.CustomLogoutSuccessHandler;
import com.sparta.magazine.exception.LoginFailureHandler;
import com.sparta.magazine.exception.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 서버가 기동할때 설정 시작.
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean // 비밀번호 암호화 해서 저장하기.
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http.csrf() // POST 기능 제한!
                .ignoringAntMatchers("/user/**") // 로컬 테스트용

                // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
                .ignoringAntMatchers("/api/**");


        http.authorizeRequests()

                    // 회원 관리 처리 API 전부를 login 없이 허용 - 테스트용
                    .antMatchers("/user/**").permitAll()

                    // 우선 모든 api 열어보기
                    .antMatchers("/api/**").permitAll()

                    // 회원 관리 처리 API 전부를 login 없이 허용 - 실전용
                    .antMatchers("/api/register").permitAll()
                    .antMatchers("/api/login").permitAll()

                    // 게시판 확인 열어주기
                    .antMatchers("/api/board/{id}").permitAll()
                    .antMatchers("/api/board").permitAll()

                    // 스웨거 열어주기
                    .antMatchers("/swagger-ui/**").permitAll()
                    .antMatchers("/swagger-resources/**").permitAll()
                    .antMatchers("/webjars/**").permitAll()
                    .antMatchers("/v2/api-docs").permitAll()

                    // 어떤 요청이든 '인증 시도'
                    .anyRequest().authenticated()
                .and()
                // 로그인 기능 허용
                    .formLogin()
                    // 로그인 뷰 제공 (GET /user/login)
                    .loginPage("/user/login") // 협업에 필요없음. 로컬 테스트용.
                    // 로그인 처리(POST /api/login)
                    .loginProcessingUrl("/api/login") // 협업 로그인 api 주소
                    // 로그인 성공 핸들러
                    .successHandler(new LoginSuccessHandler())
                    // 로그인 실패 핸들러
                    .failureHandler(new LoginFailureHandler())

                    .permitAll()
                .and()
                // 로그아웃 기능 허용
                    .logout()
                    // 로그아웃 URL - POST로 요청해야 한다!!
                    .logoutUrl("/api/logout") //  로그아웃 api 주소
                    .invalidateHttpSession(true) // 세션 제거  
                    .deleteCookies("JSESSIONID") // 쿠키 제거
                    .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                    .permitAll();
    }
}