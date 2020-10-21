/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月22日
 * Description:SmsProxy.java
 */
package com.ihomefnt.o2o.service.proxy.sms;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.sms.SmsProxy;
import com.ihomefnt.o2o.intf.domain.sms.dto.CheckSmsCodeParamVo;
import com.ihomefnt.o2o.intf.domain.sms.dto.SendSmsCodeParamVo;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhang
 */
@Service
public class SmsProxyImpl implements SmsProxy {

    @Resource
    private ServiceCaller serviceCaller;

    /**
     * 增加日志:主要为了方便定位
     */
    private static final Logger LOG = LoggerFactory.getLogger(SmsProxyImpl.class);

    @SuppressWarnings("rawtypes")
    @Override
    public boolean checkSmsCode(CheckSmsCodeParamVo params) {
        if (params == null || StringUtils.isBlank(params.getMobile()) || StringUtils.isBlank(params.getSmsCode())) {
            LOG.info("user-web.verification.checkSmsCode id is null");
            return false;
        }

        LOG.info("user-web.verification.checkSmsCode params:{}", JsonUtils.obj2json(params));
        ResponseVo responseVo = null;
        try {
            responseVo = serviceCaller.post("user-web.verification.checkSmsCode", params, ResponseVo.class);
        } catch (Exception e) {
            LOG.error("user-web.verification.checkSmsCode error:{}", e.getMessage());
            return false;
        }

        if (responseVo == null) {
            return false;
        } else {
            LOG.info("user-web.verification.checkSmsCode result :{}", JsonUtils.obj2json(responseVo));
            return responseVo.isSuccess();
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int sendSmsCode(SendSmsCodeParamVo params) {
        if (params == null || StringUtils.isBlank(params.getMobile())) {
            LOG.info("user-web.verification.sendSmsCode id is null");
            throw new BusinessException(HttpResponseCode.SMS_SEND_MOBILE_FAILED, "请输入正确的手机号码");
        }
        LOG.info("user-web.verification.sendSmsCode params:{}", JsonUtils.obj2json(params));
        ResponseVo responseVo;
        try {
            responseVo = serviceCaller.post("user-web.verification.sendSmsCode", params, ResponseVo.class);
        } catch (Exception e) {
            LOG.error("user-web.verification.sendSmsCode error:{}", e.getMessage());
            throw new BusinessException("发送失败");
        }
        if (responseVo == null) {
            throw new BusinessException("发送失败");
        } else {
            LOG.info("user-web.verification.sendSmsCode result :{}", JsonUtils.obj2json(responseVo));
            if (responseVo.getCode() == null) {
                throw new BusinessException("发送失败");
            }
            return responseVo.getCode();
        }
    }
}
