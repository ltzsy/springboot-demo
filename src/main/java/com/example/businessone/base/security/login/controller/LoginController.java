package com.example.businessone.base.security.login.controller;

import com.example.businessone.base.security.component.JwtUserDetails;
import com.example.businessone.base.security.login.controller.dto.LoginDTO;
import com.example.businessone.base.security.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 登录api <br>
 * @author: leevi.li <br>
 * @create: 2023/3/31 <br>
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<JwtUserDetails> login(@RequestBody LoginDTO loginDTO){
        JwtUserDetails jwtUserDetails = loginService.login(loginDTO);
        return ResponseEntity.ok(jwtUserDetails);
    }

}
