package ${groupId}.web.base.mvc.formatter;

import cn.hutool.core.util.StrUtil;
import ${groupId}.util.${projectName?cap_first}Const;
import ${groupId}.util.util.RegexUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

/**
 * @author ${author}
 * @since ${currentDate}
 */
public class LocalDateFormatter implements Formatter<LocalDate> {


    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        if (RegexUtils.matchDate(s)) {
            return LocalDate.parse(s, ${projectName?cap_first}Const.STD_LOCAL_DATE);
        } else {
            throw new ParseException(StrUtil.format("Unparseable date:\"{}\"", s), -1);
        }
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        return localDate.format(${projectName?cap_first}Const.STD_LOCAL_DATE);
    }
}
