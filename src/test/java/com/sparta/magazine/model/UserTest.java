package com.sparta.magazine.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

        @Test
        @DisplayName("정상 케이스")
    void createUser() {

        //given - 뭘 비교하라고 줄 것인가
        Long userId = 77L;

        String email = "leeyuwk@naver.com";
        String username = "wowba";
        String password = "1123";

        List<Board> boardList = new ArrayList<>();
        List<User> userList = new ArrayList<>();

        // when - 엣지 케이스 설정
        User user = User.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();

        // then - 어떻게 비교할 것인가.
        assertNull(user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(new ArrayList<>(), user.getBoardList());
        assertEquals(new ArrayList<>(), user.getLikeList());
    }
}