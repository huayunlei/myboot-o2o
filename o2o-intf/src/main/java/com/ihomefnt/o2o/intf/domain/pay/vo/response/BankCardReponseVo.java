/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月10日
 * Description:CardReponse.java 
 */
package com.ihomefnt.o2o.intf.domain.pay.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("银行卡")
public class BankCardReponseVo {

	@ApiModelProperty("银行code")
	private String bankCode;

	@ApiModelProperty("银行名称")
	private String bankName;

	@ApiModelProperty("银行logo")
	private String bankLogo;

	@ApiModelProperty("银行卡编号")
	private String cardNo;

	private String cardTypeDesc;// 卡类型 2 借记卡 3 信用卡,

	private String dayAmount;// 单日限额 单位元 整数类型,

	private String monthAmount;// 单月限额 单位元 整数类型,

	private String singleAmount;// 单笔限额 单位元 整数类

	private String bankStatusDesc;// 银行状态 0 正常 2 维护中,

}
