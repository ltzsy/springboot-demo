package com.springboot.demo.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.demo.base.constant.eunm.AvailableEnum;
import com.springboot.demo.base.exception.VoluntaryException;
import com.springboot.demo.base.security.utils.PasswordEncoderUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-10
 */
@Getter
@Setter
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser {

    @ApiModelProperty("用户主键")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "是否可以用：1-可用，2-不可用")
    private Integer isAvailable;

    /*
     * @description: 密码加密 <br>
     * @create: 2023/5/10 15:43 <br>
     * @param
     * @return void
     */
    public void encodePassword(){
        if(StringUtils.isBlank(password)){
            throw new VoluntaryException("密码为空");
        }
        password = PasswordEncoderUtil.bCryptEncode(password);
    }

    /*
     * @description: 是否无效 <br>
     * @create: 2023/5/10 15:43 <br>
     * @param
     * @return boolean
     */
    public boolean unAvailable(){
        return AvailableEnum.available.getValue() != isAvailable;
    }

}
