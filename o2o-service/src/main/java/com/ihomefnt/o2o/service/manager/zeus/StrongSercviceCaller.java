package com.ihomefnt.o2o.service.manager.zeus;

import com.alibaba.fastjson.JSON;
import com.ihomefnt.common.api.ApiUtil;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.zeus.excption.ServiceCallException;
import com.ihomefnt.zeus.excption.ServiceNotFoundException;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 宙斯调用封装日志
 *
 * @author liyonggang
 * @create 2018-12-29 15:46
 */
@SuppressWarnings("all")
@Repository
public class StrongSercviceCaller {

    @Resource
    private ServiceCaller serviceCaller;

    public static final Logger LOGGER = LoggerFactory.getLogger(StrongSercviceCaller.class);

    public static final Integer SERVICE_RESPONSE_LOG_SIZE = 250;

    private static ObjectMapper objectMapper = new ObjectMapper();


    public <T> T get(String serviceName, String url, Object param, Class<T> clazz) throws ServiceNotFoundException, ServiceCallException {
        LOGGER.info("service: {}, params {}", serviceName, JSON.toJSONString(param));
        long start = System.currentTimeMillis();
        T result = null;
        try {
            result = serviceCaller.get(serviceName, url, param, clazz);
        } catch (Exception e) {
            LOGGER.error("service: {} error , message :", serviceName, e);
            throw new BusinessException(HttpResponseCode.SERVICE_EXCEPTION, MessageConstant.FAILED);
        }
        LOGGER.info("service: {} times {}ms, response:{}", serviceName, System.currentTimeMillis() - start, StringUtils.substring(JSON.toJSONString(result), 0, SERVICE_RESPONSE_LOG_SIZE));
        if (result == null) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_NULL, MessageConstant.FAILED);
        }
        return result;
    }

    public <T> T get(String serviceName, Object param, Class<T> clazz) throws ServiceNotFoundException, ServiceCallException {
        LOGGER.info("service: {}, params {}", serviceName, JSON.toJSONString(param));
        long start = System.currentTimeMillis();
        T result = null;
        try {
            result = serviceCaller.get(serviceName, param, clazz);
        } catch (Exception e) {
            LOGGER.error("service: {} error , message :", serviceName, e);
            throw new BusinessException(HttpResponseCode.SERVICE_EXCEPTION, MessageConstant.FAILED);
        }
        LOGGER.info("service: {} times {}ms, response:{}", serviceName, System.currentTimeMillis() - start, StringUtils.substring(JSON.toJSONString(result), 0, SERVICE_RESPONSE_LOG_SIZE));
        if (result == null) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_NULL, MessageConstant.FAILED);
        }
        return result;
    }

    public <T> T get(String serviceName, Map<String, String> extraHeaders, Object param, Class<T> clazz) throws ServiceNotFoundException, ServiceCallException {
        LOGGER.info("service: {}, params {}", serviceName, JSON.toJSONString(param));
        long start = System.currentTimeMillis();
        T result = null;
        try {
            result = serviceCaller.get(serviceName, extraHeaders, param, clazz);
        } catch (Exception e) {
            LOGGER.error("service: {} error , message :", serviceName, e);
            throw new BusinessException(HttpResponseCode.SERVICE_EXCEPTION, MessageConstant.FAILED);
        }
        LOGGER.info("service: {} times {}ms, response:{}", serviceName, System.currentTimeMillis() - start, StringUtils.substring(JSON.toJSONString(result), 0, SERVICE_RESPONSE_LOG_SIZE));
        if (result == null) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_NULL, MessageConstant.FAILED);
        }
        return result;
    }

    public <T> T post(String serviceName, String url, Object param, Class<T> clazz) throws ServiceNotFoundException, ServiceCallException {
        LOGGER.info("service: {}, params {}", serviceName, JSON.toJSONString(param));
        long start = System.currentTimeMillis();
        T result = null;
        try {
            result = serviceCaller.post(serviceName, url, param, clazz);
        } catch (Exception e) {
            LOGGER.error("service: {} error , message :", serviceName, e);
            throw new BusinessException(HttpResponseCode.SERVICE_EXCEPTION, MessageConstant.FAILED);
        }
        LOGGER.info("service: {} times {}ms, response:{}", serviceName, System.currentTimeMillis() - start, StringUtils.substring(JSON.toJSONString(result), 0, SERVICE_RESPONSE_LOG_SIZE));
        if (result == null) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_NULL, MessageConstant.FAILED);
        }
        return result;
    }

    public <T> T post(String serviceName, Map<String, String> extraHeaders, Object param, Class<T> clazz) throws ServiceNotFoundException, ServiceCallException {
        LOGGER.info("service: {}, params {}", serviceName, JSON.toJSONString(param));
        long start = System.currentTimeMillis();
        T result = null;
        try {
            result = serviceCaller.post(serviceName, extraHeaders, param, clazz);
        } catch (Exception e) {
            LOGGER.error("service: {} error , message :", serviceName, e);
            throw new BusinessException(HttpResponseCode.SERVICE_EXCEPTION, MessageConstant.FAILED);
        }
        LOGGER.info("service: {} times {}ms, response:{}", serviceName, System.currentTimeMillis() - start, StringUtils.substring(JSON.toJSONString(result), 0, SERVICE_RESPONSE_LOG_SIZE));
        if (result == null) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_NULL, MessageConstant.FAILED);
        }
        return result;
    }

    public <T> T post(String serviceName, Object param, Class<T> clazz) throws ServiceNotFoundException, ServiceCallException {
        LOGGER.info("service: {}, params {}", serviceName, JSON.toJSONString(param));
        long start = System.currentTimeMillis();
        T result = null;
        try {
            result = serviceCaller.post(serviceName, param, clazz);
        } catch (Exception e) {
            LOGGER.error("service: {} error , message :", serviceName, e);
            throw new BusinessException(HttpResponseCode.SERVICE_EXCEPTION, MessageConstant.FAILED);
        }
        LOGGER.info("service: {} times {}ms, response:{}", serviceName, System.currentTimeMillis() - start, StringUtils.substring(JSON.toJSONString(result), 0, SERVICE_RESPONSE_LOG_SIZE));
        if (result == null) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_NULL, MessageConstant.FAILED);
        }
        return result;
    }

    public <T> T post(String serviceName, Object param, TypeReference typeReference) throws ServiceNotFoundException, ServiceCallException {
        LOGGER.info("service: {}, params {}", serviceName, JSON.toJSONString(param));
        long start = System.currentTimeMillis();
        T result = null;
        try {
            result = serviceCaller.post(serviceName, param, typeReference);
        } catch (Exception e) {
            LOGGER.error("service: {} error , message :", serviceName, e);
            throw new BusinessException(HttpResponseCode.SERVICE_EXCEPTION, MessageConstant.FAILED);
        }
        LOGGER.info("service: {} times {}ms, response:{}", serviceName, System.currentTimeMillis() - start, StringUtils.substring(JSON.toJSONString(result), 0, SERVICE_RESPONSE_LOG_SIZE));
        if (result == null) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_NULL, MessageConstant.FAILED);
        }
        return result;
    }

    public <T> T post(String serviceName, Map<String, String> extraHeaders, Object param, TypeReference typeReference) throws ServiceNotFoundException, ServiceCallException {
        LOGGER.info("service: {}, params {}", serviceName, JSON.toJSONString(param));
        long start = System.currentTimeMillis();
        T result = null;
        try {
            result = serviceCaller.post(serviceName, extraHeaders, param, typeReference);
        } catch (Exception e) {
            LOGGER.error("service: {} error , message :", serviceName, e);
            throw new BusinessException(HttpResponseCode.SERVICE_EXCEPTION, MessageConstant.FAILED);
        }
        LOGGER.info("service: {} times {}ms, response:{}", serviceName, System.currentTimeMillis() - start, StringUtils.substring(JSON.toJSONString(result), 0, SERVICE_RESPONSE_LOG_SIZE));
        if (result == null) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_NULL, MessageConstant.FAILED);
        }
        return result;

    }

    public <T> T post(String serviceName, String url, Object param, TypeReference typeReference) throws ServiceNotFoundException, ServiceCallException {
        LOGGER.info("service: {}, params {}", serviceName, JSON.toJSONString(param));
        long start = System.currentTimeMillis();
        T result;
        try {
            Object post = serviceCaller.post(serviceName, url, param, Object.class);
            objectMapper.readValue(JsonUtils.obj2json(param), typeReference);
            result = (T) ApiUtil.mapper(post, typeReference);
        } catch (Exception e) {
            LOGGER.error("service: {} error , message :", serviceName, e);
            throw new BusinessException(HttpResponseCode.SERVICE_EXCEPTION, MessageConstant.FAILED);
        }
        LOGGER.info("service: {} times {}ms, response:{}", serviceName, System.currentTimeMillis() - start, StringUtils.substring(JSON.toJSONString(result), 0, SERVICE_RESPONSE_LOG_SIZE));
        if (result == null) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_NULL, MessageConstant.FAILED);
        }
        return result;

    }
}
