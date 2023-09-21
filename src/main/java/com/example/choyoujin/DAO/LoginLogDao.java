package com.example.choyoujin.DAO;

import com.example.choyoujin.DTO.LoginLogDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogDao {
    void saveLoginLog(LoginLogDto logDto);
}
