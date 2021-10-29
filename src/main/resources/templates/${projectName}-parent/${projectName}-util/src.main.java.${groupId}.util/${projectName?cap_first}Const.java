package ${groupId}.util;

import java.time.format.DateTimeFormatter;

/**
 * @author ${author}
 * @since ${currentDate}
 */
public interface ${projectName?cap_first}Const {

    /**
     * 标准日期格式
     */
    DateTimeFormatter STD_LOCAL_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    DateTimeFormatter STD_LOCAL_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    DateTimeFormatter STD_LOCAL_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");


}
