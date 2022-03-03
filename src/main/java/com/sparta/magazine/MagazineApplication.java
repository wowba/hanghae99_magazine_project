package com.sparta.magazine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 저장소 수정 가능하게
//@EnableCaching // 캐시 가능하게 하려고 Bean 을 따로 등록하면 에러가 난다!
@SpringBootApplication
public class MagazineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagazineApplication.class, args);
    }
}
