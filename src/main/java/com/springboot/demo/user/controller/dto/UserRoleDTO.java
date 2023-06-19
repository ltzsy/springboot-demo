package com.springboot.demo.user.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @description: 这里用一句话描述这个类的作用 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/5/29 <br>
 */
@ApiModel("设置用户角色参数")
@Data
public class UserRoleDTO {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("角id键集合")
    private List<Long> roleIdList;
}
