package com.springboot.demo.user.service;

import com.springboot.demo.user.controller.dto.LoginDTO;

/**
 * @description: 登录服务 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/4/6 <br>
 */
public interface LoginService {

    String login(LoginDTO loginDTO);
}
