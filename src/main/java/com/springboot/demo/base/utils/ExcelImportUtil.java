package com.springboot.demo.base.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springboot.demo.base.annotation.ExcelField;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: excel导入工具类 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/9/20 <br>
 */
public class ExcelImportUtil {

    /*
     * @description: 读取excel数据 <br>
     * @create: 2023/9/20 16:12 <br>
     * @param file
     * @param tClass
     * @return java.util.List<T>
     */
    public static<T> List<T> readExcel(MultipartFile file, Class<T> tClass){
        if(file.isEmpty()){
            return new ArrayList<>();
        }
        //获取列名与属性名的映射
        Map<String, String> feildMap = getFeildMap(tClass);
        if(ObjectUtils.isEmpty(feildMap)){
            return new ArrayList<>();
        }
        InputStream inputStream = null;
        Workbook workbook = null;
        try {
            //解析文件
            inputStream = file.getInputStream();
            workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            int lastCellNum = sheet.getRow(0).getLastCellNum();
            //解析表头
            Row headerRow = sheet.getRow(0);
            List<String> headerNameList = new ArrayList<>();
            for (int i = 0; i < lastCellNum; i++) {
                headerNameList.add(headerRow.getCell(i).getStringCellValue());
            }
            //读取数据
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                Map<String, Object> rowValueMap = new HashMap<>();
                for (int j = 0; j < lastCellNum; j++) {
                    String key = feildMap.get(headerNameList.get(j));
                    Object value = getCellValue(row.getCell(j));
                    rowValueMap.put(key, value);
                }
                dataList.add(rowValueMap);
            }
            workbook.close();
            inputStream.close();
            List<T> list = convertToList(dataList);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(workbook != null){
                    workbook.close();
                }
                if(inputStream !=null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    /*
     * @description: 解析导入的实体，获取列名与属性名的映射 <br>
     * @create: 2023/9/20 16:27 <br>
     * @param tClass
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private static<T> Map<String, String> getFeildMap(Class<T> tClass){
        Field[] fields = tClass.getDeclaredFields();
        if(ObjectUtils.isEmpty(fields)){
            return null;
        }
        Map<String, String> keyMap = new HashMap<>();
        for(Field field : fields){
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if(ObjectUtils.isNotEmpty(excelField)){
                keyMap.put(excelField.name(), field.getName());
            }
        }
        return keyMap;
    }

    /*
     * @description: 获取单元格的值 <br>
     * @create: 2023/9/20 16:47 <br>
     * @param cell
     * @return java.lang.Object
     */
    private static Object getCellValue(Cell cell){
        if(cell == null){
            return null;
        }
        CellType cellType = cell.getCellType();
        if(cellType.equals(CellType.BLANK)){
            return null;
        }
        //字符串
        if (cellType.equals(CellType.STRING)){
            return cell.getStringCellValue();
        }
        if(cellType.equals(CellType.NUMERIC)){
            //日期
            if(HSSFDateUtil.isCellDateFormatted(cell)){
                return cell.getDateCellValue();
            }else{
                //数值
                return cell.getNumericCellValue();
            }
        }
        //布尔
        if(cellType.equals(CellType.BOOLEAN)){
            return cell.getBooleanCellValue();
        }
        return null;
    }

    /*
     * @description: 转list <br>
     * @create: 2023/9/20 17:54 <br>
     * @param obj
     * @return java.util.List<T>
     */
    private static<T> List<T> convertToList(Object obj){
        ObjectMapper objectMapper = new ObjectMapper();
        //日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //设置时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        //序列化-忽略null值的属性
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        //序列化-允许序列化空对象
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //反序列化-在遇到未知属性的时候不抛出异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        if(null == obj){
            return null;
        }
        return objectMapper.convertValue(obj, new TypeReference<List<T>>() {});
    }
}
