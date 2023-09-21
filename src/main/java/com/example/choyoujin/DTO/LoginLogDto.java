package com.example.choyoujin.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginLogDto {
    private Long id;
    private String email;
    private LocalDateTime loginDate;
}
