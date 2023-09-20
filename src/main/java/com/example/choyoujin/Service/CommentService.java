package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.CommentDao;
import com.example.choyoujin.DTO.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.choyoujin.Service.FileService.decompressBytes;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    /** 댓글 저장하기 */
    public void saveComment(CommentDto commentDto) {
        commentDao.saveComment(commentDto);
    }

    /** 게시글 아이디로 댓글 리스트 가져오기 */
    public List<CommentDto> findAllByPostId(int postId) {
        List<CommentDto> commentDtos = commentDao.findAllByPostId(postId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (CommentDto dto : commentDtos) {
            dto.setEncoding(decompressBytes(dto.getPicByte())); // 이미지 압축 해제
            commentDtoList.add(dto);
        }
        return commentDtoList;
    }

    /** 대댓글 저장하기 */
    public void saveReply(CommentDto commentDto) {
        commentDao.saveReply(commentDto);
    }

    /** 부모 댓글의 레벨 가져오기 */
    public int getCommentLevel(int id) {
        return commentDao.getCommentLevel(id);
    }

    /** 댓글 번호로 댓글 정보 가져오기 */
    public CommentDto findById(int id) {
        return commentDao.findById(id);
    }

    /** 게시글 대댓글 작성 시 CommentDto 세팅 */
    public void setCommentDto(CommentDto commentDto, int replyId, String email) {
        CommentDto parentDtao = findById(replyId); // 부모 댓글의 레벨
        commentDto.setLevel(parentDtao.getLevel() + 1); // 자식 댓글의 레벨 = 부모 댓글의 레벨 + 1
        commentDto.setPostId(parentDtao.getPostId()); // 자식 댓글의 레벨 = 부모 댓글의 레벨 + 1
        commentDto.setCommentId(replyId);
        commentDto.setEmail(email); // 사용자 아이디 & 댓글 아이디 세팅
    }
}
