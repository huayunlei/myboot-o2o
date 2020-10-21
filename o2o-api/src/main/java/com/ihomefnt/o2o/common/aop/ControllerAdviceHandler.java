package com.ihomefnt.o2o.common.aop;

import com.ihomefnt.o2o.common.util.ApiResult;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *  统一业务异常捕捉 aop
 * Created by jiangjun
 */
@ControllerAdvice
public class ControllerAdviceHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdviceHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<HttpBaseResponse> errorHandler(HttpServletRequest req, Exception e) {
        if (e instanceof BusinessException) {
//            logger.error("---BusinessException Handler---", e);
            return new ApiResult().fail(((BusinessException) e).getCode(), e.getMessage());
        } else {
            logger.error("---errorHandler bad request--- {}", e.getMessage());
            return new ApiResult().fail(HttpResponseCode.WRONG_REQUEST,MessageConstant.WRONG_REQUEST);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<HttpBaseResponse>  processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorMessages = new ArrayList<>();
        fieldErrors.stream().forEach( fieldError -> errorMessages.add(fieldError.getDefaultMessage()));
        return new ApiResult().fail(errorMessages);
    }
}
