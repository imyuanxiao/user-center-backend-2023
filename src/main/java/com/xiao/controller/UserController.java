package com.xiao.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.xiao.dto.QueryUserListDto;
import com.xiao.dto.Result;
import com.xiao.entity.User;
import com.xiao.service.UserService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(value = "User Controller API")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/currentUser")
    public Result currentUser(){
        return userService.currentUser();
    }

    @PostMapping("/userList")
    public Result userList(@RequestBody QueryUserListDto queryUserListDto, HttpServletRequest request){
        return userService.userList(queryUserListDto, request);
    }

    @PostMapping("/login/account")
    public Result loginByAccount(@RequestBody User user, HttpServletRequest request){
        return userService.loginByAccount(user, request);
    }

    @GetMapping("/login/phone")
    public Result loginByPhone(@RequestParam("phone") String phone, @RequestParam("captcha") String captcha){
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

    @PostMapping("account/update")
    public Result updateAccount(@RequestBody User user){
        return userService.updateAccount(user);
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @PostMapping("/add")
    public Result addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @DeleteMapping("/delete")
    public Result deleteUser(@RequestBody String jsonStr, HttpServletRequest request){
        return userService.deleteUser(jsonStr, request);
    }

}
