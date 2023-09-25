package com.example.choyoujin.DAO;

import com.example.choyoujin.DTO.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {
    List<BoardDto> findBoardList();

    List<BoardDto> findBoardListByRole(String role);

    BoardDto findById(int id);

    void saveParentBoard(BoardDto boardDto);

    void saveChildBoard(BoardDto boardDto);

    List<BoardDto> findAll(String role);

    void deleteByBoardId(Integer boardId);
}
