package com.springboot.demo.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: jackson工具类 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/4/10 <br>
 */
@Component
public class JacksonUtil {

    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        JacksonUtil.objectMapper = objectMapper;
    }

    /*
     * @description: 对象转json字符串 <br>
     * @create: 2023/4/10 11:04 <br>
     * @param obj
     * @return java.lang.String
     */
    public static String toJson(Object obj) {
        if(null == obj){
            return null;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description: json字符串转对象 <br>
     * @create: 2023/4/21 15:09 <br>
     * @param json
     * @param T
     * @return T
     */
    public static <T> T jsonToObject(String json, Class<T> T){
        if(null == json || json.length() == 0){
            return null;
        }
        try {
            return objectMapper.readValue(json, T);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * @description: 对象转map <br>
     * @create: 2023/4/10 17:01 <br>
     * @param obj
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String, Object> objectToMap(Object obj){
        if(null == obj){
            return null;
        }
        Map<String, Object> map = objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
        return map;
    }

    /**
     * @description: map转对象 <br>
     * @create: 2023/4/21 15:09 <br>
     * @param map
     * @param T
     * @return java.lang.Object
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> T){
        Object obj = objectMapper.convertValue(map, T);
        return (T) obj;
    }

    /*
     * @description: 对象转list <br>
     * @create: 2023/9/12 10:55 <br>
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public static<T> List<T> objectToArray(Object obj){
        if(null == obj){
            return null;
        }
        return objectMapper.convertValue(obj, new TypeReference<List<T>>() {});
    }
}
