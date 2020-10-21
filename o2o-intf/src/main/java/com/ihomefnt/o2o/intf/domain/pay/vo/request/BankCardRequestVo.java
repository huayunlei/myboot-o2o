/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月10日
 * Description:CardRequest.java 
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
@ApiModel("银行卡请求参数")
public class BankCardRequestVo extends HttpBaseRequest {

	@ApiModelProperty("银行卡编号")
	private String cardNo;

}
