package com.sparta.magazine.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // 양방향 연관관계시 객체의 참조는 둘인데 외래 키는 하나이므로 외래키를 관리할 주인을 정해야 한다.

    // failed to lazily initialize a collection of role
    // 위와 같은 문제를 급하게 해결하기 위해 EAGER를 썼지만, 추후 리팩토링 예정.
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Likelist> likeList = new ArrayList<>();

    // 게시글 생성자
    @Builder
    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}

