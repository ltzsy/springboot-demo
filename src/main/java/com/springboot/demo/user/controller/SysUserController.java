package com.springboot.demo.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.demo.user.controller.dto.SysUserDetailsDTO;
import com.springboot.demo.user.controller.dto.SysUserQueryDTO;
import com.springboot.demo.user.entity.SysUser;
import com.springboot.demo.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-09
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("创建")
    @PostMapping("/create")
    public void create(@RequestBody SysUser sysUser){
        sysUserService.create(sysUser);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public void update(@RequestBody SysUser sysUser){
        sysUserService.update(sysUser);
    }

    @ApiOperation("修改密码")
    @PostMapping("changePassword")
    public void changePassword(@RequestBody SysUser sysUser){
        sysUserService.changePassword(sysUser);
    }

    @ApiOperation("重置密码")
    @PostMapping("/resetPassword")
    public void resetPassword(@RequestParam("useridList") List<Long> useridList){
        sysUserService.resetPassword(useridList);
    }

    @ApiOperation("禁用")
    @PostMapping("/forbidden")
    public void forbidden(@RequestParam("useridList") List<Long> useridList){
        sysUserService.forbidden(useridList);
    }

    @ApiOperation("启用")
    @PostMapping("/enable")
    public void enable(@RequestParam("useridList") List<Long> useridList){
        sysUserService.enable(useridList);
    }

    @ApiOperation("查询分页")
    @PostMapping("/page")
    public ResponseEntity<Page<SysUser>> page(@RequestBody SysUserQueryDTO dto){
        return ResponseEntity.ok(sysUserService.selectPage(dto));
    }

    @ApiOperation("查询详情")
    @PostMapping("details")
    public ResponseEntity<SysUserDetailsDTO> details(@RequestParam("userid") Long userid){
        return ResponseEntity.ok(sysUserService.details(userid));
    }
}
