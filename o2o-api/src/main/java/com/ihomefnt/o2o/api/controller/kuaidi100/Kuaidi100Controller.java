/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月28日
 * Description:Kuaidi100Controller.java
 */
package com.ihomefnt.o2o.api.controller.kuaidi100;

import com.ihomefnt.common.util.validation.ValidationResult;
import com.ihomefnt.common.util.validation.ValidationUtils;
import com.ihomefnt.o2o.intf.domain.kuaidi100.vo.request.Kuaidi100HttpRequestVo;
import com.ihomefnt.o2o.intf.domain.kuaidi100.vo.request.QueryKuaidi100RequestVo;
import com.ihomefnt.o2o.intf.domain.kuaidi100.vo.response.KuaidiProductInfoResponseVo;
import com.ihomefnt.o2o.intf.service.kuaidi100.Kuaidi100Service;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author zhang
 */
@RestController
@Api(tags = "【快递API】")
@RequestMapping("/kuaidi100")
public class Kuaidi100Controller {

    @Autowired
    Kuaidi100Service service;

    @ApiOperation(value = "查看快递详情", notes = "查看快递详情是否正常")
    @RequestMapping(value = "/viewKuadi100", method = RequestMethod.POST)
    public HttpBaseResponse<KuaidiProductInfoResponseVo> viewKuadi100(@RequestBody Kuaidi100HttpRequestVo request) {
        /**
         * 验证前台传入参数是否合法
         */
        String msg = checkCommonFailed(request);
        if (null != msg) {
			return HttpBaseResponse.fail(msg);
        }
        String logisticCompanyCode = request.getLogisticCompanyCode();
        String logisticNum = request.getLogisticNum();
        KuaidiProductInfoResponseVo response = service.getKuaidi100(request.getPhone(), logisticCompanyCode, logisticNum);
        if (response == null) {
			return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
		return HttpBaseResponse.success(response);
    }

    /**
     * 根据订单号查询快递详情
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据订单号查询快递详情", notes = "根据订单号查询快递详情，orderId和orderNum二选一")
    @RequestMapping(value = "/viewKuadi100ByOrder", method = RequestMethod.POST)
    public HttpBaseResponse<KuaidiProductInfoResponseVo> viewKuadi100(@RequestBody @Valid QueryKuaidi100RequestVo request) {
        if (request.getOrderId() == null && StringUtils.isBlank(request.getOrderNum())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(service.getKuaidiInfo(request.getOrderNum(), request.getOrderId(), request.getLogisticCompanyCode(), request.getLogisticNum()));
    }

    /**
     * 验证前台传入参数
     *
     * @param request
     * @return
     */
    private String checkCommonFailed(Object request) {
        ValidationResult result = ValidationUtils.validateEntity(request);
        if (result.isHasErrors()) {
            Map<String, String> map = result.getErrorMsg();
            StringBuffer buff = new StringBuffer();
            int index = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                index++;
                buff.append(entry.getValue());
                if (index < map.size()) {
                    buff.append(",");
                }
            }
            return buff.toString();
        }
        return null;
    }


}
