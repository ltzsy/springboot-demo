package com.springboot.demo.base.security.config;

import com.springboot.demo.base.security.component.JwtAuthorizationManager;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
        //禁用表单登录
        http.formLogin().disable();
        //禁用跨域防护
        http.csrf().disable();
        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //不拦截登录请求
        http.authorizeHttpRequests().antMatchers("/login/**").permitAll();
        //不拦截swagger相关请求
        http.authorizeHttpRequests().antMatchers("/swagger-ui/**","/swagger-resources/**","/v3/api-docs/**","/doc.html","/webjars/**","/favicon.ico").permitAll();
        //设置需要认证的api使用自定义的jwt身份验证管理器进行认证
        http.authorizeHttpRequests().anyRequest().access(jwtAuthorizationManager);
        return http.build();
    }
}
