package com.sparta.magazine.security;

import com.sparta.magazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 우리는 email로 로그인을 하므로 username으로 들어온 email을 기반으로 해 찾아야 한다.
        // 즉 여기서의 username은 유저가 입력한 이메일이다.
        // username 이라고 변수명을 쓰는건 스프링 시큐리티와의 약속이라 어쩔 수 없다.
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
//        return new UserDetailsImpl(user);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }
}