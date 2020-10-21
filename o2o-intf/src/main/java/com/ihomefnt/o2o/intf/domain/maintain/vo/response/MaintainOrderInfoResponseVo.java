/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月26日
 * Description:MaintainOrderInfoResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.maintain.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@ApiModel
@Data
public class MaintainOrderInfoResponseVo {

	@ApiModelProperty("订单号")
	private Integer orderId;

	@ApiModelProperty("房号 一期1栋1001室")
	private String maintainAddress;

	@ApiModelProperty("房号 1-1-1001")
	private String simpleMaintainAddress;

	@ApiModelProperty("楼盘名称")
	private String buildingName;
}
