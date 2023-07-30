package ${groupId}.util.exception;

/**
 * @author ${author}
 * @since ${currentDate}
 */
public class Assert${camelProjectName?cap_first}Exception extends Base${camelProjectName?cap_first}Exception {

    public Assert${projectName?cap_first}Exception(String message, Object... args) {
        super(message, args);
    }

    public Assert${projectName?cap_first}Exception(Throwable cause, String message, String... args) {
        super(cause, message, args);
    }
}
