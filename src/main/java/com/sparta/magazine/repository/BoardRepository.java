package com.sparta.magazine.repository;

import com.sparta.magazine.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
