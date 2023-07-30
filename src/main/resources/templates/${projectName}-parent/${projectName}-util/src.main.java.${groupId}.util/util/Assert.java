package ${groupId}.util.util;

import cn.hutool.core.util.StrUtil;
import ${groupId}.util.exception.Assert${camelProjectName?cap_first}Exception;
import ${groupId}.util.exception.Base${camelProjectName?cap_first}Exception;

import java.util.Collection;
import java.util.Map;

/**
 * 业务判断
 *
 * @author ${author}
 * @since ${currentDate}
 */
public class Assert {


    /**
     * Not blank for check character
     *
     * @param text text
     */
    public static void hasText(String text) {
        hasText(text, "Value:{} assert hasText but failed", text);
    }

    /**
     * Not blank for check character
     *
     * @param text    text
     * @param message message
     * @param args    args
     */
    public static void hasText(String text, String message, Object... args) {
        if (StrUtil.isEmpty(text)) {
            throw new Assert${projectName?cap_first}Exception(message, args);
        }
    }


    /**
     * Is null for check object
     *
     * @param obj obj
     */
    public static void isNull(Object obj) {
        isNull(obj, "Value:{} assert isNull but failed", obj);
    }

    /**
     * Is null for check object
     *
     * @param obj obj
     */
    public static void isNull(Object obj, String message, Object... args) {
        if (obj != null) {
            throw new Assert${projectName?cap_first}Exception(message, args);
        }
    }

    /**
     * Not null for check object
     *
     * @param obj obj
     */
    public static void notNull(Object obj) {
        notNull(obj, "Value:{} assert notNull but failed", obj);
    }

    public static void notNull(Object obj, String message, Object... args) {
        if (obj == null) {
            throw new Assert${projectName?cap_first}Exception(message, args);
        }
    }

    /**
     * Not empty for check collection
     *
     * @param collection collection
     * @param <T>        Any object
     */
    public static <T> void isEmpty(Collection<T> collection) {
        isEmpty(collection, "Value:{} assert isEmpty but failed", collection);
    }


    /**
     * Not empty for check collection
     *
     * @param collection collection
     * @param message    message
     * @param args       args
     * @param <T>        t
     */
    public static <T> void isEmpty(Collection<T> collection, String message, Object... args) {
        if (collection != null && !collection.isEmpty()) {
            throw new Assert${projectName?cap_first}Exception(message, args);
        }
    }


    /**
     * Not empty for check collection
     *
     * @param collection collection
     * @param <T>        Any object
     */
    public static <T> void notEmpty(Collection<T> collection) {
        notEmpty(collection, "Value:{} assert notEmpty but failed", collection);
    }


    /**
     * Not empty for check collection
     *
     * @param array collection
     * @param <T>   Any object
     */
    public static <T> void notEmpty(T[] array) {
        notEmpty(array, "Array assert notEmpty but failed");
    }

    /**
     * Not empty for check collection
     *
     * @param array   collection
     * @param message message
     * @param args    args
     * @param <T>     Any object
     */
    public static <T> void notEmpty(T[] array, String message, Object... args) {
        if (array == null || array.length == 0) {
            throw new Assert${projectName?cap_first}Exception(message, args);
        }
    }

    /**
     * Not empty for check collection
     *
     * @param collection collection
     * @param message    message
     * @param args       args
     * @param <T>        t
     */
    public static <T> void notEmpty(Collection<T> collection, String message, Object... args) {
        if (collection == null || collection.isEmpty()) {
            throw new Assert${projectName?cap_first}Exception(message, args);
        }
    }

    public static <K, T> void notEmpty(Map<K, T> map, String message, Object... args) {
        if (map == null || map.isEmpty()) {
            throw new Assert${projectName?cap_first}Exception(message, args);
        }
    }

    /**
     * Is true for check expression
     *
     * @param expression expression
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "Express assert true but false");
    }


    public static void isTrue(boolean expression, Base${projectName?cap_first}Exception base${projectName?cap_first}Exception) {
        if (!expression) {
            throw base${projectName?cap_first}Exception;
        }
    }

    /**
     * Is true for check expression
     *
     * @param expression expression
     * @param message    message
     * @param args       args
     */
    public static void isTrue(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new Assert${projectName?cap_first}Exception(message, args);
        }
    }

    /**
     * Is false for check expression
     *
     * @param expression expression
     */
    public static void isFalse(boolean expression) {
        isFalse(expression, "Express assert false but true");

    }

    /**
     * Is false for check expression
     *
     * @param expression expression
     * @param message    message
     * @param args       args
     */
    public static void isFalse(boolean expression, String message, Object... args) {
        if (expression) {
            throw new Assert${projectName?cap_first}Exception(message, args);
        }
    }
}
