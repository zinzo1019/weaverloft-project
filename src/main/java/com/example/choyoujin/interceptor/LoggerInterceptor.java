package com.example.choyoujin.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoggerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(); // 세션 가져오기
        if (session != null) {
            long sessionCreationTime = session.getCreationTime(); // 세션 생성 시간
            long currentTime = System.currentTimeMillis(); // 현재 시간 (밀리초)

            // 세션 만료 시간을 30분
            long sessionTimeoutMillis = 30 * 60 * 10000;

//            System.out.println("세션 생성 시간: " + sessionCreationTime);
//            System.out.println("현재 시간: " + currentTime);
//            System.out.println("현재 시간 - 세션 생성 시간: " + (currentTime - sessionCreationTime));
//            System.out.println("세션 만료 시간: " + sessionTimeoutMillis);

            if (currentTime - sessionCreationTime >= sessionTimeoutMillis) {
                // 세션 만료 시 로그아웃 처리 또는 다른 작업 수행
                System.out.println("세션 만료로 인한 로그아웃 처리");
                session.invalidate(); // 세션 무효화
                // 로그아웃 후 리다이렉트 등의 처리 가능
                response.sendRedirect("/loginForm"); // 로그아웃 후 로그인 페이지로 리다이렉트
                return false; // 세션 만료 시 이후의 컨트롤러 작업을 중지
            }
        }
        return true; // 세션이 유효하면 컨트롤러 작업 계속 진행
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
