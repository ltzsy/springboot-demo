package com.springboot.demo.base.security.config;

import com.springboot.demo.base.security.component.CustomAccessDeniedHandler;
import com.springboot.demo.base.security.component.JwtAuthorizationManager;
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

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

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
        //禁用登录表单
        http.formLogin().disable();
        //禁用基本身份认证
        http.httpBasic().disable();
        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //禁用跨域防护
        http.csrf().disable();
        //禁用匿名访问
        http.anonymous().disable();
        //关闭记住我
        http.rememberMe().disable();
        //授权配置
        http.authorizeHttpRequests()
            //开放登录请求
            .antMatchers("/login/**").permitAll()
            //开放swagger请求
            .antMatchers("/swagger-ui/**","/swagger-resources/**","/v3/api-docs/**","/doc.html","/webjars/**","/favicon.ico").permitAll()
            //jwt授权管理器
            .anyRequest().access(jwtAuthorizationManager)
            //授权失败处理器
            .and().exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
        return http.build();
    }
}
