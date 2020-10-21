/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月14日
 * Description:ILianLianProxy.java 
 */
package com.ihomefnt.o2o.intf.proxy.pay;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.pay.dto.BankInfoResultDto;
import com.ihomefnt.o2o.intf.domain.pay.dto.CardBinResultDto;
import com.ihomefnt.o2o.intf.domain.pay.dto.PayOrderDto;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @see http://192.168.1.31:10038/fgw-web/swagger/
 */
public interface PayforProxy {

	/**
	 * 查询所有连连支持的银行列表
	 * 
	 * @return
	 */
	List<BankInfoResultDto> queryAllSupportPayBankInfo(String appVersion);

	/**
	 * 查询卡的信息
	 * 
	 * @param cardNo
	 * @return
	 */
	ResponseVo<CardBinResultDto> cardbin(String cardNo);

	/**
	 * 查询单个银行的信息
	 * 
	 * @param bankCode
	 * @return
	 */
	BankInfoResultDto eachCardInfo(String bankCode);

	/**
	 * 记录信息并加签接口
	 * 
	 * @param payOrder
	 * @return
	 */
	PayOrderDto signAndRecord(PayOrderDto payOrder);

	/**
	 * 查询用户已绑定的银行列表
	 * 
	 * @param id
	 * @return
	 */
	List<BankInfoResultDto> queryActivationBankInfoByUserId(Integer id);

	/**银行卡解绑
	 * @param id
	 * @param cardNo
	 */
	boolean unbindCard(Integer id, String cardNo);
	
	 /**
     * 统一支付 底层对接
     * @param param 请求参数
     * @return data 响应报文的data节点
     * @throws RuntimeException when catch any exception
     */
    JSONObject pay(JSONObject param) throws RuntimeException;

	Object pullOnlinePay(Map<String, Object> params);

}
