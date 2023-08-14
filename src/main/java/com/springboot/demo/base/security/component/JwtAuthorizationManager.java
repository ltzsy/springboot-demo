package com.springboot.demo.base.security.component;

import com.springboot.demo.base.security.config.InterceptUrlConfig;
import com.springboot.demo.base.security.utils.JwtUtil;
import com.springboot.demo.base.utils.PropertyUtil;
import java.util.List;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

    @Autowired
    private InterceptUrlConfig interceptUrlConfig;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
        RequestAuthorizationContext object) {
        String requestURI = object.getRequest().getRequestURI();
        log.info("jwt授权:{}", requestURI);
        //获取请求头里面的jwt令牌
        HttpServletRequest httpServletRequest = object.getRequest();
        String jwtToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.isBlank(jwtToken)){
            jwtToken = httpServletRequest.getHeader("authorization");
        }
        if(StringUtils.isBlank(jwtToken)){
            throw new AccessDeniedException("jwt令牌为空，请登录");
        }
        //检验jwt令牌
        jwtToken = jwtToken.replace("bearer ", "");
        if(!JwtUtil.checkJwt(jwtToken)){
            throw new AccessDeniedException("jwt令牌不合法");
        }
        //解析jwt令牌
        JwtUserDetails jwtUserDetails = null;
        try {
            jwtUserDetails = JwtUtil.parseJwt(jwtToken);
        } catch (Exception e) {
            throw new AccessDeniedException("jwt令牌解析失败");
        }
        //校验权限
        if(!checkByUrl(httpServletRequest, jwtUserDetails)){
            throw new AccessDeniedException("无权限访问该资源");
        }
        //构建authentication
        JwtAuthenticationToken token = new JwtAuthenticationToken(jwtUserDetails.getAuthorities());
        token.setDetails(jwtUserDetails);
        token.setAuthenticated(true);
        //设置securityContext
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(token);
        return new AuthorizationDecision(true);
    }

    /*
     * @description: 校验url权限 <br>
     * @create: 2023/6/25 10:32 <br>
     * @param request
     * @param jwtUserDetails
     * @return boolean
     */
    private boolean checkByUrl(HttpServletRequest request, JwtUserDetails jwtUserDetails){
        if(CollectionUtils.isEmpty(interceptUrlConfig.getAdminUrlList())){
            return true;
        }
        //判断是否为管理员权限url
        boolean isAdminUrl = false;
        for (String adminUrl : interceptUrlConfig.getAdminUrlList()){
            RequestMatcher requestMatcher = new AntPathRequestMatcher(adminUrl);
            if(requestMatcher.matches(request)){
                isAdminUrl = true;
                break;
            }
        }
        if(CollectionUtils.isNotEmpty(interceptUrlConfig.getNoninterceptUrlList())){
            for (String noninterceptUrl : interceptUrlConfig.getNoninterceptUrlList()){
                RequestMatcher requestMatcher = new AntPathRequestMatcher(noninterceptUrl);
                if(requestMatcher.matches(request)){
                    isAdminUrl = false;
                    break;
                }
            }
        }
        boolean result = true;
        if(isAdminUrl){
            //判断是否为管理员角色
            List<JwtGrantedAuthority> jwtGrantedAuthorityList = (List<JwtGrantedAuthority>) jwtUserDetails.getAuthorities();
            String adminRoleId = PropertyUtil.getString("user.admin-role-id");
            JwtGrantedAuthority jwtGrantedAuthority = new JwtGrantedAuthority(adminRoleId);
            result = jwtGrantedAuthorityList.contains(jwtGrantedAuthority);
        }
        return result;
    }
}
