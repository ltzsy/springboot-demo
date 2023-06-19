package com.springboot.demo.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.demo.base.constant.eunm.AvailableEnum;
import com.springboot.demo.base.exception.VoluntaryException;
import com.springboot.demo.user.controller.dto.SysRoleQueryDTO;
import com.springboot.demo.user.entity.SysRole;
import com.springboot.demo.user.entity.SysUser;
import com.springboot.demo.user.mapper.SysRoleMapper;
import com.springboot.demo.user.service.SysRoleService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-09
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements
    SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> selectRoleByUserId(Long userId) {
        if(ObjectUtils.isEmpty(userId)){
            return new ArrayList<>();
        }
        return sysRoleMapper.selectRoleByUserId(userId);
    }

    @Override
    public void create(SysRole sysRole) {
        //id置空
        sysRole.setRoleId(null);
        //保存
        sysRoleMapper.insert(sysRole);
    }

    @Override
    public void update(SysRole sysRole) {
        if(ObjectUtils.isEmpty(sysRole.getRoleId())){
            throw new VoluntaryException("roleid不能为空");
        }
        sysRole.setIsAvailable(null);
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public Page<SysRole> page(SysRoleQueryDTO dto) {
        Page<SysRole> page = new Page<>();
        page.setCurrent(dto.getPageNo());
        page.setSize(dto.getPageSize());
        sysRoleMapper.selectPage(page, dto);
        return page;
    }

    @Override
    public SysRole details(Long roleId) {
        if(ObjectUtils.isEmpty(roleId)){
            throw new VoluntaryException("roleid为空");
        }
        return sysRoleMapper.selectById(roleId);
    }

    @Override
    public void forbidden(List<Long> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)){
            return;
        }
        lambdaUpdate().set(SysRole::getIsAvailable, AvailableEnum.unavailable.getValue()).in(SysRole::getRoleId, roleIdList).update();
    }

    @Override
    public void enable(List<Long> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)){
            return;
        }
        lambdaUpdate().set(SysRole::getIsAvailable, AvailableEnum.available.getValue()).in(SysRole::getRoleId, roleIdList).update();
    }
}
