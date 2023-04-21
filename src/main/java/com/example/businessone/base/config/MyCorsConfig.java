package com.example.businessone.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 跨域全局配置 <br>
 * @version: 1.0 <br>
 * @author: leevi.li <br>
 * @create: 2023/3/15 16:28 <br>
 * @update: 2023/3/15 16:28 <br>
 * @since jdk11.0.5_10
 */
@Configuration(proxyBeanMethods = false)
public class MyCorsConfig {

    @Bean
    public WebMvcConfigurer corsconfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/");
            }
        };
    }
}
