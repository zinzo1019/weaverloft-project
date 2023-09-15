package com.example.choyoujin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//@ComponentScan(basePackages = "com.example.choyoujin.DAO") // 특정 빈을 찾지 못할 때
@MapperScan // 특정 빈을 찾지 못할 때
public class ChoyoujinApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChoyoujinApplication.class, args);
    }

}
