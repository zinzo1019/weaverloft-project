package com.example.choyoujin.controller;

import com.example.choyoujin.ApiResponse;
import com.example.choyoujin.DTO.UserDto;
import com.example.choyoujin.Exception.BadRequestException;
import com.example.choyoujin.Service.FileService;
import com.example.choyoujin.Service.MailService;
import com.example.choyoujin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/guest")
public class SignUpController {
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @Autowired
    MailService mailService;

    private String verificationCode;

    /** user 회원가입 페이지 */
    @RequestMapping("/register")
    public String registerForm() {
        return "guest/register";
    }

    /** user 회원가입 로직 */
    @RequestMapping("/register-process")
    public String registerProcess(UserDto userDto) {
        int imageId = userService.saveImageAndGetImageId(userDto); // 이미지 저장
        userService.saveUser(userDto, "ROLE_USER", 1, imageId); // 사용자 저장
        return "guest/register";
    }

    /** admin 회원가입 페이지 */
    @RequestMapping("/admin-register")
    public String adminRegisterForm() {
        return "guest/adminRegister";
    }

    /** admin 회원가입 로직 */
    @RequestMapping("/admin-register-process")
    public String adminRegisterProcess(UserDto userDto) throws IOException {
        int imageId = userService.saveImageAndGetImageId(userDto); // 이미지 저장
        userService.saveUser(userDto, "ROLE_ADMIN", 1, imageId); // 사용자 저장
        return "guest/register";
    }

    /** 이메일 중복 확인 */
    @GetMapping("/id/check")
    public ResponseEntity<String> checkIdDuplication(@RequestParam(value = "email") String email) throws BadRequestException {
        System.out.println("checkIdDuplication() 함수 실행 중...");
        if (userService.isUser(email) == true) {
            System.out.println("이미 사용 중인 아이디입니다.");
            throw new BadRequestException("이미 사용 중인 아이디입니다.");
        } else {
            System.out.println("사용 가능한 아이디입니다.");
            return ResponseEntity.ok("사용 가능한 아이디 입니다.");
        }
    }

    /** 메일 전송 */
    @RequestMapping("/send-verification")
    public ResponseEntity<ApiResponse> sendVerificationEmail(@RequestParam String email) {
        System.out.println("sendVerificationEmail() 실행 중 ... ");
        verificationCode = mailService.sendMail(email);
        return ResponseEntity.ok(new ApiResponse("인증 번호를 발송했습니다."));
    }

    /** 메일 인증 */
    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse> verifyCode(@RequestParam String code) {
        if (code.equals(verificationCode)) {
            System.out.println("인증에 성공했습니다!");
            return ResponseEntity.ok(new ApiResponse("인증 성공!"));
        } else if (!code.equals(verificationCode)){
            System.out.println("인증 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("인증번호가 일치하지 않습니다."));
        } else {
            System.out.println("메일 인증 진행 중 ... ");
            return ResponseEntity.ok(new ApiResponse("인증 진행 중 ... "));
        }
    }
}
