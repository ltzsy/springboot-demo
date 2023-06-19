import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: MybatisPuls代码生成器<br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/4/21 <br>
 */
public class MybatisPulsGenerator {

    /**
     * 数据库连接
     */
    private static String url = "jdbc:mysql://10.20.0.236:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false&useTimezone=true&allowMultiQueries=true";
    /**
     * 数据库账号
     */
    private static String username = "eshoponline";
    /**
     * 数据库密码
     */
    private static String password = "lEpu4JBC5!";
    /**
     * 要生成的表名，多个用","隔开
     */
    private static String tableNames = "sys_user_role_relevance";
    /**
     * 输出目录
     */
    private static String outputDir = "F://mybatisPulsCode//";
    /**
     * 作者
     */
    private static String author = "小花卷的Dad";
    /**
     * 要过滤的表前缀，多个用","隔开
     */
    private static String tablePrefixs = "";
    /**
     * 父包名
     */
    private static String parent = "";
    /**
     * 父包模块名
     */
    private static String moduleName = "";

    public static void main(String[] args) {
        FastAutoGenerator.create(url, username, password)
            .globalConfig(builder -> {
                builder
                    .author(author) //设置作者
                    .enableSwagger() //开启swagger模式
                    .outputDir(outputDir); //指定输出目录
            })
            .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                //自定义类型转换
                int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                if (typeCode == Types.SMALLINT) {
                    return DbColumnType.INTEGER;
                }
                if(typeCode == Types.TIME || typeCode == Types.DATE || typeCode == Types.TIMESTAMP){
                    return DbColumnType.DATE;
                }
                return typeRegistry.getColumnType(metaInfo);

            }))
            .packageConfig(builder -> {
                builder.parent("") //设置父包名
                    .moduleName("") //设置父包模块名
                    .pathInfo(Collections.singletonMap(OutputFile.xml, outputDir)); //设置mapperXml生成路径
            })
            .strategyConfig(builder -> {
                builder.addInclude(tableNames.split(",")) // 设置需要生成的表名
                    .addTablePrefix(tablePrefixs.split(",")) // 设置过滤表前缀
                    .entityBuilder() //entity配置
                    .enableFileOverride() //覆盖已生成文件
                    .enableLombok() //开启lombok模型
                    .disableSerialVersionUID() //禁用生成 serialVersionUID
                    .controllerBuilder() //controller配置
                    .enableRestStyle() //开启生成@RestController 控制器
                    .enableFileOverride() //覆盖已生成文件
                    .serviceBuilder() //service配置
                    .formatServiceFileName("%sService")
                    .enableFileOverride() //覆盖已生成文件
                    .mapperBuilder() //mapper配置
                    .enableFileOverride() //覆盖已生成文件
                    .mapperAnnotation(Mapper.class); //添加Mapper注解

            })
            .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
            .execute();
    }
}
