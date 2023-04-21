package com.example.businessone.base.security.login.service;

import com.example.businessone.base.security.component.JwtUserDetails;
import com.example.businessone.base.security.login.controller.dto.LoginDTO;

/**
 * @description: 登录服务 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/4/6 <br>
 */
public interface LoginService {

    JwtUserDetails login(LoginDTO loginDTO);
}
