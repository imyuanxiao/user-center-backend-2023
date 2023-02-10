package com.xiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.entity.Post;
import com.xiao.service.PostService;
import com.xiao.mapper.PostMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【post】的数据库操作Service实现
* @createDate 2023-02-06 11:01:10
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService{

}




