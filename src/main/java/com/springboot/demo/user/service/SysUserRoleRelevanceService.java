package com.springboot.demo.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.demo.user.controller.dto.UserRoleDTO;
import com.springboot.demo.user.entity.SysUserRoleRelevance;

/**
 * <p>
 * 用户-角色关联表 服务类
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-29
 */
public interface SysUserRoleRelevanceService extends IService<SysUserRoleRelevance> {

    /*
     * @description: 设置用户角色 <br>
     * @create: 2023/5/29 14:30 <br>
     * @param dto
     * @return void
     */
    void setUseRoles(UserRoleDTO dto);
}
