package com.example.choyoujin.Service;

import com.example.choyoujin.DTO.FileDto;
import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface PostService {
    void deletePostById(String id);

    Page<PostDto> findAllByBoardId(int boardId, int page, int size);

    Page<PostDto> findAllByRole(String role, int page, int size);
    /** 게시글 검색하기 */
    Page<PostDto> searchPosts(String keyword, int page, int size);
    /** 특정 게시판에서 게시글 검색하기 */
    Page<PostDto> searchPosts(int id, String keyword, int page, int size);

    /** 페이징 처리 */
    Page<PostDto> findPostsByPage(int page, int size);


    void saveImages(ImageDto imageDto);

    int findMaxPostId();

    /** 게시글 아이디로 이미지 리스트 가져오기 */
    List<ImageDto> getImageDtos(int postId);

    /** 게시글 아이디로 이미지 리스트 저장하기 */
    void saveImagesByPostId(PostDto postDto, int postId) throws IOException;

    /** 게시글 아이디로 파일 리스트 저장하기 */
    void saveFilesByPostId(PostDto postDto, int postId) throws IOException;
    /** 게시글 아이디로 파일 리스트 가져오기 */
    List<FileDto> findAllFilesByPostId(int postId);
    /** 게시판 아이디와 사용자 이름으로 PostDto 초기화 */
    PostDto setPostDtoForSave(int id, String role, PostDto postDto);
    /** 파일 아이디로 파일 가져오기 */
    FileDto findFileById(int id);
    int count();
    int countByBoardId(int boardId);
    int countByRole(String role);
    int countByKeywordByGuest(String keyword);
    int countByKeywordByBoardId(int boardId, String keyword);
}
