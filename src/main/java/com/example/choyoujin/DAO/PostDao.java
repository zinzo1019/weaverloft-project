package com.example.choyoujin.DAO;

import com.example.choyoujin.DTO.FileDto;
import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostDao {
    List<PostDto> listDao(); // 전체 게시글 가져오기 (리스트)
    PostDto viewDao(int id); // id로 게시글 가져오기 (개별뷰)
    int deletePostById(String id); // 게시글 삭제하기
    int articleCount(); // 게시글 개수 세기

    List<PostDto> findAllByBoardId(int boardId); // 게시판 번호로 게시글 가져오기

    List<PostDto> findAllByRole(String role);

    int writeDao(PostDto postDto); // 게시글 저장하기

    void saveImages(ImageDto imageDto);

    List<ImageDto> findAllByPostId(int postId);

    int findMaxPostId();

    void saveFile(FileDto fileDto); // 파일 저장하기

    List<FileDto> findAllFilesByPostId(int postId);

    FileDto findFileById(int id); // 아이디로 파일 가져오기
}
