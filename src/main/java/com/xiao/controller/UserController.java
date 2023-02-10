package com.xiao.controller;

import com.xiao.dto.Result;
import com.xiao.entity.User;
import com.xiao.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Api(value = "User Controller API")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Register")
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.register(user);
    }

    @ApiOperation(value = "Login with account")
    @PostMapping("/login/account")
    public Result loginByAccount(@RequestBody User user, HttpSession httpSession){
        return userService.loginByAccount(user, httpSession);
    }
    @ApiOperation(value = "Login with phone")
    @PostMapping("/login/captcha")
    public Result loginByCaotcha(@RequestBody User user, HttpSession httpSession){
        return Result.fail("功能暂未实现！");
    }


}
