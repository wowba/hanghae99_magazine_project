package com.sparta.magazine.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("정상 케이스")
    void createUser_Normal() {
        //given
        String email = "leeyuwk@naver.com";

        String username = "wowba";

        String password = "1123";

        List<Board> boardList = new ArrayList<>();

        List<Likelist> likeList = new ArrayList<>();

        User user = User.builder()
                .email(email)
                .username(username)
                .password(password)
                .boardList(boardList)
                .likeList(likeList).build();
    }
}