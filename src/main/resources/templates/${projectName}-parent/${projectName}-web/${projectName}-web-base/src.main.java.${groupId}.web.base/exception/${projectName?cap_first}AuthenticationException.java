package ${groupId}.web.base.exception;

import ${groupId}.util.exception.Base${projectName?cap_first}Exception;

/**
 * AuthenticationException
 * @author ${author}
 * @since ${currentDate}
 */
public class ${projectName?cap_first}AuthenticationException extends Base${projectName?cap_first}Exception {


    public ${projectName?cap_first}AuthenticationException(String message, Object... args) {
        super(message, args);
    }

    public ${projectName?cap_first}AuthenticationException(Throwable cause, String message, String... args) {
        super(cause, message, args);
    }
}
