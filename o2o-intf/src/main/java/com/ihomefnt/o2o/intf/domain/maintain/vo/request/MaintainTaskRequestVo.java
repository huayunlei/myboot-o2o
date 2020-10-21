/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月26日
 * Description:MaintainTaskRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.maintain.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
@ApiModel("提交报修任务请求参数")
public class MaintainTaskRequestVo extends HttpBaseRequest {

	@ApiModelProperty("联系人")
	private String linkMan = "";

	@ApiModelProperty("联系手机")
	private String linkMobile = "";

	@ApiModelProperty("订单id")
	private Integer orderId;

	@ApiModelProperty("维修地址")
	private String maintainAddress;

	@ApiModelProperty("问题描述")
	private String questionDesc;

	@ApiModelProperty("维修图片")
	private List<String> maintainUrls;

	@ApiModelProperty("报修任务ID")
	private Integer taskId;

	@ApiModelProperty("类型：0新增报修申请  1修改申请  2取消申请")
	private Integer taskType;
}
