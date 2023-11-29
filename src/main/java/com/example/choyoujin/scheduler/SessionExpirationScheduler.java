package com.example.choyoujin.scheduler;

import com.example.choyoujin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class SessionExpirationScheduler {

    @Autowired
    private UserService userService;

    /** 60일 간격 휴면 처리 */
//    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void processDormantAccounts() {
        Date currentDate = new Date();
        System.out.println("1분마다 실행됩니다. 현재 날짜: " + currentDate);

        // 60일 이상 활동이 없는 사용자 조회
        Date dormantThresholdDate = calculateDormantThresholdDate();
        List<String> emails = userService.findDormantUsers(dormantThresholdDate);

        // 휴면 계정 처리 로직을 여기에 작성합니다.
        for (String email : emails) {
            System.out.println(email + " 계정을 휴면 처리했습니다.");
            userService.updateEnabled(email);
        }
    }

    private Date calculateDormantThresholdDate() {
        Calendar calendar = Calendar.getInstance(); // 현재 날짜
        calendar.add(Calendar.DAY_OF_MONTH, -1); // 현재 날짜 - 60일
        Date thresholdDate = calendar.getTime();
        return thresholdDate;
    }

}
