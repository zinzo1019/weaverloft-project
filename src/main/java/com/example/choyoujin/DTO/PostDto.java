package com.example.choyoujin.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostDto {
    private int id;
    private String writer;
    private String title;
    private String content;
    List<MultipartFile> images;
    List<MultipartFile> files;
    private String role; // 공개 범위
    private int boardId; // 게시판 아이디
    private String boardName; // 게시판 이름
}
