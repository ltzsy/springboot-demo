package com.example.businessone.base.security.component;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @description: 这里用一句话描述这个类的作用 <br>
 * @author: leevi.li <br>
 * @create: 2023/4/4 <br>
 */
@Data
public class JwtGrantedAuthority implements GrantedAuthority {

    private String authority;

    public JwtGrantedAuthority() {
    }

    public JwtGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
