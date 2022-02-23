package com.sparta.magazine.repository;

import com.sparta.magazine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    
    // 회원가입시 이메일 중복 확인
    Optional<User> findByEmail(String email);
    // 회원가입시 유저네임 중복 확인
    Optional<User> findByUsername(String username);
    // 연관관계 편의 메소드용 유저 찾기.
    User findUserByUsername(String username);
}
