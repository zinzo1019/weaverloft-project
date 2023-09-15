package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.PostDao;
import com.example.choyoujin.DTO.FileDto;
import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.example.choyoujin.Service.FileService.compressBytes;
import static com.example.choyoujin.Service.FileService.decompressBytes;

@Service
public class PostService {

    @Autowired
    private PostDao postDao;
    @Autowired
    private UserService userService;

    public void deletePostById(String id) {

        postDao.deletePostById(id);
        System.out.println("게시글을 삭제했습니다.");
    }

    public List<PostDto> findAllByBoardId(int boardId) {
        return postDao.findAllByBoardId(boardId);
    }

    public List<PostDto> findAllByRole(String role) {
        return postDao.findAllByRole(role);
    }

    public void saveImages(ImageDto imageDto) {
        postDao.saveImages(imageDto);
    }

    public int findMaxPostId() {
        return postDao.findMaxPostId();
    }

    /** 게시글 아이디로 이미지 리스트 가져오기 */
    public List<ImageDto> getImageDtos(int postId) {
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
    public void saveImagesByPostId(PostDto postDto, int postId) throws IOException {
        // 다중 이미지 업로드
        for (MultipartFile image : postDto.getImages()) {
            String name = image.getOriginalFilename(); // 파일 이름
            String type = image.getContentType(); // 파일 타입
            byte[] picByte = compressBytes(image.getBytes()); // 문자열 압축

            ImageDto imageDto = new ImageDto(name, type, picByte, postId); // ImageDto 생성
            saveImages(imageDto);
        }
    }

    /** 게시글 아이디로 파일 리스트 저장하기 */
    public void saveFilesByPostId(PostDto postDto, int postId) throws IOException {
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
            fileDto.setPath(filePath); fileDto.setPostId(postId); fileDto.setName(fileName);
            postDao.saveFile(fileDto);
        }
    }

    /** 게시글 아이디로 파일 리스트 가져오기 */
    public List<FileDto> findAllFilesByPostId(int postId) {
        return postDao.findAllFilesByPostId(postId);
    }

    /** 게시판 아이디와 사용자 이름으로 PostDto 초기화 */
    public PostDto setBoardIdAndWriter(int id, String role, PostDto postDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); // 사용자 이메일 가져오기
        String userName = userService.findUserByEmail(email).getName(); // 사용자 이름 가져오기
        postDto.setBoardId(id); // 게시판 아이디
        postDto.setWriter(userName); // 사용자 이름
        postDto.setRole(role); // 게시글 권한 부여
        return postDto;
    }

    /** 파일 아이디로 파일 가져오기 */
    public FileDto findFileById(int id) {
        return postDao.findFileById(id);
    }
}
