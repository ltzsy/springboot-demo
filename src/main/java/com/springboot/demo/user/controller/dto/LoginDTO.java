package com.springboot.demo.user.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 登录传输对象 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/4/6 <br>
 */
@ApiModel(value = "登录参数")
@Data
public class LoginDTO {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
