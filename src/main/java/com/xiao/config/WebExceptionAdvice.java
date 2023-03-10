package com.xiao.config;

import com.xiao.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.xiao.util.MsgCode.ERR_SERVICE_DEFAULT;

@Slf4j
@Configuration
public class WebExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeExceptin(RuntimeException e){
        log.error(e.toString(), e);
        return Result.fail(ERR_SERVICE_DEFAULT, "服务器异常");
    }

}
