package com.example.choyoujin.DAO;

import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.Entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDao {
    void saveImage(ImageDto imageDto); // 이미지 저장하기

    ImageDto findImageByEmail(String email); // 이메일로 사용자 이미지 가져오기

    ImageDto findImageById(int id); // 기본키로 이미지 가져오기

    int findIdByEmail(String email); // 이메일로 사용자 이미지 아이디 가져오기

    void deleteImageById(int id); // 아이디로 이미지 삭제

    void deleteFileById(int id); // 아이디로 파일 삭제

    void deleteCommentById(int id); // 아이디로 댓글 삭제
}