package com.springboot.demo.base.security.component;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * @description: JwtAuthorizationManager <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/4/21 <br>
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
