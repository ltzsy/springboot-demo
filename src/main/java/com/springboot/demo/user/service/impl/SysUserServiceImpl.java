package com.springboot.demo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.demo.base.constant.eunm.AvailableEnum;
import com.springboot.demo.base.exception.VoluntaryException;
import com.springboot.demo.base.security.utils.PasswordEncoderUtil;
import com.springboot.demo.base.security.utils.SecurityContextUtil;
import com.springboot.demo.user.controller.dto.SysUserDetailsDTO;
import com.springboot.demo.user.controller.dto.SysUserQueryDTO;
import com.springboot.demo.user.entity.SysRole;
import com.springboot.demo.user.entity.SysUser;
import com.springboot.demo.user.mapper.SysUserMapper;
import com.springboot.demo.user.service.SysRoleService;
import com.springboot.demo.user.service.SysUserService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-09
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements
    SysUserService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Value("${user.admin-role-id}")
    private String adminRoleId;

    @Value("${user.reset_password}")
    private String reset_password;

    @Override
    public void create(SysUser sysUser) {
        //验证用户名是否存在
        String username = sysUser.getUsername();
        if(isExsit(username)){
            throw new VoluntaryException("用户名已存在");
        }
        //密码加密
        sysUser.encodePassword();
        //保存
        this.save(sysUser);
    }

    @Override
    public void update(SysUser sysUser) {
        //移除密码
        sysUser.setPassword(null);
        //保存
        this.updateById(sysUser);
    }

    @Override
    public void changePassword(SysUser sysUser) {
        //判断当前登录账号
        long userid = sysUser.getUserId();
        long contextUserid = SecurityContextUtil.getUserId();
        if(userid != contextUserid){
            throw new VoluntaryException("只能修改当前登录用户的密码");
        }
        //密码加密
        sysUser.encodePassword();
        //保存
        lambdaUpdate().set(SysUser::getPassword, sysUser.getPassword()).eq(SysUser::getUserId, userid).update();
    }

    @Override
    public void resetPassword(List<Long> useridList) {
        if(CollectionUtils.isEmpty(useridList)){
            return;
        }
        //判断管理员角色
        /*if(!SecurityContextUtil.isAdmin()){
            throw new VoluntaryException("没有重置密码的权限");
        }*/
        //重置密码
        String password = PasswordEncoderUtil.bCryptEncode(reset_password);
        lambdaUpdate().set(SysUser::getPassword, password).in(SysUser::getUserId, useridList).update();
    }

    @Override
    public void forbidden(List<Long> useridList) {
        if(CollectionUtils.isEmpty(useridList)){
            return;
        }
        //判断管理员角色
        /*if(!SecurityContextUtil.isAdmin()){
            throw new VoluntaryException("没有禁用用户的权限");
        }*/
        lambdaUpdate().set(SysUser::getIsAvailable, AvailableEnum.unavailable.getValue()).in(SysUser::getUserId, useridList).update();
    }

    @Override
    public void enable(List<Long> useridList) {
        if(CollectionUtils.isEmpty(useridList)){
            return;
        }
        //判断管理员角色
        /*if(!SecurityContextUtil.isAdmin()){
            throw new VoluntaryException("没有启用用户的权限");
        }*/
        lambdaUpdate().set(SysUser::getIsAvailable, AvailableEnum.available.getValue()).in(SysUser::getUserId, useridList).update();
    }

    @Override
    public boolean isExsit(String username) {
        if(StringUtils.isBlank(username)){
            return false;
        }
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUsername, username).eq(SysUser::getIsAvailable, 1);
        SysUser sysUser = this.getOne(wrapper);
        if(ObjectUtils.isEmpty(sysUser)){
            return false;
        }
        return true;
    }

    @Override
    public SysUser loadUserByUsername(String username) {
        //查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = this.getOne(wrapper);
        if(sysUser.unAvailable()){
            throw new VoluntaryException("用户已失效");
        }
        return sysUser;
    }

    @Override
    public Page<SysUser> selectPage(SysUserQueryDTO dto) {
        Page<SysUser> page = new Page<>();
        page.setCurrent(dto.getPageNo());
        page.setSize(dto.getPageSize());
        return sysUserMapper.selectPage(page,dto);
    }

    @Override
    public SysUserDetailsDTO details(Long userid) {
        if(ObjectUtils.isEmpty(userid)){
            return null;
        }
        //查询用户
        SysUser sysUser = sysUserMapper.selectById(userid);
        if(ObjectUtils.isEmpty(sysUser)){
            return null;
        }
        sysUser.setPassword(null);
        //查询角色
        List<SysRole> sysRoleList = sysRoleService.selectRoleByUserId(userid);
        SysUserDetailsDTO sysUserDetailsDTO = new SysUserDetailsDTO();
        sysUserDetailsDTO.setSysUser(sysUser);
        sysUserDetailsDTO.setSysRoleList(sysRoleList);
        return sysUserDetailsDTO;
    }
}
