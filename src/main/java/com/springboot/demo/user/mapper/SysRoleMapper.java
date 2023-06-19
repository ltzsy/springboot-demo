package com.springboot.demo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.demo.user.controller.dto.SysRoleQueryDTO;
import com.springboot.demo.user.entity.SysRole;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-09
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectRoleByUserId(Long userId);

    Page<SysRole> selectPage(Page page, @Param("param")SysRoleQueryDTO param);
}
