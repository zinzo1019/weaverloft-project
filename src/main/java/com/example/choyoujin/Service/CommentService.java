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

//    public List<CommentDto> getAllComments(int postId) {
//        List<CommentDto> rootComments = findAllByPostId(postId); // 최상위 댓글 가져오기
//        List<CommentDto> allComments = new ArrayList<>(); // 댓글 전체를 담을 리스트
//        for (CommentDto rootComment : rootComments) {
//            CommentDto commentDto = retrieveCommentsRecursively(rootComment); // 재귀함수 호출
//            allComments.add(commentDto); // 대댓글 리스트 담기
//        }
//        return allComments;
//    }

//    private CommentDto retrieveCommentsRecursively(CommentDto parentComment) {
//        List<CommentDto> childComments = findAllByCommentId(parentComment.getId()); // 댓글 아이디로 대댓글 리스트 가져오기
//        parentComment.setReplies(childComments); // 대댓글 리스트로 초기화
//        for (CommentDto childComment : childComments) { // 다시 재귀함수 호출
//            retrieveCommentsRecursively(childComment);
//        }
//        return parentComment; // 대댓글 리스트
//    }

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

//    /** 댓글 번호로 대댓글 리스트 가져오기 */
//    public List<CommentDto> findAllByCommentId(int commentId) {
//        List<CommentDto> commentDtos = commentDao.findAllByCommentId(commentId);
//        List<CommentDto> commentDtoList = new ArrayList<>();
//        for (CommentDto dto : commentDtos) {
//            dto.setEncoding(decompressBytes(dto.getPicByte())); // 이미지 압축 해제
//            commentDtoList.add(dto);
//        }
//        return commentDtoList;
//    }

    /** 대댓글 저장하기 */
    public void saveReply(CommentDto commentDto) {
        commentDao.saveReply(commentDto);
    }

}
