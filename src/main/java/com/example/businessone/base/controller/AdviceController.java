package com.example.businessone.base.controller;

import com.example.businessone.base.exception.VoluntaryException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: controller全局配置<br>
 * @version: 1.0 <br>
 * @since jdk11.0.5_10
 */
@Slf4j
@RestControllerAdvice
public class AdviceController {

    private final String API_ERROR = "API访问异常";

    /**
     * @description:  RuntimeException统一处理方法<br>
     * @version: 1.0 <br>
     * @param: e
     * @return org.springframework.http.ResponseEntity<?>
     */
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> runtimeExceptionHandler(RuntimeException e){
        log.error(API_ERROR,e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系统错误，请联系维护人员");
    }

    /**
     * @description: 主动抛出的异常处理 <br>
     * @version: 1.0 <br>
     * @param: e
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     */
    @ExceptionHandler({VoluntaryException.class})
    public ResponseEntity<String> voluntaryExceptionHandler(VoluntaryException e){
        if(ObjectUtils.isEmpty(e)){
            log.error(API_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        log.error(API_ERROR,e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
