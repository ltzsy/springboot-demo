package com.example.businessone.base.security.config;

import com.example.businessone.base.security.component.JwtAuthorizationManager;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @description: security自定义配置 <br>
 * @version: 1.0 <br>
 * @create: 2023/3/31 9:37 <br>
 * @since jdk11.0.5_10
 */
@Slf4j
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthorizationManager jwtAuthorizationManager;

    /**
     * @description: securityFilterChain <br>
     * @version: 1.0 <br>
     * @create: 2023/3/31 9:39 <br>
     * @param: http
     * @return org.springframework.security.web.SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("初始化自定义【securityFilterChain】配置");
        //禁用跨域防护
        http.csrf().disable();
        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //设置不需要身份验证的api
        http.authorizeHttpRequests().regexMatchers("/login/*").permitAll();
        //设置需要认证的api使用自定义的jwt身份验证管理器进行认证
        http.authorizeHttpRequests().anyRequest().access(jwtAuthorizationManager);
        return http.build();
    }

    /*
     * @description: 配置jackson <br>
     * @create: 2023/4/18 17:17 <br>
     * @param
     * @return com.fasterxml.jackson.databind.ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        //日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //设置时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        //序列化-忽略null值的属性
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        //序列化-允许序列化空对象
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //反序列化-在遇到未知属性的时候不抛出异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
}
