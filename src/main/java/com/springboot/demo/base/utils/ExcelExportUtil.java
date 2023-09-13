package com.springboot.demo.base.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springboot.demo.base.annotation.ExcelField;
import com.springboot.demo.base.annotation.ExcelHeader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * @description: excel工具类 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/9/12 <br>
 */
public class ExcelExportUtil {

    /*
     * @description: 导出 <br>
     * @create: 2023/9/12 16:28 <br>
     * @param response
     * @param data
     * @param clazz
     * @return void
     */
    public static<T> void export(HttpServletResponse response, List<T> data, Class<T> clazz){
        if(ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(data)){
            return;
        }
        //解析表头
        ExcelHeader excelHeader = getExcelHeader(clazz);
        if(ObjectUtils.isEmpty(excelHeader)){
            return;
        }
        //解析字段
        List<String> keyList = new ArrayList<>();
        List<ExcelField> excelFieldList = new ArrayList<>();
        getExcelFields(clazz, keyList, excelFieldList);
        if(ObjectUtils.isEmpty(keyList)){
            return;
        }
        //创建workbook
        Workbook workbook = new SXSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet(excelHeader.sheetName());
        //创建表头
        Row row = createHeader(workbook, sheet, excelHeader, excelFieldList);
        //写入数据
        setData(data, sheet, keyList, excelFieldList);
        //输出文件
        try {
            String fileName = URLEncoder.encode(excelHeader.fileName(), "UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("ISO8859-1")));
            response.setHeader("filename", fileName);
            workbook.write(response.getOutputStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * @description: 设置数据 <br>
     * @create: 2023/9/13 9:33 <br>
     * @param data
     * @param sheet
     * @param keyList
     * @return void
     */
    private static<T> void setData(List<T> data, Sheet sheet, List<String> keyList, List<ExcelField> excelFieldList){
        List<Map<String, Object>> dataList = convertDataToJson(data);
        Map<String, Object> rowData;
        String dataKey;
        Object value;
        ExcelField excelField;
        for (int i = 0; i < dataList.size(); i++) {
            Row row = sheet.createRow(i+1);
            rowData = dataList.get(i);
            for (int j = 0; j < keyList.size(); j++) {
                dataKey = keyList.get(j);
                value = rowData.get(dataKey);
                if(ObjectUtils.isEmpty(value)){
                    continue;
                }
                Cell cell = row.createCell(j);
                cell.setCellValue(value.toString());
            }
        }
    }

    /*
     * @description: 创建表头 <br>
     * @create: 2023/9/12 17:28 <br>
     * @param workbook
     * @param sheet
     * @param excelFieldList
     * @return org.apache.poi.ss.usermodel.Row
     */
    private static Row createHeader(Workbook workbook, Sheet sheet, ExcelHeader excelHeader,List<ExcelField> excelFieldList){
        //创建行
        Row row = sheet.createRow(0);
        //创建行样式
        CellStyle cellStyle = workbook.createCellStyle();
        //单元格颜色
        cellStyle.setFillForegroundColor(excelHeader.headColor().getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //字体
        Font font =  workbook.createFont();
        font.setBold(excelHeader.headBold());
        font.setFontName("宋体");
        cellStyle.setFont(font);
        for (int i = 0; i < excelFieldList.size(); i++) {
            ExcelField excelField = excelFieldList.get(i);
            Cell cell = row.createCell(i);
            cell.setCellValue(excelField.name());
            cell.setCellStyle(cellStyle);
            sheet.setColumnWidth(i+1, excelField.width() * 10 );
        }
        return row;
    }

    /*
     * @description: 获取excel头注解信息 <br>
     * @create: 2023/9/12 10:28 <br>
     * @param clazz
     * @return com.springboot.demo.base.annotation.ExcelHeader
     */
    private static<T> ExcelHeader getExcelHeader(Class<T> clazz){
        ExcelHeader excelHeader = clazz.getAnnotation(ExcelHeader.class);
        return excelHeader;
    }

    /*
     * @description: 获取excel字段注解信息 <br>
     * @create: 2023/9/12 10:31 <br>
     * @param clazz
     * @return java.util.List<com.springboot.demo.base.annotation.ExcelField>
     */
    private static<T> void getExcelFields(Class<T> clazz, List<String> keyList, List<ExcelField> excelFieldList){
        Field[] fields = clazz.getDeclaredFields();
        if(ObjectUtils.isEmpty(fields)){
            return;
        }
        Map<String, String> keyMap = new HashMap<>();
        for(Field field : fields){
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if(ObjectUtils.isNotEmpty(excelField)){
                keyMap.put(excelField.name(), field.getName());
                excelFieldList.add(excelField);
            }
        }
        if(ObjectUtils.isNotEmpty(excelFieldList)){
            excelFieldList = excelFieldList.stream().sorted(Comparator.comparing(ExcelField::order)).collect(
                Collectors.toList());
        }
        for (ExcelField excelField : excelFieldList) {
            keyList.add(keyMap.get(excelField.name()));
        }
    }

    /*
     * @description: 将数据转未json <br>
     * @create: 2023/9/13 10:00 <br>
     * @param data
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    private static<T> List<Map<String, Object>> convertDataToJson(List<T> data){
        ObjectMapper objectMapper = new ObjectMapper();
        //日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //设置时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        //序列化-忽略null值的属性
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        //序列化-允许序列化空对象
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        List<Map<String, Object>> dataList = objectMapper.convertValue(data, List.class);
        return dataList;
    }
}
