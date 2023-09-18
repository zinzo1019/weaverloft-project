package com.example.choyoujin.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDto {
    private int id;
    private String content;
    private String email;
    private String name; // 사용자 이름
    private String like;
    private int postId; // 게시글 번호
    private int commentId; // 댓글 아이디 (대댓글의 경우)
    private int level; // 레벨
//    private List<CommentDto> replies = new ArrayList<>(); // 대댓글 리스트

    // 사용자 이미지
    private int imageId;
    private byte[] picByte;
    private String type;
    private String encoding;
}
