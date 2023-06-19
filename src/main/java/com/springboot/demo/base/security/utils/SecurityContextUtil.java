package com.springboot.demo.base.security.utils;

import com.springboot.demo.base.security.component.JwtGrantedAuthority;
import com.springboot.demo.base.security.component.JwtUserDetails;
import com.springboot.demo.base.utils.PropertyUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @description: 认证中心上下文工具 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/5/24 <br>
 */
@Component
public class SecurityContextUtil {

    private static PropertyUtil propertyUtil;

    @Autowired
    public void setEnvironment(PropertyUtil propertyUtil) {
        SecurityContextUtil.propertyUtil = propertyUtil;
    }

    /*
     * @description: 获取当前登录用户 <br>
     * @create: 2023/5/24 13:49 <br>
     * @param
     * @return com.springboot.demo.base.security.component.JwtUserDetails
     */
    public static JwtUserDetails getUser(){
        return (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    /*
     * @description: 获取当前登录用户id <br>
     * @create: 2023/5/24 13:49 <br>
     * @param
     * @return java.lang.Long
     */
    public static Long getUserId(){
        return getUser().getUserid();
    }

    /*
     * @description: 获取当前登录用户的角色集合 <br>
     * @create: 2023/5/24 14:33 <br>
     * @param
     * @return java.util.List<java.lang.Long>
     */
    public static List<Long> getRoleList(){
        List<JwtGrantedAuthority> jwtGrantedAuthorityList = (List<JwtGrantedAuthority>) getUser().getAuthorities();
        List<Long> roleList = new ArrayList<>();
        for (JwtGrantedAuthority jwtGrantedAuthority : jwtGrantedAuthorityList){
            roleList.add(Long.valueOf(jwtGrantedAuthority.getAuthority()));
        }
        return roleList;
    }

    /*
     * @description: 判断当前用户是否有管理员角色 <br>
     * @create: 2023/5/24 15:17 <br>
     * @param
     * @return boolean
     */
    public static  boolean isAdmin(){
        List<Long> roleList = getRoleList();
        long adminRoleId = propertyUtil.getLong("user.admin-role-id");
        return roleList.contains(adminRoleId);
    }
}
