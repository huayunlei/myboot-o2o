/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年8月19日
 * Description:ConfirmNodeParamRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.hbms.dto;

import com.ihomefnt.o2o.intf.domain.hbms.vo.request.BaseFrontRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("节点是否满意请求参数")
public class ConfirmNodeParamDto extends BaseFrontRequest {

	@ApiModelProperty(value = "评论")
	private String comment;

	@ApiModelProperty(value = "是否满意:0不满意，1满意")
	private String confirm;

	@ApiModelProperty(value = "下一节点id")
	private String nextNodeId;

	@ApiModelProperty(value = "当前节点id")
	private String nodeId;
}
