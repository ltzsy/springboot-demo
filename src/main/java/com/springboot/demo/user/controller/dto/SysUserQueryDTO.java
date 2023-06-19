package com.springboot.demo.user.controller.dto;

import com.springboot.demo.base.controller.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 用户查询dto <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/5/24 <br>
 */
@ApiModel("用户查询参数")
@Data
public class SysUserQueryDTO extends PageDTO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "是否可以用：1-可用，2-不可用")
    private Integer isAvailable;
}
