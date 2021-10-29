package ${groupId}.util.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具
 *
 * @author ${author}
 * @since ${currentDate}
 */
public class RegexUtils {

    private RegexUtils() {
    }

    public static final Pattern STD_DATE_PATTERN = Pattern.compile("\\d{1,4}-\\d{2}-\\d{2}");

    public static final Pattern STD_TIME_PATTERN = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");

    public static final Pattern STD_TIMESTAMP_PATTERN = Pattern.compile("\\d{1,4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");

    public static final Pattern STD_IP_PATTERN = Pattern.compile("([1-9]\\d*)\\.([1-9]\\d*)\\.([1-9]\\d*)\\.([1-9]\\d*)");


    /**
     * url 地址 pattern
     */
    public static final Pattern URL_PATTERN = Pattern.compile("((http[s]?)://([a-zA-Z0-9.\\-_:]+))[/]?.*");
    /**
     * 正整数集合
     */
    public static final Pattern POSITIVE_NUMBER_PATTERN = Pattern.compile("[1-9]\\d*");


    /**
     * 日期匹配 yyyy-MM-dd
     *
     * @param dateStr
     * @return
     */
    public static boolean matchDate(String dateStr) {
        return STD_DATE_PATTERN.matcher(dateStr).matches();
    }

    /**
     * 时分匹配  HH:mm:ss
     *
     * @param timeStr
     * @return
     */
    public static boolean matchTime(String timeStr) {
        return STD_TIME_PATTERN.matcher(timeStr).matches();
    }

    /**
     * 时间戳匹配 yyyy-MM-dd HH:mm:ss
     *
     * @param timestampStr
     * @return
     */
    public static boolean matchTimestamp(String timestampStr) {
        Matcher matcher = STD_TIMESTAMP_PATTERN.matcher(timestampStr);
        int count = matcher.groupCount();
        boolean flag = matcher.matches();
        if (flag) {
            for (int i = 0; i < count; i++) {
                try {
                    flag = Integer.parseInt(matcher.group(i)) < 256;
                } catch (NumberFormatException e) {
                    flag = false;
                }
                if (!flag) {
                    break;
                }
            }
        }
        return flag;

    }

    public static boolean matchIp(String ip) {
        return STD_IP_PATTERN.matcher(ip).matches();
    }


}
