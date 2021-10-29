package ${groupId}.web.base.mvc;

import ${groupId}.util.exception.Base${projectName?cap_first}Exception;
import ${groupId}.web.base.exception.${projectName?cap_first}AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Response advice operation for RestController
 *
 * @author ${author}
 * @since ${currentDate}
 */
@Slf4j
@RestControllerAdvice(basePackages = "${groupId}.web.portal.controller")
public class BaseResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        Class<?> transferClass = methodParameter.getExecutable().getDeclaringClass();
        RestController restController = transferClass.getAnnotation(RestController.class);
        return methodParameter.hasMethodAnnotation(ResponseBody.class) || restController != null;
    }

    /**
     * @param respObj            response data obj
     * @param methodParameter    serverHttpRequest
     * @param mediaType          mediaType
     * @param aClass             aClass
     * @param serverHttpRequest  serverHttpRequest
     * @param serverHttpResponse serverHttpResponse
     * @return packaged response body
     */
    @Override
    public Object beforeBodyWrite(Object respObj, MethodParameter methodParameter, MediaType mediaType,
                                  Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (respObj instanceof ResultMsg) {
            return respObj;
        }
        return new ResultMsg<>().setRet(true).setData(respObj);
    }

    /**
     * business exception
     *
     * @param baseMossException mossException
     * @return ResultMsg<Object>
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({Base${projectName?cap_first}Exception.class})
    public ResultMsg<Object> handleMossException(Base${projectName?cap_first}Exception baseMossException) {
        log.error("Moss exception", baseMossException);
        return getErrorResultMsg(baseMossException.getMessage());
    }

    /**
     * auth exception
     *
     * @param exception exception
     * @return ResultMsg
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({${projectName?cap_first}AuthenticationException.class})
    public ResultMsg<Object> handleException(${projectName?cap_first}AuthenticationException exception) {
        return getErrorResultMsg(exception.getMessage());
    }


    /**
     * Validation exception
     *
     * @param exception exception
     * @return ResultMsg<Object>
     */
    @ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultMsg<Object> handleException(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        return getErrorResultMsg(message);
    }


    /**
     * Valid exception
     *
     * @param exception exception
     * @return ResultMsg<Object>
     */

    @ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
    @ExceptionHandler(BindException.class)
    public ResultMsg<Object> handleException(BindException exception) {
        String message = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        message = message == null ? "" : message;
        return getErrorResultMsg(message);
    }


    /**
     * Upload file exception
     *
     * @return ResultMsg<Object>
     */
    @ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResultMsg<Object> handleException() {
        return getErrorResultMsg("上传文件过大");
    }


    /**
     * Service exception
     *
     * @param exception exception
     * @return ResultMsg<Object>
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResultMsg<Object> handleException(Exception exception) {
        log.error("Exception happen", exception);
        return getErrorResultMsg("System Internal Error");
    }


    private ResultMsg<Object> getErrorResultMsg(String message) {
        return new ResultMsg<>().setRet(false).setMessage(message);
    }
}
