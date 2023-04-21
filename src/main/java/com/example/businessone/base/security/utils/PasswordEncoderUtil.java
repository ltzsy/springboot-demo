package com.example.businessone.base.security.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description: 密码加密工具 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/4/6 <br>
 */
public class PasswordEncoderUtil {

    /*
     * @description: BCrypt编码 <br>
     * @create: 2023/4/6 11:18 <br>
     * @param str
     * @return java.lang.String
     */
    public static String bCryptEncode(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode(str);
        return result;
    }

    /*
     * @description: BCrypt匹配 <br>
     * @create: 2023/4/6 11:18 <br>
     * @param str
     * @param bCryptStr
     * @return boolean
     */
    public static boolean bCryptMatch(String str, String bCryptStr){
        if(StringUtils.isBlank(str)){
            return false;
        }
        if(StringUtils.isBlank(bCryptStr)){
            return false;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        encoder.upgradeEncoding(bCryptStr);
        return encoder.matches(str, bCryptStr);
    }
}
