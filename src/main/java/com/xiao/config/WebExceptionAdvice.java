package com.xiao.config;

import com.xiao.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Configuration
public class WebExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeExceptin(RuntimeException e){
        log.error(e.toString(), e);
        return Result.fail("服务器异常");
    }

}
