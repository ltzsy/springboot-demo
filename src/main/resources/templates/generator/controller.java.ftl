package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${table.comment!}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

  @ApiOperation("创建")
  @PostMapping("/create")
  public void create(@RequestBody ${table.entityName} <#if package.ModuleName?? && package.ModuleName != "">${package.ModuleName}</#if><#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>){}

  @ApiOperation("修改")
  @PostMapping("/update")
  public void update(@RequestBody ${table.entityName} <#if package.ModuleName?? && package.ModuleName != "">${package.ModuleName}</#if><#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>){}

  @ApiOperation("删除")
  @PostMapping("/delete")
  public void delete(@RequestBody List<Long> idList){}

  @ApiOperation("查询详情")
  @PostMapping("/details")
  public ResponseEntity<${table.entityName}> details(@RequestParam("id") Long id){
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
</#if>
