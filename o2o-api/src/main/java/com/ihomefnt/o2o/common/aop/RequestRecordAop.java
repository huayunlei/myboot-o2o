package com.ihomefnt.o2o.common.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.common.CommonResponseVo;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.service.common.AiDingTalk;
import com.ihomefnt.semporna.context.IhomeContextHandler;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.testng.collections.Lists;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author onefish
 * @date 2018/9/28 0028.
 */
@Configuration
@Aspect
public class RequestRecordAop {

    @Autowired
    private AiDingTalk aiDingTalk;

    private static Logger requestLogger = LoggerFactory.getLogger("request");
    private static Logger logger = LoggerFactory.getLogger(RequestRecordAop.class);


    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String httpBaseResponseSimpleClassName = HttpBaseResponse.class.getSimpleName();

    private static final String responseEntitySimpleClassName = ResponseEntity.class.getSimpleName();

    private static final String httpBasePageResponseSimpleClassName = HttpBasePageResponse.class.getSimpleName();

    private static final String responseVoSimpleClassName = ResponseVo.class.getSimpleName();

    private static final String commonResponseVoClassName = CommonResponseVo.class.getSimpleName();


    /**
     * controller的切面
     */
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerPointcut() {
    }

    /**
     * RestController的切面
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerPointcut() {
    }

    @Around("(controllerPointcut()|| restControllerPointcut())")
    public Object recordRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final String className = signature.getDeclaringTypeName();
        if (className.contains("swagger")) {
            return joinPoint.proceed();
        }
        //方法名称
        final String methodName = signature.getMethod().getName();
        Object[] args = joinPoint.getArgs();
        JSONObject json = new JSONObject();
        json.put("className", className);
        json.put("methodName", methodName);
        json.put("traceId", IhomeContextHandler.getIhomeContext().getTraceId());

        json.put("payLoad", Lists.newArrayList(args).stream()
                .filter(arg -> !(arg instanceof HttpServletRequest))
                .collect(Collectors.toList()));
        json.put("timestamp", System.currentTimeMillis());
        Object result = null;
        Exception e = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception ex) {
            e = ex;
        } finally {
            try {
                if (null != e) {
                    json.put("exception", e.getMessage() == null ? e : e.getMessage());
                } else {
                    json.put("response", result);
                }
                requestLogger.info(json.toJSONString());

                /**
                 * 发送钉钉告警
                 */
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                String paramsJson = preHandle(args);
                asynProcessDingTalkWarn(request.getRequestURI(), paramsJson, result, signature, e);

                if (e != null) {
                    result = CustomerExceptionHandler.handlerCustomerExcelption(e, signature.getMethod());
                }
                String simpleClassName = className.substring(className.lastIndexOf(".") + 1);
                if (!"CheckController".equals(simpleClassName)) {
                    logger.info(simpleClassName + "." + methodName + " params:{}, result:{}", paramsJson, postHandle(result));
                }
            } catch (Exception ignore) {
            }
        }

        return result;
    }

    private void asynProcessDingTalkWarn(String url, String paramsJson, Object result, MethodSignature signature, Exception e) {
        // ———————返回为空或者code不为1，则发送告警——start———————
        List<TaskAction<?>> taskActions = new ArrayList<>();
        final Object finalResult = result;
        taskActions.add(() -> {
            Long resultCode = 0L;
            String resultMsg = "";
            if (null != finalResult) {
                Class<?> returnType = signature.getMethod().getReturnType();
                String simpleName = returnType.getSimpleName();

                HttpBaseResponse httpBaseResponseTmp = null;
                HttpBasePageResponse httpBasePageResponseTmp = null;
                ResponseVo responseVo = null;
                CommonResponseVo commonResponseVo = null;
                if (simpleName.equalsIgnoreCase(httpBaseResponseSimpleClassName)) {
                    httpBaseResponseTmp = (HttpBaseResponse) finalResult;
                } else if (simpleName.equalsIgnoreCase(responseEntitySimpleClassName)) {
                    httpBaseResponseTmp = (HttpBaseResponse) ((ResponseEntity) finalResult).getBody();
                } else if (simpleName.equalsIgnoreCase(httpBasePageResponseSimpleClassName)) {
                    httpBasePageResponseTmp = (HttpBasePageResponse) finalResult;
                }else if(simpleName.equalsIgnoreCase(responseVoSimpleClassName)){
                    responseVo = (ResponseVo) finalResult;
                }else if(simpleName.equalsIgnoreCase(commonResponseVoClassName)){
                    commonResponseVo = (CommonResponseVo) finalResult;
                }

                if (null != httpBaseResponseTmp) {
                    resultCode = httpBaseResponseTmp.getCode();
                    Object extTmp = httpBaseResponseTmp.getExt();
                    if (null != extTmp) {
                        resultMsg = ((HttpMessage) extTmp).getMsg();
                    }
                }
                if (null != httpBasePageResponseTmp) {
                    resultCode = httpBasePageResponseTmp.getCode();
                    resultMsg = httpBasePageResponseTmp.getExt();
                }
                if (null != responseVo) {
                    resultCode = responseVo.getCode();
                    resultMsg = responseVo.getMsg();
                }
                if(null != commonResponseVo){
                    resultCode = commonResponseVo.getCode();
                    resultMsg = commonResponseVo.getMsg();
                }
            } else {
                resultCode = getCode(e);
            }

            final String methodName = signature.getMethod().getName();
            final String className = signature.getDeclaringTypeName();
            if ((resultCode == null || 1L != resultCode)) {
                // 钉钉告警白名单
                if (resultCode <= HttpReturnCode.DING_MONITOR_WHITE_END && resultCode >= HttpReturnCode.DING_MONITOR_WHITE_START) {
                    return 1;
                }
                String[] classStringList = className.split("\\.");
                String classMethodName = classStringList[classStringList.length - 1] + "." + methodName;
                String errorMsg = "---";
                if (e != null) {
                    errorMsg = e.getMessage() == null ? String.valueOf(e) : e.getMessage();
                }
                if (resultMsg != null) {
                    errorMsg = resultMsg + "#" + errorMsg;
                }

                // 发送告警
                aiDingTalk.sendDingTalkWarn(classMethodName, url, paramsJson,
                        resultCode, errorMsg);
            }
            // ———————返回为空或者code不为1，则发送告警——end———————

            return 1;
        });
        // 执行任务
        Executor.getInvokeOuterServiceFactory().asyncExecuteTask(taskActions);
    }

    /**
     * 入参数据
     *
     * @param paramsArray
     * @return
     */
    private String preHandle(Object[] paramsArray) {
        StringBuffer params = new StringBuffer();
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (paramsArray[i] instanceof Serializable) {
                    params.append(paramsArray[i].toString()).append(",");
                } else {
                    try {
                        String param = objectMapper.writeValueAsString(paramsArray[i]);
                        if (StringUtils.isNotBlank(param))
                            params.append(param).append(",");
                    } catch (JsonProcessingException e) {
                    }
                }
            }
        } else {
            return null;
        }
        String s = params.toString();
        return s.substring(0, s.length() - 1);
    }

    /**
     * 获取返回码
     *
     * @param e
     * @return
     */
    private Long getCode(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException exception = (BusinessException) e;
            return exception.getCode();
        } else {
            return HttpResponseCode.FAILED;
        }
    }


    /**
     * 返回数据
     *
     * @param retVal
     * @return
     */
    private String postHandle(Object retVal) {
        if (null == retVal) {
            return "";
        }
        return JSON.toJSONString(retVal);
    }
}
