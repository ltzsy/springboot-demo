package com.example.businessone.demo.controller;

import com.example.businessone.base.exception.VoluntaryException;
import com.example.businessone.base.security.component.JwtUserDetails;
import com.example.businessone.demo.controller.entity.Student;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 这里用一句话描述这个类的作用 <br>
 * @version: 1.0 <br>
 * @author: leevi.li <br>
 * @create: 2023/3/15 11:40 <br>
 * @update: 2023/3/15 11:40 <br>
 * @since jdk11.0.5_10
 */
@RestController //告诉框架这是一个controller
@RequestMapping("/first") //controller的path
public class FirstController {

    @GetMapping("/test1") //告诉框架这是一个get接口方法，且path是"/test",
    public String tset1(){
        return "test1";
    }

    @PostMapping("/test2")
    public void test2(@RequestBody Student student){
        System.out.println(student);
    }

    @GetMapping("/test3")
    public ResponseEntity<?> test3(){
        Student student = new Student();
        student.setName("张三 ");
        student.setAge(11);
        student.setCreateDate(new Date());
        return ResponseEntity.ok(student);
    }

    @GetMapping("/test4")
    public void test4(){
        throw new RuntimeException("未知的错误");
        //返回“系统错误，请联系维护人员”
    }

    @GetMapping("/test5")
    public void test5(){
        throw new VoluntaryException("主动抛出的异常");
        //返回“主动抛出的异常”
    }
}
