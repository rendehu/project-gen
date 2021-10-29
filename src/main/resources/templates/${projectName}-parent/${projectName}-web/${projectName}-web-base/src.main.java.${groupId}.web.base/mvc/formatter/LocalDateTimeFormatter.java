package ${groupId}.web.base.mvc.formatter;

import cn.hutool.core.util.StrUtil;
import ${groupId}.util.${projectName?cap_first}Const;
import ${groupId}.util.util.RegexUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * @author ${author}
 * @since ${currentDate}
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {


    @Override
    public LocalDateTime parse(String s, Locale locale) throws ParseException {
        if (RegexUtils.matchTimestamp(s)) {
            return LocalDateTime.parse(s, ${projectName?cap_first}Const.STD_LOCAL_DATETIME);
        } else {
            throw new ParseException(StrUtil.format("Unparseable datetime:\"{}\"", s), -1);
        }
    }

    @Override
    public String print(LocalDateTime localDateTime, Locale locale) {
        return localDateTime.format(${projectName?cap_first}Const.STD_LOCAL_DATETIME);
    }
}
