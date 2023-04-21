package com.example.businessone.base.security.component;

import com.example.businessone.base.security.utils.JwtUtil;
import com.example.businessone.base.security.utils.PasswordEncoderUtil;
import com.example.businessone.base.utils.JacksonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

/**
 * @description: JwtUserDetailsService <br>
 * @author: leevi.li <br>
 * @create: 2023/3/31 10:17 <br>
 */
@Slf4j
@Component
public class JwtUserDetailsService implements UserDetailsManager {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //定义测试数据
        JwtUserDetails user = new JwtUserDetails();
        user.setUserid(1L);
        user.setUsername("testUser1");
        user.setPassword("$2a$10$F7Kpp2N.am/swyUSwQzaU.E5YgiQsDmsOwOg7RWFD.EKRNRGTpPvq");
        user.addJwtGrantedAuthority("admin");
        if(!user.getUsername().equals(username)){
            return null;
        }
        return user;
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        if("testUser1".equals(username)){
            return true;
        }
        return false;
    }

    /*
     * @description: buildJwt <br>
     * @create: 2023/4/6 11:38 <br>
     * @param jwtUserDetails
     * @return java.lang.String
     */
    public String buildJwt(JwtUserDetails jwtUserDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userid", jwtUserDetails.getUserid());
        claims.put("username", jwtUserDetails.getUsername());
        claims.put("authorities", jwtUserDetails.getAuthorities());
        String jwt = jwtUtil.createJwt(claims);
        return jwt;
    }

    /*
     * @description: 校验jwt是否合法 <br>
     * @create: 2023/4/6 11:43 <br>
     * @param jwt
     * @return boolean
     */
    public boolean checkJwt(String jwt){
        return false;
    }

    /*
     * @description: 解析jwt <br>
     * @create: 2023/4/6 11:47 <br>
     * @param jwt
     * @return com.example.businessone.base.security.component.JwtUserDetails
     */
    public JwtUserDetails resolvJwt(String jwt){
        return null;
    }
}
