package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.LoginLogDao;
import com.example.choyoujin.DTO.LoginLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogService {
    @Autowired
    private LoginLogDao loginLogDao;
    public void saveLoginLog(LoginLogDto logDto) {
        loginLogDao.saveLoginLog(logDto);
    }
}
