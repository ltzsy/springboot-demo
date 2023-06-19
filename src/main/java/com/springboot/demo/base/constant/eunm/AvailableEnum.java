package com.springboot.demo.base.constant.eunm;

/**
 * @description: 是否可用枚举 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/5/24 <br>
 */
public enum AvailableEnum {
    available(1,"可用"),
    unavailable(2,"禁用");

    private int value;
    private String desc;

    AvailableEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }
}
