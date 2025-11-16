package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

//    新增员工username相同异常
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
//        Duplicate entry 'm13jh' for key 'employee.idx_username'
        String error = ex.getMessage();
        String msg = "";
        if(error.contains("Duplicate entry")) {
            String[] s = error.split(" ");
            msg = s[2] + MessageConstant.ALREADY_EXISTS;
        } else {
            msg = MessageConstant.UNKNOWN_ERROR;
        }
        return Result.error(msg);
    }

}
