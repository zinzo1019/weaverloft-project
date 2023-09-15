package com.example.choyoujin.DAO;

import com.example.choyoujin.DTO.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    void saveComment(CommentDto commentDto); // 댓글 저장하기

    List<CommentDto> findAllByPostId(int postId);
}
