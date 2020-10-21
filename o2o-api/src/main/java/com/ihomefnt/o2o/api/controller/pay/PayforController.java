/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月8日
 * Description:LianlianController.java 
 */
package com.ihomefnt.o2o.api.controller.pay;

import com.alibaba.fastjson.JSON;
import com.ihomefnt.o2o.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.pay.vo.request.BankCardRequestVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.request.PayRequestVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.request.PayforRequestVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.response.BankCardReponseVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.response.BankListResponseVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.response.IdCardResponseVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.response.PayResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.pay.BankErrorCode;
import com.ihomefnt.o2o.intf.manager.constant.pay.PayforConstant;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.service.pay.PayforService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhang
 * @see 192.168.1.31:10038/fgw-web/swagger/
 */
@Api(tags = "【支付API】")
@RestController
public class PayforController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PayforController.class);

	@Autowired
	private PayforService payforService;
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "查询用户已绑定的银行列表")
	@RequestMapping(value = "/lianlian/getBankListByUserId", method = RequestMethod.POST)
	public HttpBaseResponse<BankListResponseVo> getBankListByUserId(@RequestBody HttpBaseRequest request) {
		if (request == null || StringUtils.isBlank(request.getAccessToken())) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
		}

		// 版本控制 appVersion>3.1.9的版本才展示
		String appVersion = "";
		if (null == request || null == request.getOsType() || request.getOsType() == 3) {
			appVersion = "3.1.9";
		} else {
			if (request.getOsType() != 3) {
				appVersion = request.getAppVersion();
			}
		}
		if (!VersionUtil.mustUpdate("3.1.9", appVersion)) {
			return HttpBaseResponse.fail(PayforConstant.VERSION_ERROR);
		}

		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}
		return HttpBaseResponse.success(payforService.getBankListByUserId(userDto.getId()));
	}

	@ApiOperation(value = "查询用户的连连支付绑定身份证信息")
	@RequestMapping(value = "/lianlian/getIdCardByUserId", method = RequestMethod.POST)
	public HttpBaseResponse<IdCardResponseVo> getIdCardByUserId(@RequestBody HttpBaseRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
		}

		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}
		return HttpBaseResponse.success(payforService.getIdCardByUserId(userDto.getId()));
	}

	@ApiOperation(value = "查询连连支付支持的银行列表")
	@RequestMapping(value = "/lianlian/getBankListByLianlian", method = RequestMethod.POST)
	public HttpBaseResponse<BankListResponseVo> getBankListByLianlian(@RequestBody HttpBaseRequest request) {
		BankListResponseVo obj = payforService.getBankListByLianlian(request);
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value = "根据卡号查询银行卡信息")
	@RequestMapping(value = "/lianlian/getCardInfo", method = RequestMethod.POST)
	public HttpBaseResponse<BankCardReponseVo> getCardInfo(@RequestBody BankCardRequestVo request) {
		if (request == null || StringUtils.isBlank(request.getCardNo())) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "请输入本人银行卡号，暂不支持信用卡");
		}
		
		return HttpBaseResponse.success(payforService.getCardInfo(request));
	}

	@ApiOperation(value = "将支付信息提交给连连支付平台")
	@RequestMapping(value = "/lianlian/getOrderInfoForLianlian", method = RequestMethod.POST)
	public HttpBaseResponse<PayResponseVo> getOrderInfoForLianlian(@RequestBody PayRequestVo request) {
		if (request == null || request.getSelectSum() == null || request.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
		}
		PayResponseVo obj = payforService.handlerOldLianlianSignAndRecord(request);
		if (obj == null) {
			return HttpBaseResponse.fail(BankErrorCode.NOT_VALID.getCode(), BankErrorCode.NOT_VALID.getMsg());
		}
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value = "银行卡解绑")
	@RequestMapping(value = "/lianlian/unbindBankInfo", method = RequestMethod.POST)
	public HttpBaseResponse<Void> unbindBankInfo(@RequestBody BankCardRequestVo request) {
		if (request == null || StringUtils.isBlank(request.getCardNo())) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
		}
		boolean result = payforService.unbindBankInfo(request);
		if (!result) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.FAILED);
		}
		return HttpBaseResponse.success(null);
	}
	
	/**
     * 验证请求参数
     * @param req 请求参数
     * @throws RuntimeException 参数为空时候抛出异常
     */
    private void validRequestParam(PayforRequestVo req) throws RuntimeException{
        if(null == req){
            throw new RuntimeException("pay.common param is null . ");
        }
        if(null == req.getAccessToken() || "".equals(req.getAccessToken())){
            throw new RuntimeException("pay.common accessToken is null . ");
        }
        if(null == req.getOrderId() || "".equals(req.getOrderId())){
            throw new RuntimeException("pay.common param orderId is null . ");
        }
        if(null == req.getChannelSource() || "".equals(req.getChannelSource())){
            throw new RuntimeException("pay.common param channelSource is null . ");
        }
        if(null == req.getActualPayMent() || "".equals(req.getActualPayMent())){
            throw new RuntimeException("pay.common param actualPayMent is null . ");
        }

        // 当支付方式为练练，账户名，卡号和身份证必须传
        if(PayforConstant.TYPE_PAY_LIANLIAN==req.getChannelSource() ||
				PayforConstant.TYPE_PAY_NEW_LIANLIAN==req.getChannelSource()){
            if(null == req.getCardNo() || "".equals(req.getCardNo())){
                throw new RuntimeException("pay.common param cardNo is null . ");
            }
            if(null == req.getAcctName() || "".equals(req.getAcctName() )){
                throw new RuntimeException("pay.common param acctName is null . ");
            }
            if(null == req.getIdNo() || "".equals(req.getIdNo())){
                throw new RuntimeException("pay.common param idNo is null . ");
            }
        }
    }


    @ApiOperation(value = "统一支付", notes = "统一支付")
    @RequestMapping(value = "/pay/common",method = RequestMethod.POST)
    public HttpBaseResponse<Object> pay(@RequestBody PayforRequestVo req, HttpServletRequest httpServletRequest){
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>new pay begin params is {}<<<<<<<<<<<<<<<<<<<<<<", JSON.toJSONString(req));
        try{
            validRequestParam(req);
        }catch (RuntimeException re){
            LOGGER.error("pay.common o2o-exception , more info :{}",re.getMessage());
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        LOGGER.info("pay.common param is : "+ JsonUtils.obj2json(req));

        Object obj = payforService.payForOrder(req, httpServletRequest);
        return HttpBaseResponse.success(obj);
    }
    
}