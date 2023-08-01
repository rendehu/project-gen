package $

{groupId}.codegen;

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

/**
 * mbp code generator
 *
 * @author ${author}
 * @since ${currentDate}
 */
public class CodeGenMain {


    public static void main(String[] args) {

        //数据源配置
        DataSourceConfig.Builder dscBuilder = new DataSourceConfig.Builder("jdbc:mysql://172.30.11.16:3306/cdss_dev",
                "root", "iflytek!");
        String path = Objects.requireNonNull(CodeGenMain.class.getClassLoader().getResource("")).getPath();
        String $ {
            camelProjectName
        } ParentPath = StrUtil.subBefore(path, "code-gen", true);
        Map<OutputFile, String> pathMap = new HashMap<>(10);

        //这里只需要dao模板生成文件 templateConfig中会去掉 service/serviceImpl/controller 的模板生成文件
        pathMap.put(OutputFile.mapper, $ {
            camelProjectName
        } ParentPath + "${projectName}-persistence/src/main/java/${groupPackagePath}/persistence/mapper/");
        pathMap.put(OutputFile.entity, $ {
            camelProjectName
        } ParentPath + "${projectName}-persistence/src/main/java/${groupPackagePath}/persistence/entity/");
        pathMap.put(OutputFile.mapperXml, $ {
            camelProjectName
        } ParentPath + "${projectName}-persistence/src/main/resources/mapper/");


        FastAutoGenerator.create(dscBuilder)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author("452151476@qq.com").fileOverride())
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent("com.iflytek.medical.emrgc.persistence").pathInfo(pathMap))
                .templateConfig((scanner, builder) -> builder.disable(TemplateType.CONTROLLER)
                        .mapper("/templates/Mapper.java")
                        .service("/templates/Service.java")
                        .serviceImpl("/templates/ServiceImpl.java")
                )                // 策略配置
                .strategyConfig((scanner, builder) ->
                        cfgTable(scanner.apply("请输入表名，多张表使用','分隔;全量直接回车'"), builder)
                                .entityBuilder()
                                .enableLombok()
                                .enableChainModel()
                                .idType(IdType.INPUT)
                                .build())
                //指定 freemarker 模板引擎渲染
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

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
