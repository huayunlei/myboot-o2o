/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月9日
 * Description:BankInfoResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.pay.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("银行")
public class BankInfoResponseVo {

	@ApiModelProperty("银行code")
	private String bankCode;

	@ApiModelProperty("银行名称")
	private String bankName;

	@ApiModelProperty("银行logo")
	private String bankLogo;

	private String cardTypeDesc;// 卡类型 2 借记卡 3 信用卡,

	private String dayAmount;// 单日限额 单位元 整数类型,

	private String monthAmount;// 单月限额 单位元 整数类型,

	private String singleAmount;// 单笔限额 单位元 整数类

	private Integer userId; // 用户id

	private String acctName;// 姓名

	private String idCard;// 身份证

	private String bindMobile;// 手机

	private String shortBankCard;// 银行卡缩略后四位
	private String completeBankCard;// 完整的银行卡信息
}
