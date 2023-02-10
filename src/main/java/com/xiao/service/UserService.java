package com.xiao.service;

import com.xiao.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;
import com.xiao.dto.Result;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service
* @createDate 2023-02-06 11:01:06
*/
public interface UserService extends IService<User> {
    Result loginByAccount(User user, HttpSession httpSession);
    Result register(User user);

}
