package com.springboot.demo.user.controller;

import com.springboot.demo.user.controller.dto.UserRoleDTO;
import com.springboot.demo.user.service.SysUserRoleRelevanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户-角色关联表 前端控制器
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-29
 */
@Api(tags = "用户与角色")
@RestController
@RequestMapping("/sysUserRoleRelevance")
public class SysUserRoleRelevanceController {

    @Autowired
    private SysUserRoleRelevanceService sysUserRoleRelevanceService;

    @ApiOperation("设置用户角色")
    @PostMapping("/setUserRoles")
    public void setUserRoles(@RequestBody UserRoleDTO dto){
        sysUserRoleRelevanceService.setUseRoles(dto);
    }
}
