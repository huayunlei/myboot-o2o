/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月8日
 * Description:ArtCommentViewRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.artcomment.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhang
 */
@Data
@ApiModel("评价查看参数")
public class ArtCommentViewRequest extends HttpBaseRequest {

	@ApiModelProperty("订单Id")
	@NotNull(message = "订单Id不能为空")
	private Integer orderId;

	@ApiModelProperty("商品Id")
	@NotNull(message = "商品Id不能为空")
	private Integer productId;
}
