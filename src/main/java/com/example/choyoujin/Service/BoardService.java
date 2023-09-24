package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.BoardDao;
import com.example.choyoujin.DTO.BoardDto;
import com.example.choyoujin.DTO.PageRequest;
import com.example.choyoujin.DTO.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardDao boardDao;

    /**
     * 게시판 리스트 가져오기
     */
    public List<BoardDto> findBoardList() {
        return boardDao.findBoardList();
    }

    /**
     * 권한에 따라 게시판 리스트 가져오기
     */
    public List<BoardDto> findBoardListByRole(String role) {
        return boardDao.findBoardListByRole(role);
    }

    /**
     * 권한마다 게시판 리스트 가져오기
     */
    public List<BoardDto> findTotalBoardListByRole() {
        List<BoardDto> dtoList = boardDao.findBoardListByRole("ROLE_GUEST");
        List<BoardDto> dtoList1 = boardDao.findBoardListByRole("ROLE_MEMBER");
        List<BoardDto> dtoList2 = boardDao.findBoardListByRole("ROLE_ADMIN");
        dtoList.addAll(dtoList1);
        dtoList.addAll(dtoList2);
        return dtoList;
    }

    /**
     * 권한마다 게시판 리스트 가져오기
     */
    public void getAllBoardList(Model model) {
        List<BoardDto> guestBoards = boardDao.findAll("ROLE_GUEST");
        List<BoardDto> memberBoards = boardDao.findAll("ROLE_USER");
        List<BoardDto> adminBoards = boardDao.findAll("ROLE_ADMIN");

        model.addAttribute("guestBoards", guestBoards);
        model.addAttribute("memberBoards", memberBoards);
        model.addAttribute("adminBoards", adminBoards);
    }

    public BoardDto findById(int id) {
        return boardDao.findById(id);
    }

    /**
     * 최상위 게시판 저장하기
     */
    public void saveParentBoard(String boardName, String email, String role) {
        BoardDto boardDto = new BoardDto();
        boardDto.setName(boardName);
        boardDto.setUser(email);
        boardDto.setRole(role);// 게시판 이름 & 부모 아이디 & 관리자 세팅
        boardDao.saveParentBoard(boardDto);
    }

    /**
     * 자식 게시판 저장하기
     */
    public void saveChildBoard(String boardName, int boardId, String email, String role) {
        BoardDto boardDto = new BoardDto();
        boardDto.setName(boardName); boardDto.setBoardId(boardId); boardDto.setUser(email);// boardDto 세팅
        BoardDto parentBoard = boardDao.findById(boardDto.getBoardId()); // 부모 게시판 정보 가져오기
        boardDto.setLevel(parentBoard.getLevel() + 1); // 부모 게시판 level + 1
        boardDto.setRole(parentBoard.getRole()); // 부모 게시판의 권한
        boardDao.saveChildBoard(boardDto);
    }
}
