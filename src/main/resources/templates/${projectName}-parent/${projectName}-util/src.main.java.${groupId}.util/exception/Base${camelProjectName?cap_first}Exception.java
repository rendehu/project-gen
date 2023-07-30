package ${groupId}.util.exception;

import cn.hutool.core.util.StrUtil;

/**
 * Abstract Base${projectName?cap_first}Exception
 *
 * @author ${author}
 * @since ${currentDate}
 */
public abstract class Base${camelProjectName?cap_first}Exception extends RuntimeException {

    public Base${camelProjectName?cap_first}Exception(String message, Object... args) {
        super(formatMessage(message, args));
    }

    public Base${camelProjectName?cap_first}Exception(Throwable cause, String message, String... args) {
        super(formatMessage(message, args), cause);
    }

    /**
     * Format message
     *
     * @param message message  Like slf4j log formatter log.info("hello,{}",world);
     * @param args    args
     * @return formatted message
     */
    protected static String formatMessage(String message, Object[] args) {
        if (null != args) {
            return StrUtil.format(message, args);
        }
        return message;
    }
}
