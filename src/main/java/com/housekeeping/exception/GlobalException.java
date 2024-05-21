package com.housekeeping.exception;

import com.housekeeping.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("error: ", e);
        return Result.error(e.getMessage());
    }


    @ExceptionHandler(ErrorException.class)
    public Result errorExceptionHandler(ErrorException e) {
        log.error("error: {}", e.getMsg());
        return Result.error(e.getCode(), e.getMessage());
    }

}
