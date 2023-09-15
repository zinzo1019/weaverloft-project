package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.BoardDao;
import com.example.choyoujin.DTO.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardDao boardDao;

    /** 게시판 리스트 가져오기 */
    public List<BoardDto> findBoardList() {
        return boardDao.findBoardList();
    }

    /** 권한에 따라 게시판 리스트 가져오기 */
    public List<BoardDto> findBoardListByRole(String role) {
        return boardDao.findBoardListByRole(role);
    }


    /** 권한마다 게시판 리스트 가져오기 */
    public List<BoardDto> findTotalBoardListByRole() {
        List<BoardDto> dtoList = boardDao.findBoardListByRole("ROLE_GUEST");
        List<BoardDto> dtoList1 = boardDao.findBoardListByRole("ROLE_MEMBER");
        List<BoardDto> dtoList2 = boardDao.findBoardListByRole("ROLE_ADMIN");
        dtoList.addAll(dtoList1); dtoList.addAll(dtoList2);
        return dtoList;
    }

    /** 권한마다 게시판 리스트 가져오기 */
    public void getAllBoardList(Model model) {
        // 게시판 리스트
        List<BoardDto> guestbBoards = findBoardListByRole("ROLE_GUEST");
        List<BoardDto> memberBoards = findBoardListByRole("ROLE_USER");
        List<BoardDto> adminBoards = findBoardListByRole("ROLE_ADMIN");

        model.addAttribute("guestbBoards", guestbBoards);
        model.addAttribute("memberBoards", memberBoards);
        model.addAttribute("adminBoards", adminBoards);
    }

    public BoardDto findById(int id) {
        return boardDao.findById(id);
    }
}
