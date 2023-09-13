package com.springboot.demo.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: excel导出字段注解 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/9/12 <br>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    /**
     * 字段名
     */
    String name() default "";

    /**
     * 字段顺序
     */
    int order() default 1;

    /**
     * 单元格宽度
     */
    int width() default 100;

    /**
     * 日期格式
     */
    String dateFormat() default "yyyy-MM-dd HH:mm:ss";
}
