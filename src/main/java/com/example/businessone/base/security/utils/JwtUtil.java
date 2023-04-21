package com.example.businessone.base.security.utils;

import com.example.businessone.base.security.component.JwtUserDetails;
import com.example.businessone.base.utils.JacksonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: jwt工具类 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/4/10 <br>
 */
@Component
public class JwtUtil {

    @Value("${jwt.securityKey}")
    private String jwt_security_key;

    @Value("${jwt.expiration}")
    private Long jwt_expiration;

    /*
     * @description: 创建jwt <br>
     * @create: 2023/4/10 10:42 <br>
     * @param playload 自定义的jwt载体
     * @return java.lang.String
     */
    public String createJwt(Map<String, Object> playload){
        JwtBuilder jwtBuilder = Jwts.builder();
        //设置加密算法和秘钥
        jwtBuilder.signWith(SignatureAlgorithm.HS256, jwt_security_key);
        //设置压缩压缩
        jwtBuilder.compressWith(CompressionCodecs.DEFLATE);
        //设置claims
        Claims claims = new DefaultClaims();
        //过期时间
        Date expiration = new Date(System.currentTimeMillis() + jwt_expiration);
        claims.setExpiration(expiration);
        //自定义参数
        claims.putAll(playload);
        jwtBuilder.setClaims(claims);
        return jwtBuilder.compact();
    }

    /*
     * @description: 校验jwt <br>
     * @create: 2023/4/10 15:09 <br>
     * @param jwtToken
     * @return boolean
     */
    public boolean checkJwt(String jwtToken){
        return Jwts.parser().isSigned(jwtToken);
    }

    /*
     * @description: 解析jwt <br>
     * @create: 2023/4/10 15:13 <br>
     * @param jwtToken
     * @return io.jsonwebtoken.Claims
     */
    public JwtUserDetails parseJwt(String jwtToken){
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(jwt_security_key).parseClaimsJws(jwtToken).getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            return null;
        } catch (SignatureException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
        JwtUserDetails jwtUserDetails = JacksonUtil.mapToObject(claims, JwtUserDetails.class);
        return jwtUserDetails;
    }
}
