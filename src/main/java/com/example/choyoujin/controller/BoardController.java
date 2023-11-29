package com.example.choyoujin.controller;

import com.example.choyoujin.DTO.PostDto;
import com.example.choyoujin.Service.BoardService;
import com.example.choyoujin.Service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("ROLE_ADMIN")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private PostServiceImpl postService;

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

    /** 게시판 삭제 */
    @PostMapping("/board/delete")
    public void saveBoard(@RequestParam(name = "boardId", required = false) Integer boardId) {
        try {
            boardService.deleteByBoardId(boardId); // 게시판 삭제하기
        } catch (Exception e) {
            System.out.println("게시판 삭제를 실패했습니다: " + boardId);
        }
    }

    /** 게시판 내부에 게시글이 있으면 true 없으면 false */
    @GetMapping("/isPost")
    public ResponseEntity<String> isPost(@RequestParam("boardId") int boardId, Model model) {
        List<PostDto> postDtos = postService.findAllByBoardIdWithNotPaging(boardId);
        if (!postDtos.isEmpty()) // 게시글이 있다면
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(""); // error
        else // 게시글이 없다면
            return ResponseEntity.ok("");
    }
}
