package com.xiao.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Boolean success;
    /*项目编码 1 + 模块编码 2 + 错误编号 3*/
    private String errorCode;
    private String errorMessage;
    private Object data;
    private Long total;
    public static final String SUCCESS = "0";

    public static Result ok(){
        return new Result(true, SUCCESS,null, null, null);
    }
    public static Result ok(Object data){
        return new Result(true, SUCCESS,null, data, null);
    }
    public static Result ok(List<?> data, Long total){
        return new Result(true, SUCCESS,null, data, total);
    }
    public static Result fail(String errorCode, String errorMessage){
        return new Result(false, errorCode, errorMessage, null, null);
    }
}
