package ${groupId}.persistence;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ${author}
 * @since ${currentDate}
 */
@Configuration
@ConditionalOnClass(MybatisPlusAutoConfiguration.class)
@MapperScan("${groupId}.persistence.mapper")
public class PersistenceConfiguration {


    /**
     * PaginationInnerInterceptor  for pagination
     * OptimisticLockerInnerInterceptor for OptimisticLocker
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(MybatisPlusProperties plusProperties) {
        //logic-delete
        GlobalConfig.DbConfig dbConfig = plusProperties.getGlobalConfig().getDbConfig();
        dbConfig.setLogicDeleteField("deleted");

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页、乐观锁
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }



}
