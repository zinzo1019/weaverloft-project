package com.example.choyoujin.DAO;

import com.example.choyoujin.DTO.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    void saveComment(CommentDto commentDto); // 댓글 저장하기

    List<CommentDto> findAllByPostId(int postId); // 게시글 번호로 댓글 리스트 가져오기

    void saveReply(CommentDto commentDto); // 대댓글 저장하기

    List<CommentDto> findAllByCommentId(int commentId); // 댓글 번호로 대댓글 리스트 가져오기

    List<CommentDto> selectReplies(int postId);

    int getCommentLevel(int id); // 부모 댓글의 레벨 가져오기

    CommentDto findById(int id); // 댓글 번호로 댓글 정보 가져오기
}
