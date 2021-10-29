package ${groupId}.web.base.mvc;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import ${groupId}.util.${projectName?cap_first}Const;
import ${groupId}.web.base.mvc.converter.StringToEnumConverterFactory;
import ${groupId}.web.base.mvc.formatter.LocalDateFormatter;
import ${groupId}.web.base.mvc.formatter.LocalDateTimeFormatter;
import ${groupId}.web.base.mvc.formatter.LocalTimeFormatter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author ${author}
 * @since ${currentDate}
 */
@Configuration
public class BaseWebMvcConfigurer implements WebMvcConfigurer {


    /**
     * Add Formatter
     * registered by default.
     *
     * @param registry registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
        registry.addFormatterForFieldType(LocalTime.class, new LocalTimeFormatter());
        registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
        registry.addConverterFactory(getStringToEnumConverterFactory());
    }

    @Bean
    public StringToEnumConverterFactory getStringToEnumConverterFactory() {
        return new StringToEnumConverterFactory();
    }

    /**
     * 注入 jackson 针对 LocalDate 字段 序列化和反序列化
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    @Primary
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(
                LocalDateTime.class, new LocalDateTimeSerializer(${projectName?cap_first}Const.STD_LOCAL_DATETIME))
                .serializerByType(LocalDate.class, new LocalDateSerializer(${projectName?cap_first}Const.STD_LOCAL_DATE))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(${projectName?cap_first}Const.STD_LOCAL_TIME))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(${projectName?cap_first}Const.STD_LOCAL_DATETIME))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(${projectName?cap_first}Const.STD_LOCAL_DATE))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(${projectName?cap_first}Const.STD_LOCAL_TIME));
    }

}
