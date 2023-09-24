package com.example.choyoujin.DTO;

import lombok.Data;

@Data
public class BoardDto {
    private int id;
    private String name; // 게시판 이름
    private String user; // 게시판 주인
    private int cnt; // 게시글 개수
    private String role; // 게시판 권한
    private int boardId; // 부모 게시판 아이디
    private int level; // 깊이


    public BoardDto() {
    }

    public BoardDto(String name) {
        this.name = name;
    }
}
