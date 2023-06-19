package com.springboot.demo.base.security.component;

import com.springboot.demo.base.security.utils.JwtUtil;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

/**
 * @description: JwtAuthorizationManager <br>
 * @author: leevi.li <br>
 * @create: 2023/3/31 <br>
 */
@Slf4j
@Component
public class JwtAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
        RequestAuthorizationContext object) {
        String requestURI = object.getRequest().getRequestURI();
        log.info("身份认证:{}", requestURI);
        //获取请求头里面的jwt令牌
        HttpServletRequest httpServletRequest = object.getRequest();
        String jwtToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.isBlank(jwtToken)){
            jwtToken = httpServletRequest.getHeader("authorization");
        }
        if(StringUtils.isBlank(jwtToken)){
            return new AuthorizationDecision(false);
        }
        //检验jwt令牌
        jwtToken = jwtToken.replace("bearer ", "");
        if(!JwtUtil.checkJwt(jwtToken)){
            return new AuthorizationDecision(false);
        }
        //解析jwt令牌
        JwtUserDetails jwtUserDetails = JwtUtil.parseJwt(jwtToken);
        if(ObjectUtils.isEmpty(jwtUserDetails)){
            return new AuthorizationDecision(false);
        }
        //校验权限
        //此处自由发挥即可
        RequestMatcher requestMatcher = new AntPathRequestMatcher("/first/**");
        requestMatcher.matches(httpServletRequest);
        //构建authentication
        JwtAuthenticationToken token = new JwtAuthenticationToken(jwtUserDetails.getAuthorities());
        token.setDetails(jwtUserDetails);
        token.setAuthenticated(true);
        //设置securityContext
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(token);
        return new AuthorizationDecision(true);
    }
}
