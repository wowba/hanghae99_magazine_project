package com.sparta.magazine.validator;

import com.sparta.magazine.dto.BoardRequestDto;
import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.model.Board;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.BoardRepository;
import com.sparta.magazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static com.sparta.magazine.exception.ErrorCode.*;

@Component // 선언하지 않으면 사용할 수 없다!!!!!
@RequiredArgsConstructor
public class BoardValidator {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 게시판 생성시 사용
    public void validateCreateBoard(String username) {
        // 생성하려는 유저가 존재하는지 확인.
        Optional<User> findUsername = userRepository.findByUsername(username);
        if(!findUsername.isPresent()){
            throw new ErrorCodeException(USER_NOT_FOUND);
        }
    }

    // 게시판 수정시 사용
    public Board validateEditBoard(Long id, BoardRequestDto boardRequestDto) {

        // 수정하려는 유저 조회
        User user = userRepository.findByUsername(boardRequestDto.getUsername()).orElseThrow(
                () -> new ErrorCodeException(USER_NOT_FOUND));

        if(!Objects.equals(boardRequestDto.getUsername(), user.getUsername())){
            throw new ErrorCodeException(BOARD_EDIT_OR_DELETE_NOT_MATCH);
        }

        // 수정할 게시판이 존재하는지 확인
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new ErrorCodeException(BOARD_NOT_FOUND));

        return board;
    }

    // 게시판 삭제시 사용
    public void validateDeleteBoard(Long id, User user) {
        // 삭제하려는 게시판이 존재하는지 확인
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new ErrorCodeException(BOARD_NOT_FOUND));

        // 삭제하려는 유저가 작성자인지 확인.
        String username = user.getUsername();
        if(!(Objects.equals(username, board.getUsername()))) {
            throw new ErrorCodeException(BOARD_EDIT_OR_DELETE_NOT_MATCH);
        }
    }
}
