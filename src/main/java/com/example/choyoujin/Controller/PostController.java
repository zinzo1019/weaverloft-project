package com.example.choyoujin.Controller;

import com.example.choyoujin.DAO.PostDao;
import com.example.choyoujin.DTO.FileDto;
import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.PostDto;
import com.example.choyoujin.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostDao postDao;
    @Autowired
    private PostService postService;

    /**
     * 게시글 개인 뷰
     */
    @RequestMapping("/{role}/view") // 권한 부여
    public String view(HttpServletRequest request, Model model) {
        int postId = Integer.parseInt(request.getParameter("id")); // 게시글 아이디
        model.addAttribute("dto", postDao.viewDao(postId)); // 게시글 정보

        List<ImageDto> imageDtos = postService.getImageDtos(postId); // 게시글 아이디를 토대로 이미지 리스트 가져오기
        List<FileDto> fileDtos = postService.findAllFilesByPostId(postId); // 게시글 아이디를 토대로 파일 리스트 가져오기
        model.addAttribute("images", imageDtos); // model에 추가
        model.addAttribute("files", fileDtos); // file에 추가
        return "guest/view";
    }

    /**
     * 게시글 작성 페이지
     */
    @RequestMapping("/{role}/writeForm")
    public String writeForm(@PathVariable("role") String role, @RequestParam("id") int id, Model model) {
        model.addAttribute("role", role);
        model.addAttribute("id", id);
        return "user/writeForm";
    }

    /**
     * 게시글 작성 저장 후 메인 페이지로 리다이렉트
     */
    @RequestMapping("/{role}/write")
    public String write(@PathVariable("role") String role, @RequestParam("id") int id, PostDto postDto) throws IOException {
        PostDto setPostDto = postService.setBoardIdAndWriter(id, role, postDto); // postDto에 게시판 아이디와 사용자 이름 set
        postDao.writeDao(setPostDto);// 게시글 저장하기
        int postId = postService.findMaxPostId();// 게시글 아이디 가져오기
        postService.saveImagesByPostId(postDto, postId); // 이미지 리스트 저장하기
        postService.saveFilesByPostId(postDto, postId); // 파일 리스트 저장하기

        return "redirect:board?id=" + id;
    }

    /**
     * 게시글 삭제 후 메인 페이지로 리다이렉트
     */
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request) {
        postService.deletePostById(request.getParameter("id"));
        return "redirect:board";
    }

    /** 파일 다운로드 */
    @RequestMapping("/{role}/download/{postId}")
    public String downloadFile(@PathVariable("postId") int postId, @RequestParam("id") int id, HttpServletResponse response, HttpServletRequest request) {
        FileDto fileDto = postService.findFileById(id); // 파일 정보
        File file = new File("C:/weaver_test/", fileDto.getName()); // 경로에서 읽어올 파일

        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;

        try{
            String downName = null;
            String browser = request.getHeader("User-Agent");
            //파일 인코딩
            if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){//브라우저 확인 파일명 encode

                downName = URLEncoder.encode(fileDto.getName(),"UTF-8").replaceAll("\\+", "%20");

            }else{

                downName = new String(fileDto.getName().getBytes("UTF-8"), "ISO-8859-1");

            }

            response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");
            response.setContentType("application/octer-stream");
            response.setHeader("Content-Transfer-Encoding", "binary;");

            fileInputStream = new FileInputStream(file);
            servletOutputStream = response.getOutputStream();

            byte b [] = new byte[1024];
            int data = 0;

            while((data=(fileInputStream.read(b, 0, b.length))) != -1){

                servletOutputStream.write(b, 0, data);

            }

            servletOutputStream.flush();//출력

        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(servletOutputStream!=null){
                try{
                    servletOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(fileInputStream!=null){
                try{
                    fileInputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return "redirect:/{role}/view?" + postId;

    }

}
