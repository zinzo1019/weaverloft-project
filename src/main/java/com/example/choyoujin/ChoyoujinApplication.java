package com.example.choyoujin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@MapperScan // 특정 빈을 찾지 못할 때
@EnableScheduling // 스케줄링을 활성화
public class ChoyoujinApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChoyoujinApplication.class, args);
    }

}
