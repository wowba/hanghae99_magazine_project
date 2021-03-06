package com.sparta.magazine.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.magazine.dto.BoardRequestDto;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String imageUrl;

    @Column
    private String grid;

    @Column
    private String content;

    @ManyToOne // 게시글은 여러개, 계정은 하나. 게시글은 곧 연관관계의 주인!
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL})
    private List<Likelist> likeList = new ArrayList<>();

    // 게시글 생성자
    @Builder
    public Board (String username, String imageUrl, String grid, String content) {
        Assert.hasText(username, "유저네임은 공란일 수 없습니다.");
        Assert.hasText(imageUrl, "이미지 주소는 공란일 수 없습니다.");
        Assert.hasText(grid, "그리드는 공란일 수 없습니다.");
        Assert.hasText(content, "내용은 공란일 수 없습니다.");
        
        this.username = username;
        this.imageUrl = imageUrl;
        this.grid = grid;
        this.content = content;
    }

    // 게시글 수정하기
    public void Edit(BoardRequestDto boardRequestDto) {
        this.content = boardRequestDto.getContent();
        this.grid = boardRequestDto.getGrid();
        this.imageUrl = boardRequestDto.getImageUrl();
    }

    // 연관관계 편의 메소드
    public void SetUser(User user){
        this.user = user;
        user.getBoardList().add(this);
    }
}
