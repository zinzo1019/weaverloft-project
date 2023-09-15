package com.example.choyoujin.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageDto {
    private int id;
    private String name;
    private String type;
    private byte[] picByte;
    private int postId; // 게시글 아이디
    private String encoding; // 압축 해제한 이미지 파일

    public ImageDto(String name, String type, byte[] picByte) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    public ImageDto(int id, String name, String type, byte[] picByte) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    public ImageDto(String name, String type, byte[] picByte, int postId) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
        this.postId = postId;
    }
}
