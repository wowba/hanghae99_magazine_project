package com.sparta.magazine.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Timestamped implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    // 양방향 연관관계시 객체의 참조는 둘인데 외래 키는 하나이므로 외래키를 관리할 주인을 정해야 한다.

    // failed to lazily initialize a collection of role
    // 위와 같은 문제를 급하게 해결하기 위해 EAGER를 썼지만, 추후 리팩토링 예정.
    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Board> boardList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Likelist> likeList = new ArrayList<>();

    // 게시글 생성자
    @Builder
    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

