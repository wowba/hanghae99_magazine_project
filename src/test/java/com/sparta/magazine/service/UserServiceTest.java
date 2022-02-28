package com.sparta.magazine.service;

import com.sparta.magazine.dto.UserRequestDto;
import com.sparta.magazine.dto.UserResponseDto;
import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.jwt.JwtTokenProvider;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.UserRepository;
import com.sparta.magazine.validator.LoginValidator;
import com.sparta.magazine.validator.UserValidator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.sparta.magazine.exception.ErrorCode.LOGIN_PASSWORD_NOT_MATCH;
import static com.sparta.magazine.exception.ErrorCode.LOGIN_USER_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    private String email;
    private String username;
    private String password;
    private String passwordCheck;

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private LoginValidator loginValidator;

    @BeforeEach // 각 테스트 실행 전 자동으로 설정한다.
    void setup() {
        email = "test@test.com";
        username = "test";
        password = "1123";
        passwordCheck = "1123";
    }

    @Nested
    @DisplayName("회원가입 테스트")
    class createUser {
        @Test
        @Order(1)
        @DisplayName("정상 케이스")
        void createUser() {

            // given
            UserRequestDto userRequestDto = new UserRequestDto(
                    email,
                    username,
                    password,
                    passwordCheck
            );

            // when
            // 유효성 검사 패키지
            userValidator.validateUserInput(userRequestDto);

            // 비밀번호 암호화
            String password = passwordEncoder.encode(userRequestDto.getPassword());

            // 유저 생성하기
            User user = User.builder()
                    .email(userRequestDto.getEmail())
                    .username(userRequestDto.getUsername())
                    .password(password)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();

            userRepository.save(user);

            // then
            assertEquals(email, user.getEmail());
            assertEquals(username, user.getUsername());
            assertEquals(password, user.getPassword());
        }

        @Nested
        @DisplayName("실패 케이스")
        class FailCases {
            @Nested
            @DisplayName("회원 유저네임 에러")
            class usernameCheck {
                @Test
                @Order(2)
                @DisplayName("유저네임 공백")
                void fail1() {

                    // given
                    username = "";
                    UserRequestDto userRequestDto = new UserRequestDto(
                            email,
                            username,
                            password,
                            passwordCheck
                    );

                    // when
                    // 유효성 검사 패키지
                    Exception exception = assertThrows(ErrorCodeException.class, ()
                            -> userValidator.validateUserInput(userRequestDto));

                    // then
                    assertEquals(exception.getClass(), ErrorCodeException.class);
                }
                @Test
                @Order(3)
                @DisplayName("유저네임 중복")
                void fail2() {

                    // given
                    username = "wowba";
                    UserRequestDto userRequestDto = new UserRequestDto(
                            email,
                            username,
                            password,
                            passwordCheck
                    );

                    // when
                    // 유효성 검사 패키지
                    Exception exception = assertThrows(ErrorCodeException.class, ()
                            -> userValidator.validateUserInput(userRequestDto));

                    // then
                    assertEquals(exception.getClass(), ErrorCodeException.class);
                }
            }
            @Nested
            @DisplayName("회원 이메일 에러")
            class emailCheck {
                @Test
                @Order(4)
                @DisplayName("이메일 중복")
                void fail1() {

                    // given
                    email = "leeyuwk@naver.com";
                    UserRequestDto userRequestDto = new UserRequestDto(
                            email,
                            username,
                            password,
                            passwordCheck
                    );

                    // when
                    // 유효성 검사 패키지
                    Exception exception = assertThrows(ErrorCodeException.class, ()
                            -> userValidator.validateUserInput(userRequestDto));

                    // then
                    assertEquals(exception.getClass(), ErrorCodeException.class);
                }
            }
            @Nested
            @DisplayName("회원 비밀번호 에러")
            class passwordCheck {
                @Test
                @Order(5)
                @DisplayName("비밀번호 유효성 에러")
                void fail1() {
                    // given
                    password = "";
                    UserRequestDto userRequestDto = new UserRequestDto(
                            email,
                            username,
                            password,
                            passwordCheck
                    );

                    // when
                    // 유효성 검사 패키지
                    Exception exception = assertThrows(ErrorCodeException.class, ()
                            -> userValidator.validateUserInput(userRequestDto));

                    // then
                    assertEquals(exception.getClass(), ErrorCodeException.class);
                }
                @Test
                @Order(6)
                @DisplayName("비밀번호 일치 에러")
                void fail2() {
                    // given
                    password = "12345";
                    passwordCheck = "";
                    UserRequestDto userRequestDto = new UserRequestDto(
                            email,
                            username,
                            password,
                            passwordCheck
                    );

                    // when
                    // 유효성 검사 패키지
                    Exception exception = assertThrows(ErrorCodeException.class, ()
                            -> userValidator.validateUserInput(userRequestDto));

                    // then
                    assertEquals(exception.getClass(), ErrorCodeException.class);
                }
            }
        }
    }
    @Nested
    @DisplayName("회원가입 후 로그인 테스트")
    class loginUser {
        @Test
        @Order(7)
        @DisplayName("로그인 성공시 토큰 발급")
        void loginUserSuccess() {

            // given
            // 회원가입 하기
            UserRequestDto userRequestDto = new UserRequestDto(
                    email,
                    username,
                    password,
                    passwordCheck
            );

            // 유효성 검사 패키지
            userValidator.validateUserInput(userRequestDto);

            // 비밀번호 암호화
            String password = passwordEncoder.encode(userRequestDto.getPassword());

            // 유저 생성하기
            User user = User.builder()
                    .email(userRequestDto.getEmail())
                    .username(userRequestDto.getUsername())
                    .password(password)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();

            userRepository.save(user);

            // 로그인하는 유저
            Map<String, String> userlogin = new HashMap<>();
            userlogin.put("email", "test@test.com");
            userlogin.put("password", "1123");

            // when
            User member = userRepository.findByEmail(userlogin.get("email"))
                    .orElseThrow(() -> new ErrorCodeException(LOGIN_USER_NOT_FOUND));
            if (!passwordEncoder.matches(userlogin.get("password"), member.getPassword())) {
                throw new ErrorCodeException(LOGIN_PASSWORD_NOT_MATCH);
            }

            UserResponseDto userResponseDto = UserResponseDto.builder()
                    .email(member.getEmail())
                    .username(member.getUsername())
                    .userId(member.getId())
                    .token(jwtTokenProvider.createToken(member.getUsername(), member.getRoles()))
                    .build();

            String token = userResponseDto.getToken();

            // then
            assertThat(token).isNotEmpty();
        }
        @Nested
        @DisplayName("로그인 실패 테스트")
        class loginFail {
            @Test
            @Order(8)
            @DisplayName("이메일 불일치")
            void loginUserFail1() {
                // given
                // 회원가입 하기
                UserRequestDto userRequestDto = new UserRequestDto(
                        email,
                        username,
                        password,
                        passwordCheck
                );

                userValidator.validateUserInput(userRequestDto);

                String password = passwordEncoder.encode(userRequestDto.getPassword());

                User user = User.builder()
                        .email(userRequestDto.getEmail())
                        .username(userRequestDto.getUsername())
                        .password(password)
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build();

                userRepository.save(user);

                // 로그인하는 유저
                Map<String, String> loginRequestDto = new HashMap<>();
                loginRequestDto.put("email", "not_exist");
                loginRequestDto.put("password", "1123");

                // when
                // 이메일 일치 확인
                Exception exception = assertThrows(ErrorCodeException.class, ()
                        -> loginValidator.validateLoginInput(loginRequestDto));

                // then
                assertEquals(exception.getClass(), ErrorCodeException.class);

            }
            @Test
            @Order(9)
            @DisplayName("비밀번호 불일치")
            void loginUserFail2() {
                // given
                // 회원가입 하기
                UserRequestDto userRequestDto = new UserRequestDto(
                        email,
                        username,
                        password,
                        passwordCheck
                );

                userValidator.validateUserInput(userRequestDto);

                String password = passwordEncoder.encode(userRequestDto.getPassword());

                User user = User.builder()
                        .email(userRequestDto.getEmail())
                        .username(userRequestDto.getUsername())
                        .password(password)
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build();

                userRepository.save(user);

                // 로그인하는 유저
                Map<String, String> loginRequestDto = new HashMap<>();
                loginRequestDto.put("email", "test@test.com");
                loginRequestDto.put("password", "not_match");

                // when
                Exception exception = assertThrows(ErrorCodeException.class, ()
                        -> loginValidator.validateLoginInput(loginRequestDto));

                // then
                assertEquals(exception.getClass(), ErrorCodeException.class);
            }
        }
    }
}