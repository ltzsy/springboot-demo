package com.example.businessone.base.security.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @description: jwt用户信息 <br>
 * @author: leevi.li <br>
 * @create: 2023/4/3 <br>
 */
@Data
public class JwtUserDetails implements UserDetails {

    private Long userid;

    private String username;

    private String password;

    private List<JwtGrantedAuthority> authorities;

    private String jwt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    /**
     * @description: 添加权限 <br>
     * @create: 2023/4/21 15:16 <br>
     * @param authoritie
     * @return void
     */
    public void addJwtGrantedAuthority(String authoritie){
        if(CollectionUtils.isEmpty(authorities)){
            authorities = new ArrayList<>();
        }
        JwtGrantedAuthority jwtGrantedAuthority = new JwtGrantedAuthority(authoritie);
        authorities.add(jwtGrantedAuthority);
    }

}
