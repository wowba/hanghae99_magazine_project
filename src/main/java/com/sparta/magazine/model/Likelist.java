package com.sparta.magazine.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likelist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

//    @ManyToOne
    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonManagedReference
    private Board board;
    
    // 좋아요 생성자
    @Builder
    public Likelist(User user, Board board){
        this.user = user;
        this.board = board;
    }

    // 연관관계 편의 메소드 (유저)
    public void SetUser(User user) {
        this.user = user;
        user.getLikeList().add(this);
    }
    // 연관관계 편의 메소드 (게시글)
    public void SetBoard(Board board) {
        this.board = board;
        board.getLikeList().add(this);
    }
}