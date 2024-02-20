package com.hrd;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Dict dict = Dict.create()
                .set("group", scan("请输入项目组简称(建议2-个英文小写简称如:xxl):"))
                .set("companySite", scan("请输入公司域名(一级域名)"))
                .set("projectName", scan("请输入项目简称(英文小写):"))
                .set("projectCnName", scan("请输入项目中文名称"))
                .set("projectDesc", scan("请输入项目描述:"))
                .set("dbType",scan("请确认数据库类型(mysql/postgresql):"))
                .set("author", scan("请输入项目author"));
        List<String> groups = CollectionUtil.reverse(StrUtil.split(dict.get("companySite").toString(), '.'));
        //添加工程组
        groups.add(dict.get("group").toString());
        dict.set("groupId", StrUtil.join(".", groups));
        dict.set("groupIdDir",StrUtil.join("/", groups));
        System.out.println("-----> groupId=" + dict.get("groupId"));
        //project校验
        String projectName = (String) dict.get("projectName");
        Assert.isTrue(Pattern.matches("[a-z]+-*[a-z]+", projectName), "项目名称必须英文小写字母开头,多个英文单词只能'-'连接");
        String camelProjectName = StrUtil.toCamelCase(projectName.replace("-", "_"));

        dict.set("camelProjectName", camelProjectName);

        dict.set("groupPackagePath", ((String) dict.get("groupId")).replace(".", "/"));
        // pom
        dict.set("java_version", "${java.version}");
        dict.set("maven_compiler_source", "${maven.compiler.source}");
        dict.set("maven_compiler_target", "${maven.compiler.target}");
        dict.set("maven_compiler_compilerVersion", "${maven.compiler.compilerVersion}");
        dict.set("project_build_sourceEncoding", "${project.build.sourceEncoding}");
        dict.set("org_projectlombok_version", "${org.projectlombok.version}");
        dict.set("org_mapstruct_version", "${org.mapstruct.version}");

        //log4j
        dict.set("LOG_PATH", "${LOG_PATH}");
        dict.set("LOG_PATTERN", "${LOG_PATTERN}");
        dict.set("notesStarts", "/**");
        dict.set("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dict.set("empty", "{}");
        //parent
        dict.set("parentGroupId", StrUtil.subBefore((CharSequence) dict.get("groupId"), ".", true));

        String localParentPath = scan("请输入项目本地存放目录");
        localParentPath = localParentPath.endsWith(File.separator) ? localParentPath.substring(0, localParentPath.length() - 1) : localParentPath;
        System.out.println(StrUtil.format("basic data: {}", dict));

        File projectFile = FileUtil.file(new File(localParentPath));
        File templateFile = FileUtil.file("classpath:templates/${group}-${projectName}-svc");

        new FileProcessor(templateFile, projectFile, dict).process();
    }


    private static String scan(String memo) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(memo);
        String nextLine = scanner.nextLine();
        if (StrUtil.isBlank(nextLine)) {
            // 如果输入空行继续等待
            return scanner.next();
        }
        return nextLine;
    }
}
