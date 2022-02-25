package com.sparta.magazine.api;

import com.sparta.magazine.dto.LikeRequestDto;
import com.sparta.magazine.model.responseEntity.Success;
import com.sparta.magazine.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class LikeRestController {

    private final LikeService likeService;

    // 좋아요 생성하기
    @PostMapping("/api/board/{board_id}/like")
    public ResponseEntity<Success> createLike(@PathVariable Long board_id, @RequestBody LikeRequestDto likeRequestDto){

        likeService.createLike(board_id, likeRequestDto);
        return new ResponseEntity<>(new Success("success",  "(" + board_id + ") 좋아요 추가 성공!"), HttpStatus.OK);
    }

    // 좋아요 삭제하기
    @DeleteMapping("/api/board/{board_id}/like")
    public ResponseEntity<Success> deleteLike(@PathVariable Long board_id, @RequestBody LikeRequestDto likeRequestDto){
        likeService.deleteLike(board_id, likeRequestDto);
        return new ResponseEntity<>(new Success("success","(" + board_id + ") 좋아요 삭제 성공!"), HttpStatus.OK);
    }

}
