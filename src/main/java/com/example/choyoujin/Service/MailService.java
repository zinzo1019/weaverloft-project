package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendMail(String email) {
        // 이메일 발송 로직
        String verifyCode = generateRandomCode(6);
        sendConfirmationEmail(email, verifyCode);
        System.out.println("verifyCode is " + verifyCode);
        return verifyCode;
    }

    /** 이메일 내용 생성 & 이메일 전송 */
    private void sendConfirmationEmail(String toEmail, String verifyCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("인증번호: " + verifyCode);
        javaMailSender.send(mailMessage);
    }

    public String sendTempPw(String email) {
        String verifyCode = generateRandomCode(6); // 임시 비밀번호 발급
        sendTempEmail(email, verifyCode);
        System.out.println("임시 비밀번호는 " + verifyCode);
        return verifyCode;
    }

    /** 이메일 내용 생성 & 이메일 전송 */
    private void sendTempEmail(String toEmail, String verifyCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("임시 비밀번호");
        mailMessage.setText("비밀번호: " + verifyCode);
        javaMailSender.send(mailMessage);
    }

    /** 인증 번호 발급 */
    private String generateRandomCode(int length) {
        String characters = "0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

}
