package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.PostDao;
import com.example.choyoujin.DTO.FileDto;
import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.choyoujin.Service.FileService.compressBytes;
import static com.example.choyoujin.Service.FileService.decompressBytes;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;
    @Autowired
    private UserService userService;

    
    @Override public void deletePostById(String id) {
        postDao.deletePostById(id);
    }

    @Override public Page<PostDto> findAllByBoardId(int boardId, int page, int size) {
        int start = (page - 1) * size;
        List<PostDto> posts = postDao.findAllByBoardId(boardId, start, size);
        int total = postDao.countByBoardId(boardId);
        return new PageImpl<>(posts, PageRequest.of(page -1, size), total);
    }

    @Override public Page<PostDto> findAllByRole(String role, int page, int size) {
        int start = (page - 1) * size;
        List<PostDto> posts = postDao.findAllByRole(role, start, size);
        int total = postDao.countByRole(role);
        return new PageImpl<>(posts, PageRequest.of(page -1, size), total);
    }

    /** 게시글 검색하기 */
    @Override public Page<PostDto> searchPosts(String keyword, int page, int size) {
        int start = (page - 1) * size;
        List<PostDto> posts = postDao.searchPosts(keyword, start, size);
        int total = postDao.countByKeywordByGuest(keyword);
        return new PageImpl<>(posts, PageRequest.of(page -1, size), total);
    }

    /** 특정 게시판에서 게시글 검색하기 */
    @Override public Page<PostDto> searchPosts(int id, String keyword, int page, int size) {
        int start = (page - 1) * size;
        List<PostDto> posts = postDao.searchPostsByBoardId(id, keyword, start, size);
        int total = postDao.countByKeywordByBoardId(id, keyword);
        return new PageImpl<>(posts, PageRequest.of(page -1, size), total);
    }

    /** 페이징 처리 */
    @Override public Page<PostDto> findPostsByPage(int page, int size) {
        int start = (page - 1) * size;
        List<PostDto> posts = postDao.findPostsByPage(start, size);
        int total = postDao.count();
        return new PageImpl<>(posts, PageRequest.of(page -1, size), total);
    }


    @Override public void saveImages(ImageDto imageDto) {
        postDao.saveImages(imageDto);
    }

    @Override public int findMaxPostId() {
        return postDao.findMaxPostId();
    }

    /** 게시글 아이디로 이미지 리스트 가져오기 */
    @Override public List<ImageDto> getImageDtos(int postId) {
        // 다중 이미지 가져오기
        List<ImageDto> images = postDao.findAllByPostId(postId); // 게시글 아이디로 다중 이미지 가져오기
        List<ImageDto> imageDtos = new ArrayList<>();

        for (ImageDto imageDto : images) {
            imageDto.setEncoding(decompressBytes(imageDto.getPicByte())); // 이미지 압축 풀기
            imageDtos.add(imageDto);
        }
        return imageDtos;
    }

    /** 게시글 아이디로 이미지 리스트 저장하기 */
    @Override public void saveImagesByPostId(PostDto postDto, int postId) throws IOException {
        List<MultipartFile> imgList = postDto.getImages();
        if (!imgList.get(0).getOriginalFilename().equals("")) { // 이미지가 들어와야 아래 로직 수행
            // 다중 이미지 업로드
            for (MultipartFile image : postDto.getImages()) {
                String name = image.getOriginalFilename(); // 파일 이름
                String type = image.getContentType(); // 파일 타입
                byte[] picByte = compressBytes(image.getBytes()); // 문자열 압축

                ImageDto imageDto = new ImageDto(name, type, picByte, postId); // ImageDto 생성
                saveImages(imageDto);
            }
        }
    }

    /** 게시글 아이디로 파일 리스트 저장하기 */
    @Override public void saveFilesByPostId(PostDto postDto, int postId) throws IOException {
        List<MultipartFile> fileList = postDto.getFiles();
        if (!fileList.get(0).getOriginalFilename().equals("")) { // 파일이 들어와야 아래 로직 수행

            List<String> fileNames = new ArrayList<>();
            for (MultipartFile file : postDto.getFiles()) {
                String uploadDir = "C:/weaver_test/"; // 파일 경로
                String fileName = file.getOriginalFilename(); // 파일 이름
                String filePath = uploadDir + fileName; // 파일 경로 + 파일 이름

                // 파일 저장
                File dest = new File(filePath);
                file.transferTo(dest);
                fileNames.add(fileName);

                // 파일 경로를 DB에 저장
                FileDto fileDto = new FileDto();
                fileDto.setPath(filePath);
                fileDto.setPostId(postId);
                fileDto.setName(fileName);
                postDao.saveFile(fileDto);
            }
        }
    }

    /** 게시글 아이디로 파일 리스트 가져오기 */
    @Override public List<FileDto> findAllFilesByPostId(int postId) {
        return postDao.findAllFilesByPostId(postId);
    }

    /** 게시판 아이디와 사용자 이름으로 PostDto 초기화 */
    @Override public PostDto setPostDtoForSave(int id, String role, PostDto postDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); // 사용자 이메일 가져오기
//        String userName = userService.findUserByEmail(email).getName(); // 사용자 이름 가져오기
        postDto.setBoardId(id); // 게시판 아이디
        postDto.setEmail(email); // 사용자 아이디
        postDto.setRole(role); // 게시글 권한 부여
        LocalDate nowDate = LocalDate.now();LocalTime nowTime = LocalTime.now(); // 현재 날짜와 시간
        postDto.setCreateDate(nowDate);postDto.setCreateTime(nowTime);
        return postDto;
    }

    /** 파일 아이디로 파일 가져오기 */
    @Override public FileDto findFileById(int id) {
        return postDao.findFileById(id);
    }

    @Override public int count() {
        return postDao.count();
    }

    @Override public int countByBoardId(int boardId) {
        return postDao.countByBoardId(boardId);
    }

    @Override public int countByRole(String role) {
        return postDao.countByRole(role);
    }

    @Override public int countByKeywordByGuest(String keyword) {
        return postDao.countByKeywordByGuest(keyword);
    }

    @Override public int countByKeywordByBoardId(int boardId, String keyword) {
        return postDao.countByKeywordByBoardId(boardId, keyword);
    }

    public void modifyPost(PostDto postDto) {
        postDao.modifyPost(postDto);
    }
}
