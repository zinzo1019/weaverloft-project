package com.example.choyoujin.controller;

import com.example.choyoujin.ApiResponse;
import com.example.choyoujin.DTO.UserDto;
import com.example.choyoujin.Service.FileService;
import com.example.choyoujin.Service.MailService;
import com.example.choyoujin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/guest")
public class FindController {
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @Autowired
    MailService mailService;

    String tempPw = null; // 임시 비밀번호
    String Exemail = null;

    /** 이메일 찾기 페이지 */
    @GetMapping("/findEmail")
    public String findEmailPage() {
        return "guest/findEmail";
    }

    /** 이메일 찾기 로직 */
    @PostMapping("/findEmail")
    public ResponseEntity<ApiResponse> findEmail(@RequestParam Map<String, String> userData) {
        String name = userData.get("name");
        String phone = userData.get("phone");
        try {
            // 같은 전화번호를 가진 사용자의 데이터 가져오기
            UserDto userDto = userService.findUserByPhone(phone);
            if (userDto.getName().equals(name)) { // 이름이 일치한다면
                return ResponseEntity.ok(new ApiResponse("이메일: " + userDto.getEmail()));
            } else { // 이름이 일치하지 않는다면
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("이름 또는 핸드폰 번호가 맞지 않습니다. 다시 확인해주세요."));
            }
        } catch (Exception c) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("조회 실패"));
        }
    }

    /** 비밀번호 찾기 페이지 */
    @GetMapping("/findPw")
    public String findPwPage(String email) {
        return "guest/findPw";
    }

    /** 비밀번호 찾기 - 임시 비밀번호 전송 */
    @PostMapping("/findPw")
    public ResponseEntity<ApiResponse> sendTempPw(String email) {
        Exemail = email;
        // 임시 비밀벝호 전송
        tempPw = mailService.sendTempPw(email);
        if (tempPw != null) {
            return ResponseEntity.ok(new ApiResponse("임시 비밀번호를 메일로 전송했습니다. 새 비밀번호로 변경해주세요."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("메일 전송에 실패했습니다."));
        }
    }

    /** 비밀번호 변경 페이지 */
    @GetMapping("/updatePw")
    public String updatePwPage() {
        return "guest/updatePw";
    }

    /** 임시 비밀번호 확인 페이지 */
    @PostMapping("/checkTempPw")
    public ResponseEntity<ApiResponse> checkTempPw(String tempPwCheck) {
        // 임시 비밀번호와 tempPw 비교
        if (tempPw.equals(tempPwCheck)) {
            return ResponseEntity.ok(new ApiResponse("임시 비밀번호가 확인됐습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("잘못된 비밀번호입니다. 다시 확인해주세요."));
        }
    }

    /** 비밀번호 변경 로직 */
    @PostMapping("/updatePw")
    public ResponseEntity<ApiResponse> updatePw(String pw) {
        try {
            String email = Exemail;
            userService.updatePw(email, pw); // 업데이트
            return ResponseEntity.ok(new ApiResponse("비밀번호를 변경했습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("비밀번호 변경에 실패했습니다."));
        }
    }
}
