package com.example.choyoujin.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        return "roleError"; // 에러 페이지로 리다이렉트
    }
}
