package com.example.choyoujin.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UserDto {
//    회원가입 시 ajax로 받는 변수
    private Long id;
    private String email;
    private String name;
    private String gender;
    private String pw;
    private String birth;
    private String address;
    private String phone;
    private MultipartFile image;
    private String role;

//    다른 로직 변수
    private int imageId;
    private int enabled;
    private byte[] picByte;
    private String type;
}
