package com.sparta.magazine.service;

import com.sparta.magazine.dto.LikeRequestDto;
import com.sparta.magazine.model.Board;
import com.sparta.magazine.model.Likelist;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.LikelistRepository;
import com.sparta.magazine.validator.LikeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikelistRepository likelistRepository;
    private final LikeValidator likeValidator;

    // 좋아요 생성하기
    @Transactional
    public void createLike(Long board_id, LikeRequestDto likeRequestDto){
        
        // 유효성 검사
        List<Object> userAndBoard = likeValidator.validateCreateLike(board_id, likeRequestDto);
        User user = (User) userAndBoard.get(0);
        Board board = (Board) userAndBoard.get(1);
        // 좋아요 생성
        Likelist likelist = Likelist.builder()
                        .board((Board) userAndBoard.get(1))
                        .user((User) userAndBoard.get(0))
                        .build();

        likelist.SetUser(user);
        likelist.SetBoard(board);
        likelistRepository.save(likelist);
    }

    // 좋아요 삭제하기
    @Transactional
    public void deleteLike(Long board_id, LikeRequestDto likeRequestDto){
        
        // 유효성 검사
        likeValidator.validateDeleteLike(board_id, likeRequestDto);
        // 키를 두개 사용해서 해당 데이터를 찾아 삭제하기.
        likelistRepository.deleteLikelistByBoard_IdAndUser_Id(board_id, likeRequestDto.getUserId());
    }
}
