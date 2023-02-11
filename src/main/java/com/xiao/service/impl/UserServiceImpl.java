package com.xiao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.dto.Result;
import com.xiao.dto.UserDTO;
import com.xiao.entity.User;
import com.xiao.service.UserService;
import com.xiao.mapper.UserMapper;
import com.xiao.util.UserHolder;
import com.xiao.util.VaildUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xiao.util.RedisConstants.*;

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


    /*注册+登录一体*/
    @Override
    public Result loginByPhone(String phone, String captcha) {
        //校验手机号和验证码
        if(!PhoneUtil.isPhone(phone)){
            return Result.fail("手机号格式不正确！");
        }
        if(!NumberUtil.isNumber(captcha)){
            return Result.fail("验证码格式不正确！");
        }

        //检查redis中是否有该手机号，无则直接返回false
        String key = LOGIN_CAPTCHA_KEY + phone;
        String result = stringRedisTemplate.opsForValue().get(key);
        if(StrUtil.isBlank(result)){
            return Result.fail("验证码已失效！");
        }
        if(!result.equals(captcha)){
            return Result.fail("验证码不正确！");
        }
        //验证通过，检查数据库是否存在该用户
        User user = query().eq("phone",phone).one();
        //不存在，新建用户
        if(user == null){
            //验证通过，新建用户，保存至数据库，默认用户名和密码为手机号
            user = new User();
            user.setUserAccount(phone);
            user.setUserPassword(SecureUtil.md5(phone));
            user.setUserName(RandomUtil.randomString(8));
            user.setUserRole("user");
            user.setGender(1);
            boolean save = save(user);
            if(!save){
                return  Result.fail("登录失败，请稍后再试！");
            }
        }
        //注册成功，新建token并返回
        String token = saveTokenToRedis(user);
        return Result.ok(token);
    }

    /*账号密码仅提供登录功能*/
    @Override
    public Result loginByAccount(User user, HttpServletRequest request) {
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
        // 一致，已有token，直接返回现有token
        String token = request.getHeader("authorization");
        if(StrUtil.isNotBlank(token)){
            return Result.ok(token);
        }
        token = saveTokenToRedis(one);
        // 返回token，作为验证登录的查询方式
        return Result.ok(token);
    }

    private String saveTokenToRedis(User user) {
        // 不存在，生成新的登录token
        String token = UUID.randomUUID().toString(true);
        String keyToken = LOGIN_USER_KEY + token;
        // 利用工具类，隐藏敏感信息
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        // id为long，转换为map会有问题，需要手动或使用工具类将long改为string
        // userDTO转为hashMap
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(), CopyOptions.create().
                setIgnoreNullValue(true).setFieldValueEditor((fieldName, fieldValue) -> fieldValue != null ? fieldValue.toString() : ""));
        // 保存token到redis，设置过期时间
        stringRedisTemplate.opsForHash().putAll(keyToken, userMap);
        stringRedisTemplate.expire(keyToken, LOGIN_USER_TTL, TimeUnit.MINUTES);
        return token;
    }


    /*TODO 用手机号注册代替，这方法可删*/
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

    @Override
    public Result currentUser() {
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }

    @Override
    public Result loginOut(HttpServletRequest request) {
        //从redis数据库查找token并删除
        String token = request.getHeader("authorization");
        String keyToken = LOGIN_USER_KEY + token;
        stringRedisTemplate.delete(keyToken);
        return Result.ok("成功退出！");
    }

    @Override
    public Result getCaptcha(String phone) {
        if(!PhoneUtil.isPhone(phone)){
            return Result.fail("手机号格式不正确！");
        }
        //检查redis中是否有该手机号，有则直接返回false
        String key = LOGIN_CAPTCHA_KEY + phone;
        String result = stringRedisTemplate.opsForValue().get(key);
        if(StrUtil.isNotBlank(result)){
            return Result.fail("验证过于频繁！");
        }
        //生成验证码
        String random = RandomUtil.randomNumbers(LOGIN_CAPTCHA_LEN);
        //将手机和验证码存入redis，并设置过期时间
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(random));
        stringRedisTemplate.expire(key, LOGIN_CAPTCHA_TTL, TimeUnit.MINUTES);
        return Result.ok("验证码已发送！验证码为：" + random);
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




