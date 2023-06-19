package com.springboot.demo.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.demo.user.controller.dto.SysRoleQueryDTO;
import com.springboot.demo.user.entity.SysRole;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-05-09
 */
public interface SysRoleService extends IService<SysRole> {

    /*
     * @description: 根据userid查询角色信息 <br>
     * @create: 2023/5/10 15:06 <br>
     * @param userId
     * @return java.util.List<com.springboot.demo.user.entity.SysRole>
     */
    List<SysRole> selectRoleByUserId(Long userId);

    /*
     * @description: 创建 <br>
     * @create: 2023/5/25 9:22 <br>
     * @param sysRole
     * @return void
     */
    void create(SysRole sysRole);

    /*
     * @description: 修改 <br>
     * @create: 2023/5/25 9:23 <br>
     * @param sysRole
     * @return void
     */
    void update(SysRole sysRole);

    /*
     * @description: 查询分页 <br>
     * @create: 2023/5/25 9:30 <br>
     * @param null
     * @return
     */
    Page<SysRole> page(SysRoleQueryDTO dto);

    /*
     * @description: 查询详情 <br>
     * @create: 2023/5/25 9:30 <br>
     * @param roleId
     * @return com.springboot.demo.user.entity.SysRole
     */
    SysRole details(Long roleId);

    /*
     * @description: 禁用 <br>
     * @create: 2023/5/25 9:31 <br>
     * @param roleIdList
     * @return void
     */
    void forbidden(List<Long> roleIdList);

    /*
     * @description: 启用 <br>
     * @create: 2023/5/25 9:31 <br>
     * @param roleIdList
     * @return void
     */
    void enable(List<Long> roleIdList);
}
