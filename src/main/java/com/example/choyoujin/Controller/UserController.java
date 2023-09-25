package com.example.choyoujin.Controller;

import com.example.choyoujin.ApiResponse;
import com.example.choyoujin.DAO.UserDao;
import com.example.choyoujin.DTO.UserDto;
import com.example.choyoujin.Service.BoardService;
import com.example.choyoujin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.example.choyoujin.Service.FileService.decompressBytes;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private BoardService boardService;

    /** 사용자 정보 가져오기 */
    @GetMapping("/ROLE_ADMIN/session")
    @ResponseBody
    public UserDto session() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername(); // 사용자 이메일
        UserDto userDto = userService.findUserByEmail(email); // 이메일로 사용자 찾기
        return userDto;
    }

    /** 마이 페이지 */
    @GetMapping("/{role}/myPage")
    public String myPage(Model model) {
        // 사용자 정보
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserDto userDto = userService.findUserByEmail(email); // 이메일로 사용자 찾기

        if (userDto.getType() != null) { // 이미지가 있다면
            String encoding = decompressBytes(userDto.getPicByte()); // 이미지 압축 풀기
            model.addAttribute("encoding", encoding);
        }
        model.addAttribute("userDto", userDto);
        return "user/myPage";
    }

    /** 마이 페이지 수정 */
    @PostMapping("/{role}/updateUser")
    public ResponseEntity<ApiResponse> updateUser(UserDto userDto) {
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

    /** 게시판 관리 페이지 */
    @GetMapping(value = "/ROLE_ADMIN/board")
    public String manageBoardPage(Model model) {
        // 게시판 정보 가져오기
        boardService.getAllBoardList(model); // 모델에 게시판 리스트 담기
        return "admin/board";
    }

}
