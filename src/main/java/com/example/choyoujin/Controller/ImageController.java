package com.example.choyoujin.Controller;

import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.UserDto;
import com.example.choyoujin.Service.FileService;
import com.example.choyoujin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import static com.example.choyoujin.Service.FileService.decompressBytes;

@Controller
@RequestMapping("/guest")
public class ImageController {

    @Autowired
    private FileService fileService;

    /** 이미지 띄우기 테스트 */
    @GetMapping("/image")
    public ResponseEntity<byte[]> showImage() throws IOException {
        // 이메일로 유저 정보 가져오기
        ImageDto imageDto = fileService.findImageById(16);
        imageDto.setPicByte(decompressBytes(imageDto.getPicByte())); // 압축 풀기
        // 유저 이미지 가져오기
        byte[] picByte = imageDto.getPicByte();
        // 압축 해제
        System.out.println(picByte);
        return ResponseEntity.ok().contentType(MediaType.valueOf(imageDto.getType())).body(imageDto.getPicByte());
    }

    /** 이미지 다중 업로드 테스트 */
    @GetMapping("/file/register")
    public String fileRegister(Model model) {
        model.addAttribute(new UserDto());
        return "imageRegister";
    }

}
