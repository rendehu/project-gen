package ${groupId}.codegen;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * mbp code generator
 *
 * @author rdhu3
 * @since 2023-12-14 12:49:12
 */
public class CodeGenMain {


    public static void main(String[] args) {

        //数据源配置
        <#if dbType="postgresql">
        DataSourceConfig.Builder dscBuilder = new DataSourceConfig.Builder("jdbc:postgresql://172.31.186.107:5432/${group}_dev?currentSchema=${group}",
                "u_dprs", "Fpva_123456")
                .schema("${projectName}");
        <#else>
        DataSourceConfig.Builder dscBuilder = new DataSourceConfig.Builder("jdbc:mysql://172.30.11.16:3306/${group}_dev?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
                "root", "iflytek!"
        </#if>
        String path = Objects.requireNonNull(CodeGenMain.class.getClassLoader().getResource("")).getPath();
        String evalParentPath = StrUtil.subBefore(path, "code-gen", true);
        Map<OutputFile, String> pathMap = new HashMap<>(10);

        //这里只需要dao模板生成文件 templateConfig中会去掉 service/serviceImpl/controller 的模板生成文件
        pathMap.put(OutputFile.mapper, evalParentPath + "${group}-${projectName}-persistence/src/main/java/${groupIdDir}/${projectName}/persistence/mapper/");
        pathMap.put(OutputFile.entity, evalParentPath + "${group}-${projectName}-persistence/src/main/java/${groupIdDir}/${projectName}/persistence/entity/");
        pathMap.put(OutputFile.mapperXml, evalParentPath + "${group}-${projectName}-persistence/src/main/resources/mapper/");

        FastAutoGenerator.create(dscBuilder)
                // 全局配置  .fileOverride()
                .globalConfig((scanner, builder) -> builder.author("rdhu3@iflytek.com").fileOverride())
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent("${groupId}.${projectName}.persistence").pathInfo(pathMap))
                .templateConfig((scanner, builder) -> builder.disable(TemplateType.CONTROLLER)
                        .mapper("/templates/Mapper.java")
                        .service("/templates/Service.java")
                        .serviceImpl("/templates/ServiceImpl.java")
                )                // 策略配置
                .strategyConfig((scanner, builder) ->
                        cfgTable(parseTables(), builder)
                                .addTablePrefix("t_")
                                .entityBuilder()
                                .enableLombok()
                                .enableChainModel()
                                .idType(IdType.ASSIGN_ID)
                                .enableRemoveIsPrefix()
                                .build())
                //指定 freemarker 模板引擎渲染
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }


    public static String parseTables(){

        System.out.println("请输入表名，多张表使用','分隔;全量输入*回车");
        Scanner scanner = new Scanner(System.in);
        String tables = scanner.nextLine();
        if (tables.contains("*")){
            return "";
        }
        return tables;
    }

    /**
     * 处理 all 情况
     *
     * @param tableStrategy tableName  逗号分隔
     * @return 返回解析的table list
     */
    private static StrategyConfig.Builder cfgTable(String tableStrategy, StrategyConfig.Builder builder) {
        if (StrUtil.isBlank(tableStrategy)) {
            return builder;
        }
        builder.addInclude(tableStrategy.split(","));
        return builder;
    }
}

