/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月13日
 * Description:ArtistApplyCashRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
@ApiModel("申请提现请求参数")
public class ArtistApplyCashRequest extends HttpBaseRequest {

	@ApiModelProperty("提现金额")
	private BigDecimal applyCashMoney;
}
