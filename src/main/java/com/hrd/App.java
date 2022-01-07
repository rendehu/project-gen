package com.hrd;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Dict dict = Dict.create().set("projectName", scan("请输入项目简称(建议2-4个英文小写简称如:xxl):"))
                .set("projectCnName", scan("请输入项目中文名称"))
                .set("projectDesc", scan("请输入项目描述:"))
                .set("groupId", scan("请输入项目groupId: eg. org.springframework"))
                .set("author",scan("请输入项目author"));
        dict.set("groupPackagePath", ((String) dict.get("groupId")).replace(".", "/"));
        // pom
        dict.set("java_version", "${java.version}");
        dict.set("maven_compiler_source", "${maven.compiler.source}");
        dict.set("maven_compiler_target", "${maven.compiler.target}");
        dict.set("maven_compiler_compilerVersion", "${maven.compiler.compilerVersion}");
        dict.set("project_build_sourceEncoding", "${project.build.sourceEncoding}");
        dict.set("org_projectlombok_version", "${org.projectlombok.version}");
        dict.set("org_mapstruct_version", "${org.mapstruct.version}");

        dict.set("xxl_job_admin_addresses","${xxl.job.admin.addresses");
        dict.set("xxl_job_executor_appname","${xxl.job.executor.appname:${spring.application.name}}");
        dict.set("xxl_job_executor_ip","${xxl.job.executor.ip:${spring.cloud.client.ip-address:}}");
        dict.set("xxl_job_executor_port","#{${xxl.job.executor.port:${server.port:8080}+5}}");
        dict.set("xxl_job_accessToken","${xxl.job.accessToken:}");
        dict.set("xxl_job_executor_logpath","${xxl.job.executor.logpath:/home/logs/${spring.application.name}/xxl-job}");
        dict.set("xxl_job_executor_logretentiondays","${xxl.job.executor.logretentiondays:-1}");

        dict.set("pool_core_pool_size","${pool.core-pool-size:20}}");
        dict.set("pool_max_pool_size","${pool.max-pool-size:30}");
        dict.set("pool_keep_alive_time","${pool.keep-alive-time:600}");

        //log4j
        dict.set("LOG_PATH", "${LOG_PATH}");
        dict.set("LOG_PATTERN", "${LOG_PATTERN}");
        dict.set("notesStarts","/**");
        dict.set("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dict.set("empty","{}");

        String localParentPath = scan("请输入项目本地存放目录");
        localParentPath = localParentPath.endsWith(File.separator) ? localParentPath.substring(0, localParentPath.length() - 1) : localParentPath;

        System.out.println(StrUtil.format("basic data: {}", dict));

        File projectFile = FileUtil.file(new File(localParentPath));
        File templateFile = FileUtil.file("classpath:templates/${projectName}-parent");

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
