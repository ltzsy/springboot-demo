package com.springboot.demo.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户-角色关联表
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-10
 */
@Getter
@Setter
@TableName("sys_user_role_relevance")
@ApiModel(value = "SysUserRoleRelevance对象", description = "用户-角色关联表")
public class SysUserRoleRelevance {


    @ApiModelProperty("主键")
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户主键")
    private Long userId;

    @ApiModelProperty("角色主键")
    private Long roleId;
}
