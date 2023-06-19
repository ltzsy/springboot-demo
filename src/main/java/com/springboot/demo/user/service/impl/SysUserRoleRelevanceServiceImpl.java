package com.springboot.demo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.demo.base.exception.VoluntaryException;
import com.springboot.demo.user.controller.dto.UserRoleDTO;
import com.springboot.demo.user.entity.SysUserRoleRelevance;
import com.springboot.demo.user.mapper.SysUserRoleRelevanceMapper;
import com.springboot.demo.user.service.SysUserRoleRelevanceService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户-角色关联表 服务实现类
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-29
 */
@Service
public class SysUserRoleRelevanceServiceImpl extends ServiceImpl<SysUserRoleRelevanceMapper, SysUserRoleRelevance> implements
    SysUserRoleRelevanceService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setUseRoles(UserRoleDTO dto) {
        List<Long> roleIdList = dto.getRoleIdList();
        if(CollectionUtils.isEmpty(roleIdList)){
            throw new VoluntaryException("角色id集合为空");
        }
        long userId = dto.getUserId();
        //删除用户角色
        LambdaQueryWrapper<SysUserRoleRelevance> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRoleRelevance::getUserId, userId);
        this.remove(queryWrapper);
        //保存用户角色
        List<SysUserRoleRelevance> userRoleRelevanceList = new ArrayList<>();
        for(long roleId : roleIdList){
            SysUserRoleRelevance sysUserRoleRelevance = new SysUserRoleRelevance();
            sysUserRoleRelevance.setUserId(userId);
            sysUserRoleRelevance.setRoleId(roleId);
            userRoleRelevanceList.add(sysUserRoleRelevance);
        }
        this.saveBatch(userRoleRelevanceList);
    }

    @Transactional
    @Async
    public void test(){
        //数据库操作1
        //数据库操作2
        //数据库操作3
        //数据库操作4
    }
}
