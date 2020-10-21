package com.ihomefnt.o2o.common.aop;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;

/**
 * 自定义异常处理
 *
 * @author liyonggang
 * @create 2018-12-20 17:48
 */
public class CustomerExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerExceptionHandler.class);

    private static final String httpBaseResponseSimpleClassName = HttpBaseResponse.class.getSimpleName();

    private static final String responseEntitySimpleClassName = ResponseEntity.class.getSimpleName();


    public static Object handlerCustomerExcelption(Exception e, Method method) {
        if (e instanceof BusinessException) {
            BusinessException exception = (BusinessException) e;
            Object obj = handlerResult(exception.getCode(), exception.getMessage(), method);
            LOGGER.error("---handlerCustomerExcelption.BusinessException Handler---### {} ### {}", method.getName(), e);
            return obj;
        } else if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause() instanceof BusinessException) {
            BusinessException exception = (BusinessException) e.getCause().getCause();
            Object obj = handlerResult(exception.getCode(), exception.getMessage(), method);
            LOGGER.error("---post.handlerCustomerExcelption.BusinessException Handler---### {} ### {}", method.getName(), e);
            return obj;
        } else {
            LOGGER.error("---handlerCustomerExcelption.Exception Handler---### {} ### {}", method.getName(), e);
            return handlerResult(HttpResponseCode.FAILED, "服务器内部异常", method);
        }
    }

    private static Object handlerResult(Long code, String message, Method method) {
        Class<?> returnType = method.getReturnType();
        String simpleName = returnType.getSimpleName();
        Object result = null;
        HttpBaseResponse baseResponse = HttpBaseResponse.fail(code, message);
        if (simpleName.equalsIgnoreCase(httpBaseResponseSimpleClassName)) {
            result = baseResponse;
        } else if (simpleName.equalsIgnoreCase(responseEntitySimpleClassName)) {
            result = new ResponseEntity(baseResponse, HttpStatus.OK);
        }
        return result;
    }
}
