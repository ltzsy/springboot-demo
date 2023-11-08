package com.springboot.demo.test.controller;

import com.springboot.demo.base.utils.ExcelImportUtil;
import com.springboot.demo.test.entity.ImportTest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 这里用一句话描述这个类的作用 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/9/20 <br>
 */
@Api(tags = "测试")
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperation("导入")
    @PostMapping("/importExcel")
    public ResponseEntity<List<ImportTest>> importExcel(@ApiParam(name = "file") @RequestPart("file") MultipartFile file){
        List<ImportTest> list = ExcelImportUtil.readExcel(file, ImportTest.class);
        System.out.print(list);
        return ResponseEntity.ok(list);
    }
}
