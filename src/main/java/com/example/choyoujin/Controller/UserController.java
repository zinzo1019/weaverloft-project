package com.example.choyoujin.Controller;

import com.example.choyoujin.ApiResponse;
import com.example.choyoujin.DAO.UserDao;
import com.example.choyoujin.DTO.UserDto;
import com.example.choyoujin.Service.FileService;
import com.example.choyoujin.Service.MailService;
import com.example.choyoujin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import static com.example.choyoujin.Service.FileService.decompressBytes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    /** 마이 페이지 */
    @GetMapping("/myPage")
    public String myPage(Model model) {
        // 사용자 정보
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();

        UserDto userDto = userService.findUserByEmail(email); // 이메일로 사용자 찾기
        userDto.setPicByte(decompressBytes(userDto.getPicByte())); // 이미지 압축 풀기
        String encoding = Base64.getEncoder().encodeToString(userDto.getPicByte()); // img로 띄우기 위해 인코딩
        model.addAttribute("userDto", userDto);
        model.addAttribute("encoding", encoding);
        return "user/myPage";
    }

    /** 마이 페이지 수정 */
    @PostMapping("/updateUser")
    public ResponseEntity<ApiResponse> updateUser(UserDto userDto) throws IOException {
        System.out.println("userDto.name is " + userDto.getName());
        try {
            userService.updateUser(userDto);
            return ResponseEntity.ok(new ApiResponse("수정했습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("수정에 실패했습니다. 다시 시도해주세요."));
        }
    }

    /** 사용자 리스트 페이지 */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userListPage(Model model) {
        model.addAttribute("users", userDao.list());
        return "admin/userlist";
    }

}
