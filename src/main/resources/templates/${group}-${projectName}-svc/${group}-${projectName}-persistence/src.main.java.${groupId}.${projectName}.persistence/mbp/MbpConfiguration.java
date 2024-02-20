package ${groupId}.${projectName}.persistence.mbp;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author ${author}
 * @since 2022-12-07 11:19:46
 */
<#if true>
@Configuration
public class MbpConfiguration {

    /**
     * 方法注入
     * @return
     */
    @Bean
    public DefaultSqlInjector getSqlInjector() {
        return new DefaultSqlInjector() {
            @Override
            public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
                List<AbstractMethod> methods = super.getMethodList(mapperClass, tableInfo);
                methods.add(new InsertBatchMethod());
                return methods;
            }
        };
    }

</#if>
}
