package ${groupId}.web.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ${author}
 * @since ${currentDate}
 */
@SpringBootApplication(scanBasePackages = "${groupId}")
public class ${projectName?cap_first}PortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(${projectName?cap_first}PortalApplication.class, args);
    }
}
