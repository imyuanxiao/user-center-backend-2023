package com.xiao.usercenter;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.xiao.dto.Result;
import com.xiao.entity.User;
import com.xiao.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserCenterApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void testUserRegister() {
        User user = new User();
        user.setUserAccount("eric9527");
        user.setUserPassword("eric9527");
        Result result = userService.register(user);
        System.out.println(result);
    }

    @Test
    void testUserLogin(){
        User user = new User();
        user.setUserAccount("eric9527");
        user.setUserPassword("eric9527");
        user.setUserRole("admin");
        Result login = userService.loginByAccount(user, null);
        System.out.println(login);
    }

}
