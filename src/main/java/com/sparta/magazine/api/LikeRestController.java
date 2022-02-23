package com.sparta.magazine.api;

import com.sparta.magazine.model.responseEntity.Success;
import com.sparta.magazine.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class LikeRestController {

    private final LikeService likeService;

    // 좋아요 생성하기
    @PostMapping("/api/board/{board_id}/like")
    public ResponseEntity<Success> createLike(@PathVariable Long board_id){

        // 로그인 유무 체크
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(principal.toString(), "anonymousUser")){
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }

        likeService.createLike(board_id);
        return new ResponseEntity<>(new Success("success",  "(" + board_id + ") 좋아요 추가 성공!"), HttpStatus.OK);
    }

    // 좋아요 삭제하기
    @DeleteMapping("/api/board/{board_id}/like")
    public ResponseEntity<Success> deleteLike(@PathVariable Long board_id){

        // 로그인 유무 체크
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(principal.toString(), "anonymousUser")){
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }

        likeService.deleteLike(board_id);
        return new ResponseEntity<>(new Success("success","(" + board_id + ") 좋아요 삭제 성공!"), HttpStatus.OK);
    }

}
