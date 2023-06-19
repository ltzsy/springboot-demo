package com.springboot.demo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.demo.user.controller.dto.SysUserDetailsDTO;
import com.springboot.demo.user.controller.dto.SysUserQueryDTO;
import com.springboot.demo.user.entity.SysUser;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-09
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /*
     * @description: 查询分页 <br>
     * @create: 2023/5/24 17:22 <br>
     * @param page
     * @param param
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.springboot.demo.user.entity.SysUser>
     */
    Page<SysUser> selectPage(Page<SysUser> page, @Param("param") SysUserQueryDTO param);

}
