/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月10日
 * Description:PayRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.pay.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("订单支付请求参数")
public class PayRequestVo extends HttpBaseRequest {

	@ApiModelProperty("订单Id")
	private Integer orderId;

	@ApiModelProperty("支付金额")
	private String selectSum;

	@ApiModelProperty("银行卡编号")
	private String cardNo;

	@ApiModelProperty("姓名")
	private String userName;

	@ApiModelProperty("身份证")
	private String idCard;
}
