package com.example.businessone.base.security.login.service.impl;

import com.example.businessone.base.exception.VoluntaryException;
import com.example.businessone.base.security.component.JwtUserDetails;
import com.example.businessone.base.security.component.JwtUserDetailsService;
import com.example.businessone.base.security.login.controller.dto.LoginDTO;
import com.example.businessone.base.security.login.service.LoginService;
import com.example.businessone.base.security.utils.PasswordEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 这里用一句话描述这个类的作用 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/4/6 <br>
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    public JwtUserDetails login(LoginDTO loginDTO) {
        //检验参数
        String username = loginDTO.getUsername();
        if(StringUtils.isBlank(username)){
            throw new VoluntaryException("username不能为空");
        }
        String password = loginDTO.getPassword();
        if(StringUtils.isBlank(password)){
            throw new VoluntaryException("password不能为空");
        }
        //查询用户信息
        boolean exist = jwtUserDetailsService.userExists(username);
        if(!exist){
            throw new VoluntaryException("用户不存在");
        }
        //校验用户密码
        JwtUserDetails user = (JwtUserDetails) jwtUserDetailsService.loadUserByUsername(username);
        String dbPassword = user.getPassword();
        boolean checkPassword = PasswordEncoderUtil.bCryptMatch(password, dbPassword);
        if(!checkPassword){
            throw new VoluntaryException("密码错误");
        }
        //生成jwt
        String jwt = jwtUserDetailsService.buildJwt(user);
        user.setJwt(jwt);
        //清空密码
        user.setPassword(null);
        return user;
    }
}
