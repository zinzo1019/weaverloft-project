package com.example.choyoujin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignInController {
    /** 로그인 페이지 */
    @RequestMapping("/loginForm")
    public String loginForm() {
        return "guest/loginForm";
    }
}
