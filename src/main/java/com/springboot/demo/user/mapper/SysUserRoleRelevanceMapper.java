package com.springboot.demo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.demo.user.entity.SysUserRoleRelevance;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户-角色关联表 Mapper 接口
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-29
 */
@Mapper
public interface SysUserRoleRelevanceMapper extends BaseMapper<SysUserRoleRelevance> {

}
