package com.springboot.demo.user.controller.dto;

import com.springboot.demo.base.controller.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 角色查询参数对象 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/5/25 <br>
 */
@ApiModel("角色查询参数")
@Data
public class SysRoleQueryDTO extends PageDTO {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("是否可以用：1-可用，2-不可用")
    private Integer isAvailable;
}
