//package com.sparta.magazine.model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserTest {
//
//    @Nested
//    @DisplayName("유저 생성하기")
//    class createUser() {
//
//        String email;
//        String username;
//        String password;
//        List<Board> boardList;
//        List<Likelist> likeList;
//
//        @BeforeEach
//        void setup() {
//            email = "leeyuwk@naver.com";
//            username = "wowba";
//            password = "1123";
//            boardList = new ArrayList<>();
//            likeList = new ArrayList<>();
//        }
//
//        @Test
//        @DisplayName("정상 케이스")
//        void createUser_Normal() {
//            // given, when
//            User user = User.builder()
//                    .email(email)
//                    .username(username)
//                    .password(password)
//                    .boardList(boardList)
//                    .likeList(likeList)
//                    .build();
//
//            // then
//            assertNull(user.getId());
//            assertEquals(email, user.getEmail());
//            assertEquals(username, user.getUsername());
//            assertEquals(password, user.getPassword());
//            assertEquals(new ArrayList<>(), user.getLikeList());
//            assertEquals(new ArrayList<>(), user.getBoardList());
//        }
//
//        @Nested
//        @DisplayName("실패 케이스")
//        class FailClass {
//            @Nested
//            @DisplayName("이메일 확인")
//            class boardId{
//                @Test
//                @DisplayName("null")
//                void fail1() {
//                    // given
//                    email = null;
//                    // when
//                    User user = User.builder()
//                            .email(email)
//                            .username(username)
//                            .password(password)
//                            .boardList(boardList)
//                            .likeList(likeList)
//                            .build();
//                }
//            }
//        }
//
//    }
//}