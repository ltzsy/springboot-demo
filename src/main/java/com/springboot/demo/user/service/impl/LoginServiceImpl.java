package com.springboot.demo.user.service.impl;

import com.springboot.demo.base.exception.VoluntaryException;
import com.springboot.demo.base.security.component.JwtGrantedAuthority;
import com.springboot.demo.base.security.utils.JwtUtil;
import com.springboot.demo.base.security.utils.PasswordEncoderUtil;
import com.springboot.demo.user.controller.dto.LoginDTO;
import com.springboot.demo.user.entity.SysRole;
import com.springboot.demo.user.entity.SysUser;
import com.springboot.demo.user.service.LoginService;
import com.springboot.demo.user.service.SysRoleService;
import com.springboot.demo.user.service.SysUserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public String login(LoginDTO loginDTO) {
        //检验参数
        String username = loginDTO.getUsername();
        if(StringUtils.isBlank(username)){
            throw new VoluntaryException("用户名为空");
        }
        String password = loginDTO.getPassword();
        if(StringUtils.isBlank(password)){
            throw new VoluntaryException("密码为空");
        }
        //查询用户信息
        boolean exist = sysUserService.isExsit(username);
        if(!exist){
            throw new VoluntaryException("用户不存在");
        }
        //校验用户密码
        SysUser user = sysUserService.loadUserByUsername(username);
        String dbPassword = user.getPassword();
        boolean checkPassword = PasswordEncoderUtil.bCryptMatch(password, dbPassword);
        if(!checkPassword){
            throw new VoluntaryException("密码错误");
        }
        //生成jwt
        String jwt = buildJwt(user);
        return jwt;
    }

    /*
     * @description: 生成jwt <br>
     * @create: 2023/5/24 15:27 <br>
     * @param user
     * @return java.lang.String
     */
    private String buildJwt(SysUser user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userid", user.getUserId());
        claims.put("username", user.getUsername());
        List<JwtGrantedAuthority> authorities = getJwtAuthorities(user.getUserId());
        claims.put("authorities", authorities);
        String jwt = JwtUtil.createJwt(claims);
        return jwt;
    }

    /*
     * @description: 获取权限集合 <br>
     * @create: 2023/5/24 15:36 <br>
     * @param userid
     * @return java.util.List<com.springboot.demo.base.security.component.JwtGrantedAuthority>
     */
    private List<JwtGrantedAuthority> getJwtAuthorities(Long userid){
        List<SysRole> sysRoleList = sysRoleService.selectRoleByUserId(userid);
        if(CollectionUtils.isEmpty(sysRoleList)){
            throw new VoluntaryException("用户未配置角色");
        }
        List<JwtGrantedAuthority> jwtGrantedAuthorityList = new ArrayList<>();
        for (SysRole sysRole : sysRoleList){
            jwtGrantedAuthorityList.add(new JwtGrantedAuthority(String.valueOf(sysRole.getRoleId())));
        }
        return jwtGrantedAuthorityList;
    }
}
