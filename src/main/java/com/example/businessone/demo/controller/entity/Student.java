package com.example.businessone.demo.controller.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

/**
 * @description: 这里用一句话描述这个类的作用 <br>
 * @version: 1.0 <br>
 * @author: leevi.li <br>
 * @create: 2023/3/15 14:45 <br>
 * @update: 2023/3/15 14:45 <br>
 * @since jdk11.0.5_10
 */
@Data
public class Student {

    private String name;

    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;
}
