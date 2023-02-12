package com.xiao.dto;

import lombok.Data;

@Data
public class QueryUserListDto {
    private int current;
    private int pageSize;
    private String userAccount;
    private String userName;
    private String userRole;
    private String userPhone;
}
