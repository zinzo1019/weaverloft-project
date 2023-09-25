package com.example.choyoujin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorLogDto {
    private Long id;
    private String methodName;
    private String errorMessage;
    private String stackTrace;
    private Date timestamp = new Date();
}
