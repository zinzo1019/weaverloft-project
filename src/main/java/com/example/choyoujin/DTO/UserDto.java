package com.example.choyoujin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserDto {
//    회원가입 시 ajax로 받는 변수
    private Long id;
    private String email;
    private String name;
    private String gender;
    private String pw;
    private LocalDate birth;
    private String address;
    private String phone;
    private MultipartFile image;
    private String role;
    private LocalDate createDate;

    private String birth_string;

//    다른 로직 변수
    private int imageId;
    private int enabled;
    private byte[] picByte;
    private String type;

    public UserDto(String email, String name, String gender, String pw, LocalDate birth, String address, String phone) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.pw = pw;
        this.birth = birth;
        this.address = address;
        this.phone = phone;
    }
}
