package com.example;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.demo.user.entity.SysUserRoleRelevance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户-角色关联表 前端控制器
 * </p>
 *
 * @author 小花卷的Dad
 * @since 2023-06-21
 */
@Api(tags = "用户-角色关联表")
@RestController
@RequestMapping("/sysUserRoleRelevance")
public class SysUserRoleRelevanceController {

  @ApiOperation("创建")
  @PostMapping("/create")
  public void create(@RequestBody SysUserRoleRelevance sysUserRoleRelevance){}

  @ApiOperation("修改")
  @PostMapping("/update")
  public void update(@RequestBody SysUserRoleRelevance sysUserRoleRelevance){}

  @ApiOperation("删除")
  @PostMapping("/delete")
  public void delete(@RequestBody List<Long> idList){}

  @ApiOperation("查询详情")
  @PostMapping("/details")
  public ResponseEntity<SysUserRoleRelevance> details(@RequestParam("id") Long id){
    return ResponseEntity.ok(null);
  }

  @ApiOperation("查询")
  @PostMapping("/list")
  public ResponseEntity<List> list(@RequestBody Object dto){
    return ResponseEntity.ok(null);
  }

  @ApiOperation("分页")
  @PostMapping("/page")
  public ResponseEntity<Page> page(@RequestBody Object dto){
    return ResponseEntity.ok(null);
  }
}
