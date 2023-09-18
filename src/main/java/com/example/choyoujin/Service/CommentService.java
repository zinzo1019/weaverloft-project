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
//            dto.setEncoding(decompressBytes(dto.getPicByte())); // 이미지 압축 해제
            commentDtoList.add(dto);
        }
        return commentDtoList;
    }

    /** 댓글 번호로 대댓글 리스트 가져오기 */
    public List<CommentDto> findAllByCommentId(int commentId) {
        List<CommentDto> commentDtos = commentDao.findAllByCommentId(commentId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (CommentDto dto : commentDtos) {
//            dto.setEncoding(decompressBytes(dto.getPicByte())); // 이미지 압축 해제
            commentDtoList.add(dto);
        }
        return commentDtoList;
    }

    /** 대댓글 저장하기 */
    public void saveReply(CommentDto commentDto) {
        commentDao.saveReply(commentDto);
    }

    public List<CommentDto> getAllComments(int postId) {
        // 최상위 댓글 가져오기
        List<CommentDto> rootComments = findAllByPostId(postId);
        List<CommentDto> allComments = new ArrayList<>();

        // 각 최상위 댓글부터 재귀적으로 댓글과 대댓글을 가져옵니다.
        for (CommentDto rootComment : rootComments) {
            retrieveCommentsRecursively(rootComment, allComments);
        }

        System.out.println(allComments);



        return allComments;
    }

    private void retrieveCommentsRecursively(CommentDto parentComment, List<CommentDto> allComments) {
        List<CommentDto> childComments = findAllByCommentId(parentComment.getId());
        for (CommentDto childComment : childComments) {
            allComments.add(childComment);
            retrieveCommentsRecursively(childComment, allComments);
        }
    }
    
}
