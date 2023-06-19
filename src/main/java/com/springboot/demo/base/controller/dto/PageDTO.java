package com.springboot.demo.base.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 分页dto <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/5/24 <br>
 */
@ApiModel("分页参数")
@Data
public class PageDTO {

    @ApiModelProperty("页码")
    @TableField(exist = false)
    private int pageNo;

    @ApiModelProperty("每页大小")
    @TableField(exist = false)
    private int pageSize;
}
