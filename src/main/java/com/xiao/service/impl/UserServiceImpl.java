package com.xiao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.dto.Result;
import com.xiao.dto.UserDTO;
import com.xiao.entity.User;
import com.xiao.service.UserService;
import com.xiao.mapper.UserMapper;
import com.xiao.util.VaildUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xiao.util.RedisConstants.LOGIN_USER_KEY;
import static com.xiao.util.RedisConstants.LOGIN_USER_TTL;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-02-06 11:01:06
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result loginByAccount(User user, HttpSession httpSession) {
        // 判断用户格式或密码格式
        if(!checkFormat(user)){
            return Result.fail("用户名或密码格式错误");
        }
        // 从数据库中查询用户
        User one = query().eq("userAccount", user.getUserAccount()).one();
        if(one == null){
            return Result.fail("用户不存在");
        }
        // 判断用户名密码是否对的上
        if(!one.getUserPassword().equals(SecureUtil.md5(user.getUserPassword()))){
            return Result.fail("用户名或密码错误！");
        }
        // 一致，生成登录token
        String token = UUID.randomUUID().toString(true);
        String keyToken = LOGIN_USER_KEY + token;
        // 利用工具类，隐藏敏感信息
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        // id为long，转换为map会有问题，需要手动或使用工具类将long改为string
        // userDTO转为hashMap
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(), CopyOptions.create().
                setIgnoreNullValue(true).setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        // 保存token到redis，设置过期时间
        stringRedisTemplate.opsForHash().putAll(keyToken, userMap);
        stringRedisTemplate.expire(keyToken, LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 返回token，作为验证登录的查询方式
        return Result.ok(token);
    }

    @Override
    public Result register(User user) {
        if(!checkFormat(user)){
            return Result.fail("用户名或密码格式错误");
        }
        // 3. userAccount existed?
        Long count = query().eq("userAccount", user.getUserAccount()).count();
        if(count != 0) {
            return Result.fail("用户名已存在！");
        }
        // 4. encode password
        user.setUserPassword(SecureUtil.md5(user.getUserPassword()));
        user.setUserName(user.getUserAccount());
        boolean save = save(user);
        if(!save){
           return  Result.fail("注册失败，请稍后再试！");
        }
        return Result.ok("注册成功，请登录！");
    }

    private boolean checkFormat(User user){
        // 1. username valid?
        String userAccount = user.getUserAccount();
        if(!VaildUtils.checkUserAccount(userAccount)){
            return false;
        }
        // 2. password valid?
        String userPassword = user.getUserPassword();
        return VaildUtils.checkPassword(userPassword);
    }

}




