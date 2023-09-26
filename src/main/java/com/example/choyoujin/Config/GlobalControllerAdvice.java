package com.example.choyoujin.Config;

import com.example.choyoujin.DTO.UserDto;
import com.example.choyoujin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserService userService;

//    /** 최근 API 호출 기록 저장 */
//    @ModelAttribute
//    public void globalModelAttribute(HttpServletRequest request) {
//        try {
//            // 사용자 정보를 조회
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String email = ((UserDetails) principal).getUsername(); // 사용자 이메일
//            UserDto userDto = userService.findUserByEmail(email); // 이메일로 사용자 찾기
//            // 사용자의 last_activity_date를 현재 날짜로 업데이트
//            if (userDto != null) {
//                Date currentActivityDate = new Date();
//                userService.updateUserLastActivityDate(userDto.getEmail(), currentActivityDate); // 마지막 활동 날짜 저장
//            }
//        } catch (Exception e) {
//            System.out.println("로그인을 해주세요.");
//        }
//    }

}
