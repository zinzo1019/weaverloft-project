package com.example.choyoujin.Auth;

import com.example.choyoujin.DTO.LoginLogDto;
import com.example.choyoujin.Entity.User;
import com.example.choyoujin.Service.LoginLogService;
import com.example.choyoujin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String userEmail = authentication.getName(); // 사용자 이메일
        // 사용자의 last_activity_date를 현재 날짜로 업데이트
        if (userEmail != null) {
            Date currentActivityDate = new Date();
            userService.updateUserLastActivityDate(userEmail, currentActivityDate); // 마지막 활동 날짜 저장
        }
        LoginLogDto logDto = new LoginLogDto();
        logDto.setEmail(userEmail); logDto.setLoginDate(LocalDateTime.now()); // 사용자 이메일과 현재 시간 세팅
        loginLogService.saveLoginLog(logDto); // 로그 저장 서비스를 호출하여 로그를 저장합니다.
        response.sendRedirect("/ROLE_GUEST"); // 로그인 성공 후 리다이렉션
    }

}