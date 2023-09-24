package com.example.choyoujin.Controller;

import com.example.choyoujin.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("ROLE_ADMIN")
public class BoardController {

    @Autowired
    private BoardService boardService;

    /** 게시판 추가 */
    @PostMapping("/board/save")
    public void saveBoard(@RequestParam(name = "boardId", required = false) Integer boardId,
                          @RequestParam String board, @RequestParam String role) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); // 사용자 이메일
        if (boardId == null) { // 최상위 게시판
            boardService.saveParentBoard(board, email, role);
        } else { // 하위 게시판
            boardService.saveChildBoard(board, boardId, email, role);
        }
    }

}
