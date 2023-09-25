package com.example.choyoujin.Config;

import com.example.choyoujin.DAO.ErrorLogDao;
import com.example.choyoujin.DTO.ErrorLogDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class AopConfig {

    @Autowired
    private ErrorLogDao errorLogDao;
    @AfterThrowing(pointcut = "execution(* com.example.*.*.*(..))", throwing = "ex")
    public void logError(JoinPoint joinPoint, Exception ex) {
        // 오류 메시지와 스택 트레이스를 가져와서 로그에 저장
        String errorMessage = ex.getMessage();
        String stackTrace = ExceptionUtils.getStackTrace(ex);

        // 오류 로그를 DB에 저장
        ErrorLogDto errorLog = new ErrorLogDto();
        errorLog.setMethodName(joinPoint.getSignature().toShortString());
        errorLog.setErrorMessage(errorMessage);
        errorLog.setStackTrace(stackTrace);
        errorLogDao.insertErrorLog(errorLog);
    }
}