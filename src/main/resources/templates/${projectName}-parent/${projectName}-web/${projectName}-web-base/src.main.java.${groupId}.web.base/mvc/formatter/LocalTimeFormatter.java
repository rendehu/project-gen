package ${groupId}.web.base.mvc.formatter;

import cn.hutool.core.util.StrUtil;
import ${groupId}.util.${projectName?cap_first}Const;
import ${groupId}.util.util.RegexUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

/**
 * @author ${author}
 * @since ${currentDate}
 */
public class LocalTimeFormatter implements Formatter<LocalTime> {


    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        if (RegexUtils.matchTime(s)) {
            return LocalTime.parse(s, ${projectName?cap_first}Const.STD_LOCAL_TIME);
        } else {
            throw new ParseException(StrUtil.format("Unparseable time:\"{}\"", s), -1);
        }
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return localTime.format(${projectName?cap_first}Const.STD_LOCAL_TIME);
    }
}
