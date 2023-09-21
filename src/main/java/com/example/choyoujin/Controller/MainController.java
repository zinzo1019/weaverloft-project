package com.example.choyoujin.Controller;

import com.example.choyoujin.DTO.*;
import com.example.choyoujin.Service.BoardService;
import com.example.choyoujin.Service.PostServiceImpl;
import com.example.choyoujin.Service.UserService;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    PostServiceImpl postService;
    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;

    /**
     * 메인 페이지
     */
    @RequestMapping({"/", "/main", "/{role}"})
    public String root(@RequestParam(name = "id", required = false) Integer id,
                       @RequestParam(defaultValue = "1") int page,
                       Model model) throws Exception {

        boardService.getAllBoardList(model); // 모델에 게시판 리스트 담기
        getUserSession(model); // 모델에 사용자 리스트 담기

        Pagination pagination = getPagination();
        Page<PostDto> list = null;

        if (id == null || id == 0) { // 게시판 선택하기 전 (최근 게시물)
            model.addAttribute("id", 0);
            list = postService.findAllByRole("ROLE_GUEST", page, 5);
            pagination.setTotalCount(postService.countByRole("ROLE_GUEST"));
            model.addAttribute("board", new BoardDto());
        } else { // 게시판 선택 후
            model.addAttribute("id", id);
            list = postService.findAllByBoardId(id, page, 5);
            pagination.setTotalCount(postService.countByBoardId(id));
            model.addAttribute("board", boardService.findById(id));
        }
        model.addAttribute("pagination", pagination);
        model.addAttribute("list", list);

        return "guest/main";
    }

    /** 게시글 검색 */
    @PostMapping("/search")
    public String root(@RequestParam(name = "id", required = false) Integer id, // 게시판 아이디
                       @RequestParam(defaultValue = "1") int page, Model model, String keyword) throws Exception {
        getUserSession(model); // 모델에 사용자 리스트 담기
        Pagination pagination = getPagination(); // 페이징 처리
        String role = "ROLE_GUEST"; role = getUserRole(role); // 사용자 권한 받아오기 (없으면 ROLE_GUEST)

        if (id == 0 || id == null) { // 게시판 선택 전 (최근 게시글)
            pagination.setTotalCount(postService.countByKeywordByGuest(keyword));
            Page<PostDto> list = postService.searchPosts(keyword, page, 5); // 최근 게시글 중 검색하기
            model.addAttribute("list", list); // 검색된 게시글 리스트

        } else { // 게시판 선택 후
            pagination.setTotalCount(postService.countByKeywordByBoardId(id, keyword));
            Page<PostDto> list = postService.searchPosts(id, keyword, page, 5); // 최근 게시글 중 검색하기
            model.addAttribute("list", list); // 검색된 게시글 리스트
        }
        model.addAttribute("board", new BoardDto("검색된 게시글"));
        model.addAttribute("pagination", pagination); // 페이징 처리
        model.addAttribute("role", role); // 권한 부여
        return "guest/searchResult";
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

    /** 사용자 권한 받아오기 */
    private String getUserRole(String role) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            role = userService.findUserByEmail(email).getRole();
        }
        return role;
    }

    /** Pagination 생성 */
    private static Pagination getPagination() {
        Pagination pagination = new Pagination();
        pagination.setPageRequest(new PageRequest());
        return pagination;
    }

}
