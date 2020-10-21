/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月14日
 * Description:LianLianService.java 
 */
package com.ihomefnt.o2o.intf.service.pay;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.order.dto.PayInput;
import com.ihomefnt.o2o.intf.domain.pay.vo.request.BankCardRequestVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.request.PayRequestVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.request.PayforRequestVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.response.BankCardReponseVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.response.BankListResponseVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.response.IdCardResponseVo;
import com.ihomefnt.o2o.intf.domain.pay.vo.response.PayResponseVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhang
 * @see http://192.168.1.31:10038/fgw-web/swagger/
 */
public interface PayforService {

	/**
	 * 查询连连支付支持的银行列表
	 * 
	 * @return
	 */
	BankListResponseVo getBankListByLianlian(HttpBaseRequest request);

	/**
	 * 根据卡号查询银行卡信息
	 * 
	 * @param request
	 * @return
	 */
	BankCardReponseVo getCardInfo(BankCardRequestVo request);

	/**
	 * 将支付信息提交给连连支付平台
	 * 
	 * @param request
	 * @return
	 */
	PayResponseVo getOrderInfoForLianlian(PayRequestVo request);

	/**
	 * 查询用户已绑定的银行列表
	 * 
	 * @param id
	 * @return
	 */
	BankListResponseVo getBankListByUserId(Integer id);

	/**
	 * 银行卡解绑
	 * 
	 * @param request
	 * @return
	 */
	boolean unbindBankInfo(BankCardRequestVo request);

	/**
	 * 查询用户的连连支付绑定身份证信息
	 * 
	 * @param id
	 * @return
	 */
	IdCardResponseVo getIdCardByUserId(Integer id);
	
	/**
     * 统一支付 服务逻辑
     * @return 对应的响应 支付宝 微信 lianlian
     */
	Object payForOrder(PayforRequestVo req, HttpServletRequest httpServletRequest);
	
	/**
     * 统一支付 服务逻辑
     * @param param 请求参数
     * @return 对应的响应 支付宝 微信 lianlian
     * @throws RuntimeException when catch any exception
     */
    Object pay(JSONObject param)throws RuntimeException;
    
    
    /**
     * 处理老的连连拉起支付的请求
     * @param request
     * @return
     */
    PayResponseVo handlerOldLianlianSignAndRecord(PayRequestVo request);

    /**
     * 处理老的支付宝拉起支付的请求
     * @param payInput
     * @return
     */
    String handlerOldAliPaySignAndRecord(PayInput payInput);

}
