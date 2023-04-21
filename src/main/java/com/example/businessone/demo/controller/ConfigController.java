package com.example.businessone.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 这里用一句话描述这个类的作用 <br>
 * @version: 1.0 <br>
 * @author: leevi.li <br>
 * @create: 2023/3/7 9:37 <br>
 * @update: 2023/3/7 9:37 <br>
 * @since jdk11.0.5_10
 */
@Slf4j
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${test.value1}")
    private String testValue;

    @GetMapping("/getConfigValue")
    public String getConfigValue(){
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
        return testValue;
    }
}
