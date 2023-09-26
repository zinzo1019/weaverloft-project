package com.example.choyoujin.DAO;

import com.example.choyoujin.DTO.FileDto;
import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.PageRequest;
import com.example.choyoujin.DTO.PostDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostDao {
    List<PostDto> listDao(); // 전체 게시글 가져오기 (리스트)
    PostDto viewDao(int id); // id로 게시글 가져오기 (개별뷰)
    int deletePostById(String id); // 게시글 삭제하기
    int articleCount(); // 게시글 개수 세기

    List<PostDto> findAllByBoardId(int boardId, int page, int size); // 게시판 번호로 게시글 가져오기

    int countByBoardId(int boardId); // 게시판 번호로 게시글 개수 세기

    List<PostDto> findAllByRole(String role, int page, int size);

    int writeDao(PostDto postDto); // 게시글 저장하기

    void saveImages(ImageDto imageDto);

    List<ImageDto> findAllImagesByPostId(@Param("postId") int postId);

    int findMaxPostId();

    void saveFile(FileDto fileDto); // 파일 저장하기

    List<FileDto> findAllFilesByPostId(int postId);
    FileDto findFileById(int id); // 아이디로 파일 가져오기
    int count();
    int countByRole(String role);
    List<PostDto> list(PageRequest pageRequest);

    List<PostDto> findPostsByPage(int page, int size);

    List<PostDto> searchPosts(String keyword, int page, int size); // 게시글 검색하기
    List<PostDto> searchPostsByBoardId(int boardId, String keyword, int page, int size); // 게시판 아이디로 게시글 검색하기

    int countByKeywordByGuest(String keyword);
    int countByKeywordByBoardId(int boardId, String keyword);

    void modifyPost(PostDto postDto);

    List<PostDto> findAllByBoardIdWithNotPaging(int boardId);

//    List<PostDto> findAllByBoardId(int boardId); // 게시판 아이디로 게시글 리스트 가져오기
}
