package com.example.businessone.base.security.login.controller.dto;

import lombok.Data;

/**
 * @description: 登录传输对象 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/4/6 <br>
 */
@Data
public class LoginDTO {

    private String username;

    private String password;
}
