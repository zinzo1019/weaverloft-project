package com.example.choyoujin.Config;

import com.example.choyoujin.DAO.ErrorLogDao;
import com.example.choyoujin.DTO.ErrorLogDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopConfig {

    @Autowired
    private ErrorLogDao errorLogDao;
    @AfterThrowing(pointcut = "execution(* com.example.choyoujin.controller.ModifyController.*(..))", throwing = "ex")
    public void logError(JoinPoint joinPoint, Exception ex) throws Exception {
        // 오류 메시지와 스택 트레이스를 가져와서 로그에 저장
        String errorMessage = ex.getMessage();
        String stackTrace = ExceptionUtils.getStackTrace(ex);

        // 오류 로그를 DB에 저장
        ErrorLogDto errorLog = new ErrorLogDto();
        errorLog.setMethodName(joinPoint.getSignature().toShortString());
        errorLog.setErrorMessage(errorMessage);
        errorLog.setStackTrace(stackTrace);
        errorLogDao.insertErrorLog(errorLog);

        System.out.println("메서드 실행 중 예외 발생: " + joinPoint.getSignature().toShortString());
        System.out.println("errorMessage: " + errorMessage);
        System.out.println("stackTrace: " + stackTrace);
        throw ex;
    }
}