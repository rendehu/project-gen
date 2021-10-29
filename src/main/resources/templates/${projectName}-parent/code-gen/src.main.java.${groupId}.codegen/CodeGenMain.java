package ${groupId}.codegen;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
        DataSourceConfig.Builder dscBuilder = new DataSourceConfig.Builder("jdbc:postgresql://host:port/db",
                "postgres", "xxx");
        String path = Objects.requireNonNull(CodeGenMain.class.getClassLoader().getResource("")).getPath();
        String ${projectName}ParentPath = StrUtil.subBefore(path, "code-gen", true);
        Map<OutputFile, String> pathMap = new HashMap<>(10);

        //这里只需要dao模板生成文件 templateConfig中会去掉 service/serviceImpl/controller 的模板生成文件
        pathMap.put(OutputFile.mapper, ${projectName}ParentPath + "${projectName}-persistence/src/main/java/${groupPackagePath}/persistence/mapper/");
        pathMap.put(OutputFile.entity, ${projectName}ParentPath + "${projectName}-persistence/src/main/java/${groupPackagePath}/persistence/entity/");
        pathMap.put(OutputFile.mapperXml, ${projectName}ParentPath + "${projectName}-persistence/src/main/resources/mapper/");


        FastAutoGenerator.create(dscBuilder)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author("rdhu3@iflytek.com").fileOverride())
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent("${groupId}.persistence").pathInfo(pathMap))
                .templateConfig((scanner, builder) -> builder.disable(TemplateType.CONTROLLER, TemplateType.SERVICE, TemplateType.SERVICEIMPL))
                // 策略配置
                .strategyConfig((scanner, builder) ->
                        builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                                .entityBuilder().enableLombok().enableChainModel().idType(IdType.INPUT)
                        .build())
                //指定 freemarker 模板引擎渲染
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }

    /**
     * 处理 all 情况
     *
     * @param tables tableName  逗号分隔
     * @return 返回解析的table list
     */
    private static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
