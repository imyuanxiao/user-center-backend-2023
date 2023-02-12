package com.xiao.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;//数据库主键
    private String userId;//唯一标识，uuid
    private String userName;//昵称
    private Integer userGender;//性别
    private String userAccount;//账号
    private String userAvatar;//头像
    private String userRole;//级别
    private String userPhone;//手机号
}
