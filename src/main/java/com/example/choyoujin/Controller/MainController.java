package com.example.choyoujin.Controller;

import com.example.choyoujin.ApiResponse;
import com.example.choyoujin.DAO.PostDao;
import com.example.choyoujin.DTO.BoardDto;
import com.example.choyoujin.DTO.PostDto;
import com.example.choyoujin.DTO.UserDto;
import com.example.choyoujin.Service.BoardService;
import com.example.choyoujin.Service.UserService;
import com.example.choyoujin.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    PostDao PostDao;
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;

    /** 메인 페이지 (게시판 선택 전) */
    @RequestMapping({"/", "/main", "/board/main"})
    public String root(Model model) {
        boardService.getAllBoardList(model); // 모델에 게시판 리스트 담기
        getUserSession(model);

        List<PostDto> guestPosts = postService.findAllByRole("ROLE_GUEST");

        System.out.println(guestPosts);

        model.addAttribute("list", guestPosts);
        model.addAttribute("board", new BoardDto());
        return "guest/main";
    }

    /** 접근 제한 페이지 */
    @RequestMapping("/roleError")
    public String roleError(Model model) {
        // 사용자 역할이 없는 경우, alert 메시지를 설정하고 "alert" 페이지로 이동
        String alertMessage = "접근이 제한됐습니다.";
        String redirectUrl = "/main"; // 리디렉션할 URL

        // alert 메시지를 JavaScript 변수로 전달
        model.addAttribute("alertMessage", alertMessage);
        // 리디렉션 URL을 JavaScript 변수로 전달
        model.addAttribute("redirectUrl", redirectUrl);

        return "roleError";
    }

    /** 메인 페이지 (게시판 선택 후) */
    @GetMapping("/{role}/board")
    public String guestBoardPage(@RequestParam("id") int id, Model model) {
        boardService.getAllBoardList(model); // 모델에 게시판 리스트 담기
        getUserSession(model); model.addAttribute("id", id);

         // 게시판 번호로 게시글 리스트 가져오기
        List<PostDto> list = postService.findAllByBoardId(id);
        // 게시판 번호로 게시판 정보 가져오기
        BoardDto boardDto = boardService.findById(id);

        model.addAttribute("list", list);
        model.addAttribute("board", boardDto);
        return "guest/main";
    }

    private void getUserSession(Model model) {
        // 사용자 정보
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            UserDto userDto = userService.findUserByEmail(email);
            model.addAttribute("user", userDto);
        }
    }

}
