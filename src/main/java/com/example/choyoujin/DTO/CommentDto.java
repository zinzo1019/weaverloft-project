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

    // 사용자 이미지
    private int imageId;
    private byte[] picByte;
    private String type;
    private String encoding;

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", like='" + like + '\'' +
                ", postId=" + postId +
                ", commentId=" + commentId +
                ", level=" + level +
                '}';
    }
}
