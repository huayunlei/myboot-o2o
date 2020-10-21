/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: Ivan Shen
 * Date: 2016年8月15日
 * Description:UserIdParamVo.java 
 */
package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Ivan Shen
 */
@Data
@ApiModel("账户请求参数-用户ID")
public class UserIdParamVo {
	
	@ApiModelProperty("用户ID")
	@NotNull(message="用户ID不能为空")
	@Min(value=1,message="用户ID不合法")
	private Integer userId;
}
