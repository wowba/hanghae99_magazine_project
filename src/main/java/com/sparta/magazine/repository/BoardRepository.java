package com.sparta.magazine.repository;

import com.sparta.magazine.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 기존 모든 게시글 한번에 가져오는 용도
//    List<Board> findAll();


}
