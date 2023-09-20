package com.example.choyoujin.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
public class PostDto {
    private int id;
    private String userName;
    private String email; // 작성자 이메일 (삭제 버튼 활성화 시 비교군으로 사용됨)
    private String title;
    private String content;
    List<MultipartFile> images;
    List<MultipartFile> files;
    private String role; // 공개 범위
    private int boardId; // 게시판 아이디
    private String boardName; // 게시판 이름
    private LocalDate createDate; // 생성 날짜
    private LocalTime createTime; // 생성 시간
    private LocalDate updateDate; // 수정 날짜
    private LocalTime updateTime; // 수정 날짜
}
