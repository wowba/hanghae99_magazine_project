package com.sparta.magazine.service;

import com.sparta.magazine.dto.BoardRequestDto;
import com.sparta.magazine.dto.LikeRequestDto;
import com.sparta.magazine.dto.UserRequestDto;
import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.model.Likelist;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.BoardRepository;
import com.sparta.magazine.repository.LikelistRepository;
import com.sparta.magazine.repository.UserRepository;
import com.sparta.magazine.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeServiceTest {


    private String username;
    private String imageUrl;
    private String grid;
    private String content;
    private Long boardId;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardService boardService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private LikelistRepository likelistRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Order(0)
    @BeforeEach// 각 테스트 실행 전 자동으로 설정한다.
    void setup() {

        // 테스트 게시판 생성
        username = "wowba";
        imageUrl = "image.com";
        grid = "column";
        content = "backendisking";

    }

    @Test
    @Order(1)
    @DisplayName("좋아요 만들기")
    void createLike() {

        // given
        // 유저 찾기
        User user = userRepository.findUserByUsername(username);
        LikeRequestDto likeRequestDto = new LikeRequestDto(user.getId());

        // 게시판 생성
        BoardRequestDto boardRequestDto = new BoardRequestDto(username, imageUrl, grid, content);
        boardId = boardService.createBoard(boardRequestDto);

        // when
        likeService.createLike(boardId, likeRequestDto);

        // then
        Likelist likelist = likelistRepository.findLikelistByBoard_IdAndUser_Id(boardId, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("Like does not Exist"));
        assertNotNull(likelist);
    }

    @Test
    @Order(2)
    @DisplayName("좋아요 삭제하기")
    void deleteLike() {

        // given
        // 유저 찾기
        User user = userRepository.findUserByUsername(username);
        LikeRequestDto likeRequestDto = new LikeRequestDto(user.getId());

        // 게시판 생성
        BoardRequestDto boardRequestDto = new BoardRequestDto(username, imageUrl, grid, content);
        boardId = boardService.createBoard(boardRequestDto);

        // 좋아요 추가
        likeService.createLike(boardId, likeRequestDto);
        System.out.println("좋아요 생성 성공");

        // when

        // 좋아요 삭제
//        likeService.deleteLike(boardId, likeRequestDto);
//        likelistRepository.deleteAll();
//        likelistRepository.deleteLikelistByBoard_IdAndUser_Id(boardId, likeRequestDto.getUserId());
        likelistRepository.deleteAllInBatch();
        System.out.println("제발 삭제해줘");
        List<Likelist> likelist = likelistRepository.findAll();
        System.out.println(likelist.isEmpty());
        // then
        assertTrue(likelist.isEmpty());
    }
}