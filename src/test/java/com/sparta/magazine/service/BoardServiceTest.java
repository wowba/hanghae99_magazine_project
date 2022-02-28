package com.sparta.magazine.service;

import com.sparta.magazine.dto.BoardRequestDto;
import com.sparta.magazine.dto.BoardResponseDto;
import com.sparta.magazine.dto.LikeResponseDto;
import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.jwt.JwtTokenProvider;
import com.sparta.magazine.model.Board;
import com.sparta.magazine.model.Likelist;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.BoardRepository;
import com.sparta.magazine.repository.LikelistRepository;
import com.sparta.magazine.repository.UserRepository;
import com.sparta.magazine.validator.LoginValidator;
import com.sparta.magazine.validator.UserValidator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.magazine.exception.ErrorCode.BOARD_NOT_FOUND;
import static com.sparta.magazine.exception.ErrorCode.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    private String username;
    private String imageUrl;
    private String grid;
    private String content;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;


    @BeforeEach // 각 테스트 실행 전 자동으로 설정한다.
    void setup() {
        username = "wowba";
        imageUrl = "image.com";
        grid = "column";
        content = "backendisking";
    }

    @Test
    @Order(1)
    @DisplayName("게시판 생성하기")
    void createBoard() {

        // given
        BoardRequestDto boardRequestDto = new BoardRequestDto(username, imageUrl, grid, content);

        // when
        Long id = boardService.createBoard(boardRequestDto);
        Board made = boardRepository.findById(id).orElseThrow(() -> new ErrorCodeException(BOARD_NOT_FOUND));

        // then
        assertEquals(username, made.getUsername());
    }

    @Test
    @Order(2)
    @DisplayName("상세 게시판 가져오기")
    void getBoard() {

        // given
        BoardRequestDto boardRequestDto = new BoardRequestDto(username, imageUrl, grid, content);
        Long id = boardService.createBoard(boardRequestDto);

        // when
        BoardResponseDto boardResponseDto = boardService.getBoard(id);

        // then
        assertNotNull(boardResponseDto);
    }

    @Test
    @Order(3)
    @DisplayName("전체 게시판 중 첫 페이지 가져오기")
    void getAllBoard() {

        // given

        int page = 1;
        int size = 5;
        String sortBy = "id";

        // when

        // 모든 게시글 가져오기 (무한스크롤 적용)
        page = page - 1; // DB에선 0부터 찾기 때문에 이렇게 해줘야 한다.
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Board> boardList = boardRepository.findAll(pageable);

        // then
        assertTrue(boardList.isFirst());
    }

    @Test
    @Order(4)
    @DisplayName("게시판 수정하기")
    void editBoard() {

        // given
        BoardRequestDto boardRequestDto1 = new BoardRequestDto(username, imageUrl, grid, content);
        Long id = boardService.createBoard(boardRequestDto1);

        content = "Edit Board";
        BoardRequestDto boardRequestDto2 = new BoardRequestDto(username, imageUrl, grid, content);

        // when
        boardService.editBoard(id, boardRequestDto2);
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new ErrorCodeException(BOARD_NOT_FOUND));

        // then
        assertEquals(content, board.getContent());
    }

    @Test
    @Order(5)
    @DisplayName("게시판 삭제하기")
    void deleteBoard() {

        // given
        BoardRequestDto boardRequestDto = new BoardRequestDto(username, imageUrl, grid, content);
        Long id = boardService.createBoard(boardRequestDto);

        // when
        boardRepository.deleteById(id);

        // then
        assertEquals(Optional.empty(), boardRepository.findById(id));
    }
}