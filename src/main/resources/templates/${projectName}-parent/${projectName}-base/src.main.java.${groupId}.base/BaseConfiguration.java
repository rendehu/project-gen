package ${groupId}.base;

import cn.hutool.extra.spring.EnableSpringUtil;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rdhu3
 * @since 2021-12-28 21:41:51
 */
@Configuration
@EnableSpringUtil
@Slf4j
public class BaseConfiguration {


    @Value("${xxl_job_admin_addresses}")
    String adminAddresses;
    @Value("${xxl_job_executor_appname}")
    String appName;
    @Value("${xxl_job_executor_ip}")
    String ip;
    @Value("${xxl_job_executor_port}")
    Integer port;
    @Value("${xxl_job_accessToken}")
    String accessToken;
    @Value("${xxl_job_executor_logpath}")
    String logPath;
    @Value("${xxl_job_executor_logretentiondays}")
    int logRetentionDays;

    @Bean
    @ConditionalOnProperty(value = {"xxl.job.enabled"}, matchIfMissing = true)
    public XxlJobExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job 2.* config init .");
        XxlJobExecutor xxlJobExecutor = new XxlJobSpringExecutor();
        xxlJobExecutor.setAppname(appName);
        xxlJobExecutor.setAdminAddresses(adminAddresses);
        xxlJobExecutor.setIp(ip);
        xxlJobExecutor.setPort(port);
        xxlJobExecutor.setAccessToken(accessToken);
        xxlJobExecutor.setLogPath(logPath);
        xxlJobExecutor.setLogRetentionDays(logRetentionDays);
        return xxlJobExecutor;
    }
}
