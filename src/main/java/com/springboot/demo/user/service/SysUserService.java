package com.springboot.demo.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.demo.user.controller.dto.SysUserDetailsDTO;
import com.springboot.demo.user.controller.dto.SysUserQueryDTO;
import com.springboot.demo.user.entity.SysUser;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-09
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 创建
     * @param sysUser
     * @return
     */
    void create(SysUser sysUser);

    /*
     * @description: 修改 <br>
     * @create: 2023/5/24 11:13 <br>
     * @param sysUser
     * @return void
     */
    void update(SysUser sysUser);

    /**
     * 修改密码
     * @param sysUser
     */
    void changePassword(SysUser sysUser);

    /*
     * @description: 重置密码 <br>
     * @create: 2023/5/24 14:12 <br>
     * @param useridList
     * @return void
     */
    void resetPassword(List<Long> useridList);

    /**
     * 禁用用户
     * @param userId
     */
    void forbidden(List<Long> useridList);

    /**
     * 启用用户
     * @param userId
     */
    void enable(List<Long> useridList);

    /**
     * 用户是否存在
     * @param username
     * @return
     */
    boolean isExsit(String username);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    SysUser loadUserByUsername(String username);

    /*
     * @description: 分页查询 <br>
     * @create: 2023/5/11 10:53 <br>
     * @param sysUser
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.springboot.demo.user.entity.SysUser>
     */
    Page<SysUser> selectPage(SysUserQueryDTO dto);

    /*
     * @description: 查询用户详情 <br>
     * @create: 2023/5/24 17:21 <br>
     * @param userid
     * @return com.springboot.demo.user.controller.dto.SysUserDetailsDTO
     */
    SysUserDetailsDTO details(Long userid);
}
