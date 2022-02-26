package com.sparta.magazine.service;

import com.sparta.magazine.dto.LikeRequestDto;
import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.model.Board;
import com.sparta.magazine.model.Likelist;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.BoardRepository;
import com.sparta.magazine.repository.LikelistRepository;
import com.sparta.magazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.sparta.magazine.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final BoardRepository boardRepository;
    private final LikelistRepository likelistRepository;
    private final UserRepository userRepository;

    // 좋아요 생성하기
    @Transactional
    public void createLike(Long board_id, LikeRequestDto likeRequestDto){

        // 좋아요를 이미 눌렀는지 확인
        Optional<Likelist> checkLikelist = likelistRepository.findLikelistsByBoard_IdAndUser_Id(board_id, likeRequestDto.getUserId());
        if(checkLikelist.isPresent()){
            throw new ErrorCodeException(LIKE_EXIST);
        }

        // 유저 존재 확인
        User user = userRepository.findById(likeRequestDto.getUserId()).orElseThrow(
                () -> new ErrorCodeException(USER_NOT_FOUND));

        // 게시판 존재 확인
        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new ErrorCodeException(BOARD_NOT_FOUND));

        Likelist likelist = Likelist.builder()
                        .board(board)
                        .user(user)
                        .build();

        likelist.SetUser(user);
        likelist.SetBoard(board);
        likelistRepository.save(likelist);
    }

    // 좋아요 삭제하기
    @Transactional
    public void deleteLike(Long board_id, LikeRequestDto likeRequestDto){

        // 좋아요 유무 확인
        Optional<Likelist> likelist = likelistRepository.findLikelistsByBoard_IdAndUser_Id(board_id, likeRequestDto.getUserId());
        if(!likelist.isPresent()){
            throw new ErrorCodeException(LIKE_EXIST);
        }

        // 키를 두개 사용해서 해당 데이터를 찾아 삭제하기.
        likelistRepository.deleteLikelistByBoard_IdAndUser_Id(board_id, likeRequestDto.getUserId());
    }
}
