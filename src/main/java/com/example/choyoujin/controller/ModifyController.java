package com.example.choyoujin.controller;

import com.example.choyoujin.DTO.CommentDto;
import com.example.choyoujin.DTO.FileDto;
import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.PostDto;
import com.example.choyoujin.Service.CommentService;
import com.example.choyoujin.Service.FileService;
import com.example.choyoujin.Service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ModifyController {

    @Autowired
    private PostServiceImpl postService; // Impl
    @Autowired
    private CommentService commentService;
    @Autowired
    private FileService fileService;

    /**
     * 게시글 수정 페이지
     */
    @RequestMapping("/{role}/update/view") // 권한 부여
    public String updateView(@RequestParam("id") int postId, @PathVariable("role") String role, Model model) throws Exception {
        // 작성자와 로그인한 자의 정보가 일치하는지 확인
        if (postService.isWriter(postId)) { // 일치한다면
            findDtosAndAddModels(model, role, postId); // 모델에 데이터 담기
            return "guest/update_view";
        } else { // 일치하지 않는다면
            throw new Exception("수정 페이지 접근이 제한됐습니다."); // AOP
        }
    }

    /**
     * 게시글 수정하기
     */
    @PostMapping("/{role}/modify")
    public void modifyPost(@RequestParam("id") int id, PostDto postDto) { // 게시글 아이디
        LocalDate date = LocalDate.now(); LocalTime time = LocalTime.now(); // 수정 날짜 & 시간
        postDto.setUpdateDate(date); postDto.setUpdateTime(time);
        postDto.setId(id); postService.modifyPost(postDto); // 수정
    }

    /**
     * 이미지 삭제하기
     */
    @PostMapping("/{role}/image/delete")
    public void deleteImage(@RequestParam("id") int id) { // 이미지 아이디
        fileService.deleteImageById(id);
    }

    /**
     * 파일 삭제하기
     */
    @PostMapping("/{role}/file/delete")
    public void deleteeFile(@RequestParam("id") int id) { // 파일 아이디
        fileService.deltetFileById(id);
    }

    /**
     * 댓글 삭제하기
     */
    @PostMapping("/{role}/comment/delete")
    public void deleteComment(@RequestParam("id") int id) { // 댓글 아이디
        System.out.println("deleteComment");
        fileService.deleteCommentById(id);
    }

    /**
     * 게시글 수정 - 이미지 & 파일 추가
     */
    @RequestMapping("/{role}/addFiles")
    public ResponseEntity<Integer> addFiles(@RequestParam("id") int id, PostDto postDto) throws IOException {
        postService.saveImagesByPostId(postDto, id); // 이미지 리스트 저장하기
        postService.saveFilesByPostId(postDto, id); // 파일 리스트 저장하기
        return ResponseEntity.ok(id); // 게시글 아이디로 응답
    }

    /** 게시글 수정 페이지 데이터 가져오기 & 모델에 담기 */
    private void findDtosAndAddModels(Model model, String role, int postId) {
        List<ImageDto> imageDtos = postService.getImageDtos(postId); // 게시글 아이디로 이미지 리스트 가져오기
        List<FileDto> fileDtos = postService.findAllFilesByPostId(postId); // 게시글 아이디로 파일 리스트 가져오기
        List<CommentDto> allComments = commentService.findAllByPostId(postId); // 댓글 리스트 가져오기

        model.addAttribute("dto", postService.viewDao(postId)); // 게시글 정보
        model.addAttribute("images", imageDtos); // 이미지 리스트 추가
        model.addAttribute("files", fileDtos); // 파일 리스트 추가
        model.addAttribute("comments", allComments); // 댓글 리스트 추가
        model.addAttribute("role", role); // 댓글 리스트 추가
    }

}
