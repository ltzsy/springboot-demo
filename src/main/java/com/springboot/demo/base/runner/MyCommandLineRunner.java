package com.springboot.demo.base.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @description:
 * 如果定义了多个 CommandLineRunner Bean，并且需要它们按照特定的顺序先后执行。
 * 那么可以实现 org.springframework.core.Ordered 接口或使用 org.springframework.core.annotation.Order 注解来指定顺序<br>
 * @author: leevi.li <br>
 * @create: 2023/3/6 11:48 <br>
 * @since jdk11.0.5_10
 */
@Slf4j
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    /*
     * @description: 该方法在 SpringApplication.run() 执行完毕之前被调用 <br>
     * @author: leevi.li <br>
     * @create: 2023/4/3 16:26 <br>
     * @param: null
     * @return 
     */ 
    @Override
    public void run(String... args) throws Exception {
        log.info("=============CommandLineRunner：do something");
    }
}
