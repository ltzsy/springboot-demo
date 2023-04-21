package com.example.businessone.base.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @description: 这里用一句话描述这个类的作用 <br>
 * 如果定义了多个 ApplicationRunner Bean，并且需要它们按照特定的顺序先后执行。
 * 那么可以实现 org.springframework.core.Ordered 接口或使用 org.springframework.core.annotation.Order 注解来指定顺序<br>
 * @version: 1.0 <br>
 * @create: 2023/3/6 11:50 <br>
 */
@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {

    /*
     * @description: 该方法在 SpringApplication.run() 执行完毕之前被调用 <br>
     * @author: leevi.li <br>
     * @create: 2023/4/3 16:26 <br>
     * @param: null 
     * @return 
     */ 
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("=============ApplicationRunner：do something");
    }
}
