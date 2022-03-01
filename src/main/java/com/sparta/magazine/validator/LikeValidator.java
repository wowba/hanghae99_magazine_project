package com.sparta.magazine.validator;

import com.sparta.magazine.dto.LikeRequestDto;
import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.model.Board;
import com.sparta.magazine.model.Likelist;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.BoardRepository;
import com.sparta.magazine.repository.LikelistRepository;
import com.sparta.magazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.magazine.exception.ErrorCode.*;

@Component // 선언하지 않으면 사용할 수 없다!!!!!
@RequiredArgsConstructor
public class LikeValidator {

    private final LikelistRepository likelistRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public List<Object> validateCreateLike(Long board_id, LikeRequestDto likeRequestDto) {
        Optional<Likelist> checkLikelist = likelistRepository.findLikelistByBoard_IdAndUser_Id(board_id, likeRequestDto.getUserId());
        if(checkLikelist.isPresent()){
            throw new ErrorCodeException(LIKE_EXIST);
        }

        // 유저 존재 확인
        User user = userRepository.findById(likeRequestDto.getUserId()).orElseThrow(
                () -> new ErrorCodeException(USER_NOT_FOUND));

        // 게시판 존재 확인
        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new ErrorCodeException(BOARD_NOT_FOUND));

        List<Object> userAndBoard = new ArrayList<>();

        userAndBoard.add(user);
        userAndBoard.add(board);

        return userAndBoard;
    }

    public void validateDeleteLike(Long board_id, LikeRequestDto likeRequestDto) {
        // 좋아요 유무 확인
        Optional<Likelist> likelist = likelistRepository.findLikelistByBoard_IdAndUser_Id(board_id, likeRequestDto.getUserId());
        if(!likelist.isPresent()){
            throw new ErrorCodeException(LIKE_EXIST);
        }
    }
}
