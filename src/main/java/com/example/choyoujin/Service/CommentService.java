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
        System.out.println("댓글을 저장했습니다.");
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
}
