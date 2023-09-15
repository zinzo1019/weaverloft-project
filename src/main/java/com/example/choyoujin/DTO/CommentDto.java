package com.example.choyoujin.DTO;

import lombok.Data;

@Data
public class CommentDto {
    private int id;
    private String content;
    private String email;
    private String name; // 사용자 이름
    private String like;
    private int postId; // 게시글 번호

    // 사용자 이미지
    private int imageId;
    private byte[] picByte;
    private String type;
    private String encoding;
}
