package com.sparta.magazine.repository;

import com.sparta.magazine.model.Likelist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LikelistRepository extends JpaRepository<Likelist, Long> {

    // 좋아요 삭제 전 유무 확인용
    Optional<Likelist> findLikelistByBoard_IdAndUser_Id(Long board_id, Long user_id);
    // 좋아요 삭제용
    void deleteLikelistByBoard_IdAndUser_Id(Long board_id, Long user_id);
    // 상세페이지 좋아요 불러오기용
    List<Likelist> findLikelistsByBoard_Id(Long board_id);
}
