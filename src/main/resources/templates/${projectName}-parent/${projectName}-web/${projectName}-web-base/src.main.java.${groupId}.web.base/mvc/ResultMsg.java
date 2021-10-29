package ${groupId}.web.base.mvc;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ${author}
 * @since ${currentDate}
 */
@Data
@Accessors(chain = true)
public class ResultMsg<T> {

    /**
     * Success /failed
     */
    private Boolean ret;

    /**
     * Message
     */
    private String message;

    /**
     * Business data
     */
    private T data;
}
