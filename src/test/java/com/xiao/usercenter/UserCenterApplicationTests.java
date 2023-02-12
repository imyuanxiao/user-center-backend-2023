package com.xiao.usercenter;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiao.dto.Result;
import com.xiao.dto.UserDTO;
import com.xiao.entity.User;
import com.xiao.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {

    @Autowired
    private UserService userService;


    @Test
    void testUserLogin(){
        User user = new User();
        user.setUserAccount("eric9527");
        user.setUserPassword("eric9527");
        user.setUserRole("admin");
        Result login = userService.loginByAccount(user, null);
        System.out.println(login);
    }

    @Test
    void testQuery(){
        User user = new User();
        user.setUserAccount("eric9527");
        User one = userService.query().eq("userAccount", "eric9527").one();
        System.out.println(one);
    }

    @Test
    void testUserList(){
        Page<User> page = new Page<>(1, 5);
        userService.page(page);
        List<User> records = page.getRecords();
        for(User user : records){
            System.out.println(user);
        }
    }

}
