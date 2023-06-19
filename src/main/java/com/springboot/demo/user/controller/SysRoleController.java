package com.springboot.demo.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.demo.base.exception.VoluntaryException;
import com.springboot.demo.user.controller.dto.SysRoleQueryDTO;
import com.springboot.demo.user.entity.SysRole;
import com.springboot.demo.user.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-09
 */
@Api(tags = "角色")
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("创建")
    @PostMapping("/create")
    public void create(@RequestBody SysRole sysRole){
        sysRoleService.create(sysRole);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public void update(@RequestBody SysRole sysRole){
        sysRoleService.update(sysRole);
    }

    @ApiOperation("查询分页")
    @PostMapping("/page")
    public ResponseEntity<Page<SysRole>> page(@RequestBody SysRoleQueryDTO dto){
        return ResponseEntity.ok(sysRoleService.page(dto));
    }

    @ApiOperation("查询详情")
    @PostMapping("/details")
    public ResponseEntity<SysRole> details(@RequestParam("roleId") Long roleId){
        return  ResponseEntity.ok(sysRoleService.details(roleId));
    }

    @ApiOperation("禁用")
    @PostMapping("/forbidden")
    public void forbidden(@RequestParam("roleIdList") List<Long> roleIdList){
        sysRoleService.forbidden(roleIdList);
    }

    @ApiOperation("启用")
    @PostMapping("/enable")
    public void enable(@RequestParam("roleIdList") List<Long> roleIdList){
        sysRoleService.enable(roleIdList);
    }
}
