/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月14日
 * Description:CardBinResultVo.java 
 */
package com.ihomefnt.o2o.intf.domain.pay.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class CardBinResultDto {

	private String bankCode;// 银行code,
	private String bankName;// 银行名称,
	private Integer cardType;// 银行卡类型
}
