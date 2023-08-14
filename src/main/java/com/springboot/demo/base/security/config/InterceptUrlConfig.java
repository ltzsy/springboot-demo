package com.springboot.demo.base.security.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: 拦截url配置 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/6/26 <br>
 */
@Data
@Component
@ConfigurationProperties(prefix = "intercept-url")
public class InterceptUrlConfig {

    /**
     * 需要管理员权限的url
     */
    private List<String> adminUrlList;

    /**
     * 不拦截的url
     */
    private List<String> noninterceptUrlList;
}
