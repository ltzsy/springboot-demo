package com.springboot.demo.user.controller.dto;

import com.springboot.demo.user.entity.SysRole;
import com.springboot.demo.user.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @description: 用户详情信息 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/5/24 <br>
 */
@ApiModel("用户详情")
@Data
public class SysUserDetailsDTO {

    @ApiModelProperty("基本信息")
    private SysUser sysUser;

    @ApiModelProperty("角色信息")
    private List<SysRole> sysRoleList;

}
