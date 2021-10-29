package ${groupId}.web.base.mvc.converter;

import ${groupId}.util.util.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Objects;

/**
 * @author ${author}
 * @since ${currentDate}
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> aClass) {
        T[] ts = aClass.getEnumConstants();
        if (null != ts) {
            return s -> {
                for (T t : ts) {
                    if (Objects.equals(s, t.name())) {
                        return t;
                    }
                }
                return null;
            };
        }
        return null;
    }
}
