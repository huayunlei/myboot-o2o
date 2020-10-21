/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月9日
 * Description:BankListResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.pay.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
@ApiModel("银行列表")
public class BankListResponseVo {

	@ApiModelProperty("银行列表")
	private List<BankInfoResponseVo> bankList;

	@ApiModelProperty("true:可用,false:不可用")
	private boolean enabled;
}
