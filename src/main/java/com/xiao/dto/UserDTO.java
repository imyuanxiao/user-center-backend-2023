package com.xiao.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private Integer gender;
    private String userAccount;
    private String userAvatar;
    private String userRole;
    private String phone;
}
