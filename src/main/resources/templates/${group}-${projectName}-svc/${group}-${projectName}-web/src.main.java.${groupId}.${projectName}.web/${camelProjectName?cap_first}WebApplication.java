package ${groupId}.${projectName}.web;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ${author}
 * @since ${currentDate}
 */
@SpringBootApplication(scanBasePackages = "${groupId}")
@EnableSpringUtil
public class ${camelProjectName?cap_first}WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(${projectName?cap_first}WebApplication.class, args);
    }
}
