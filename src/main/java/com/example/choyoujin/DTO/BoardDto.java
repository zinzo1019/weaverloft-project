package com.example.choyoujin.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BoardDto {
    private int id;
    private String name; // 게시판 이름
    private String user; // 게시판 주인
    private int cnt; // 게시글 개수
    private String role; // 게시판 권한
    private int boardId; // 부모 게시판 아이디
    private int level; // 깊이

    private int postId; // 게시글 번호
    private String title; // 게시글 제목
    private LocalDate createDate; // 게시글 생성 날짜
    private LocalTime createTime; // 게시글 생성 시간
    private String postWriter; // 게시글 작성자


    public BoardDto() {
    }

    public BoardDto(String name) {
        this.name = name;
    }
}
