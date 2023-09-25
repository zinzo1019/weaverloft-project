package com.example.choyoujin.DAO;

import com.example.choyoujin.DTO.ErrorLogDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ErrorLogDao {
    void insertErrorLog(ErrorLogDto errorLog); // 에러 저장하기
}
