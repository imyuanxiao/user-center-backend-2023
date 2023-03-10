package com.xiao.service;

import com.xiao.dto.QueryUserListDto;
import com.xiao.dto.Result;
import com.xiao.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author Administrator
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-02-10 22:28:22
*/
public interface UserService extends IService<User> {
    Result currentUser();

    Result loginByAccount(User user, HttpServletRequest request);

    Result loginByPhone(String phone, String captcha);

    Result loginOut(HttpServletRequest request);

    Result getCaptcha(String phone);

    Result updateAccount(User user);

    Result userList(QueryUserListDto queryUserListDto, HttpServletRequest request);

    Result updateUser(User user);

    Result addUser(User user);

    Result deleteUser(String jsonStr, HttpServletRequest request);
}
