package com.example.choyoujin.DTO;

public class Comment {
    private int id;
    private String content;
    private int commentId; // 부모 댓글의 ID
    private int postId;   // 게시글의 ID
    private int level;    // 계층 레벨 (댓글: 0, 대댓글: 1, 대대댓글: 2, ...)

    // Getter와 Setter 메서드 생략
}