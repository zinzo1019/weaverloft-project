package com.example.choyoujin.controller;

import com.example.choyoujin.ApiResponse;
import com.example.choyoujin.DAO.PostDao;
import com.example.choyoujin.DTO.*;
import com.example.choyoujin.Service.CommentService;
import com.example.choyoujin.Service.FileService;
import com.example.choyoujin.Service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class PostController {
    @Autowired
    private PostDao postDao;
    @Autowired
    private PostServiceImpl postService; // Impl
    @Autowired
    private CommentService commentService;
    @Autowired
    private FileService fileService;

    /**
     * 게시글 개인 뷰
     */
    @RequestMapping("/{role}/view") // 권한 부여
    public String view(HttpServletRequest request, Model model, @PathVariable("role") String role) {
        int postId = Integer.parseInt(request.getParameter("id")); // 게시글 아이디
        model.addAttribute("dto", postDao.viewDao(postId)); // 게시글 정보

        List<ImageDto> imageDtos = postService.getImageDtos(postId); // 게시글 아이디로 이미지 리스트 가져오기
        List<FileDto> fileDtos = postService.findAllFilesByPostId(postId); // 게시글 아이디로 파일 리스트 가져오기
        List<CommentDto> allComments = commentService.findAllByPostId(postId); // 댓글 리스트 가져오기

        model.addAttribute("images", imageDtos); // 이미지 리스트 추가
        model.addAttribute("files", fileDtos); // 파일 리스트 추가
        model.addAttribute("comments", allComments); // 댓글 리스트 추가
        model.addAttribute("role", role); // 댓글 리스트 추가
        return "guest/view";
    }

    /**
     * 게시글 댓글 작성하기
     */
    @PostMapping("/{role}/comment")
    public ResponseEntity<ApiResponse> saveComments(CommentDto commentDto, HttpServletRequest request) throws Exception {
        try {
            int postId = Integer.parseInt(request.getParameter("id")); // 게시글 아이디
            String email = SecurityContextHolder.getContext().getAuthentication().getName(); // 사용자 이메일
            commentDto.setPostId(postId);
            commentDto.setEmail(email); // 사용자 아이디 & 게시글 아이디 세팅
            commentService.saveComment(commentDto); // 댓글 저장
            return ResponseEntity.ok(new ApiResponse("댓글을 저장했습니다."));
        } catch (Exception e) {
            throw new Exception("댓글 저장에 실패했습니다.");
        }
    }

    /**
     * 게시글 대댓글 작성하기
     */
    @PostMapping("/{role}/reply")
    public ResponseEntity<ApiResponse> saveReplys(CommentDto commentDto, HttpServletRequest request) {
        int replyId = Integer.parseInt(request.getParameter("id")); // 댓글 아이디
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); // 사용자 이메일
        commentService.setCommentDto(commentDto, replyId, email);
        commentService.saveReply(commentDto); // 대댓글 저장
        return ResponseEntity.ok(new ApiResponse("댓글을 저장했습니다."));
    }

    /**
     * 게시글 작성 페이지
     */
    @GetMapping("/{role}/writeForm")
    public String writeForm(@PathVariable("role") String role, @RequestParam("id") int id, Model model) {
        model.addAttribute("role", role);
        model.addAttribute("id", id);
        return "user/writeForm";
    }

    /**
     * 게시글 저장
     */
    @RequestMapping("/{role}/write")
    public ResponseEntity<Integer> write(@PathVariable("role") String role, @RequestParam("id") int id, PostDto postDto) throws Exception {
        try {
        PostDto setPostDto = postService.setPostDtoForSave(id, role, postDto); // 게시판 아이디 & 사용자 정보 & 현재 날짜로 set
        postDao.writeDao(setPostDto);// 게시글 저장하기
        int postId = postService.findMaxPostId();// 게시글 아이디 가져오기
        postService.saveImagesByPostId(postDto, postId); // 이미지 리스트 저장하기
        postService.saveFilesByPostId(postDto, postId); // 파일 리스트 저장하기
        return ResponseEntity.ok(postId); // 게시글 아이디로 응답
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("게시글 저장에 실패했습니다.");
        }
    }

    /**
     * 게시글 삭제 후 메인 페이지로 리다이렉트
     */
    @RequestMapping("/{role}/delete")
    public String delete(HttpServletRequest request) {
        postService.deletePostById(request.getParameter("id"));
        String boardId = request.getParameter("boardId");
        return "redirect:?id="+boardId;
    }

    /**
     * 파일 다운로드
     */
    @RequestMapping("/{role}/download/{postId}")
    public String downloadFile(@PathVariable("postId") int postId, @RequestParam("id") int id, HttpServletResponse response, HttpServletRequest request) {
        FileDto fileDto = postService.findFileById(id); // 파일 정보
        File file = new File("C:/weaver_test/", fileDto.getName()); // 경로에서 읽어올 파일
        fileService.downloadFile(response, request, fileDto, file); // 파일 다운로드
        return "redirect:/{role}/view?" + postId;
    }

}
