package com.sparta.magazine.service;

import com.sparta.magazine.dto.LikeRequestDto;
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

@Service
@RequiredArgsConstructor
public class LikeService {

    private final BoardRepository boardRepository;
    private final LikelistRepository likelistRepository;
    private final UserRepository userRepository;

    // 좋아요 생성하기
    @Transactional
    public void createLike(Long board_id, LikeRequestDto likeRequestDto){

        // 추가 전 유무 확인
        Optional<Likelist> checkLikelist = likelistRepository.findLikelistsByBoard_IdAndUser_Id(board_id, likeRequestDto.getUserId());
        if(checkLikelist.isPresent()){
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        User user = userRepository.findById(likeRequestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("좋아요를 누른 유저가 존재하지 않습니다."));

        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new IllegalArgumentException("좋아요를 누른 게시판이 존재하지 않습니다."));

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

        // 삭제 전 유무 확인
        Optional<Likelist> likelist = likelistRepository.findLikelistsByBoard_IdAndUser_Id(board_id, likeRequestDto.getUserId());
        if(!likelist.isPresent()){
            throw new IllegalArgumentException("삭제하려는 좋아요가 존재하지 않습니다.");
        }

        // 키를 두개 사용해서 해당 데이터를 찾아 삭제하기.
        likelistRepository.deleteLikelistByBoard_IdAndUser_Id(board_id, likeRequestDto.getUserId());
    }
}
