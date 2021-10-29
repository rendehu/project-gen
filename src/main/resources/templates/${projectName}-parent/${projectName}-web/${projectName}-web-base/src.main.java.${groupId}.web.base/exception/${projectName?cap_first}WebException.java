package ${groupId}.web.base.exception;

import ${groupId}.util.exception.Base${projectName?cap_first}Exception;

/**
 * @author ${author}
 * @since ${currentDate}
 */
public class ${projectName?cap_first}WebException extends Base${projectName?cap_first}Exception {


    public ${projectName?cap_first}WebException(String message, Object... args) {
        super(message, args);
    }

}
