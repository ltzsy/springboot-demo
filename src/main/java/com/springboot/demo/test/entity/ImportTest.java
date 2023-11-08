package com.springboot.demo.test.entity;

import com.springboot.demo.base.annotation.ExcelField;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: 这里用一句话描述这个类的作用 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/9/20 <br>
 */
@Data
public class ImportTest {

    @ExcelField(name = "字符串")
    private String feild1;

    @ExcelField(name = "数值")
    private BigDecimal feild2;

    @ExcelField(name = "布尔")
    private Boolean feild3;

    @ExcelField(name = "日期")
    private Date feild4;
}
