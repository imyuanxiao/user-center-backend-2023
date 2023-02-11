package com.xiao.controller;

import com.xiao.dto.Result;
import com.xiao.entity.User;
import com.xiao.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(value = "User Controller API")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("currentUser")
    public Result currentUser(){
        return userService.currentUser();
    }


    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login/account")
    public Result loginByAccount(@RequestBody User user, HttpServletRequest request){
        return userService.loginByAccount(user, request);
    }

    @GetMapping("/login/phone")
    public Result loginByPhone(@RequestParam("mobile") String phone, @RequestParam("captcha") String captcha){
        return userService.loginByPhone(phone, captcha);
    }

    @PostMapping("/login/loginOut")
    public Result loginOut(HttpServletRequest request){
        return userService.loginOut(request);
    }

    @GetMapping("/login/captcha")
    public Result getCaptcha(@RequestParam("phone") String phone){
        return userService.getCaptcha(phone);
    }


}
