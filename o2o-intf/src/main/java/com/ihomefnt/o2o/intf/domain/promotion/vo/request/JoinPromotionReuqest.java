/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月19日
 * Description:JoinPromotionReuqest.java 
 */
package com.ihomefnt.o2o.intf.domain.promotion.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
@ApiModel("参加促销活动请求参数")
public class JoinPromotionReuqest extends HttpBaseRequest {

	@ApiModelProperty("订单Id")
	private Integer orderId;

	@ApiModelProperty("要参加的活动列表")
	private List<Integer> actCodes;
}
