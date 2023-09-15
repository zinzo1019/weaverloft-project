package com.example.choyoujin.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 사용자 정의 클래스 - 에러 처리
 * 사용자 인증 실패 시의 여러 가지 상황에 따른 에러 메세지를 구분해서 출력
 * 로그인 실패 횟수를 기록해서 몇 회 이상이면 일정 시간 더 이상 로그인 시도를 못하게 막는 등의 처리
 */
@Configuration
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String loginid = request.getParameter("email");
        String errormsg = "";

        if (exception instanceof BadCredentialsException) {
            loginFailureCount(loginid);
            errormsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            loginFailureCount(loginid);
            errormsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof DisabledException) {
            errormsg = "계정이 비활성화되었습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof CredentialsExpiredException) {
            errormsg = "비밀번호 유효기간이 만료되었습니다. 관리자에게 문의하세요.";
        }

        request.setAttribute("email", loginid);
        request.setAttribute("error_message", errormsg);

        request.getRequestDispatcher("/loginForm?error=true").forward(request, response);
    }

    // 비밀번호 3번 이상 틀릴 시 계정 잠금 처리
    protected void loginFailureCount(String email) {
//        // 틀린 횟수 업데이트
//        userDao.countFailure(email);
//        // 틀린 횟수 조회
//        int cnt = userDao.checkFailureCount(email);
//        if (cnt==3) {
//            // 계정 잠금 처리
//            userDao.disabledEmail(email);
//        }
    }

}
