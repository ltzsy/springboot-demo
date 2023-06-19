package com.springboot.demo.base.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @description: 配置文件工具 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/5/24 <br>
 */
@Component
public class PropertyUtil {

    private static Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        PropertyUtil.environment = environment;
    }

    /*
     * @description: 获取配置 <br>
     * @create: 2023/5/24 14:45 <br>
     * @param key
     * @return java.lang.String
     */
    public String getString(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return environment.getProperty(key);
    }

    /*
     * @description: 获取配置 <br>
     * @create: 2023/5/24 14:47 <br>
     * @param key
     * @return java.lang.Long
     */
    public Long getLong(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return environment.getProperty(key, Long.class);
    }
}
