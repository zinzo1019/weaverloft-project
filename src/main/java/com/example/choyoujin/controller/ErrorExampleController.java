package com.example.choyoujin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ROLE_GUEST")
public class ErrorExampleController {

    /** 예외 발생 테스트 controller */
    @GetMapping("/simulateException")
    public String simulateException() throws Exception {
        // 예외를 강제로 발생시키는 예제
        throw new Exception("예외가 발생했습니다.");
    }

}
