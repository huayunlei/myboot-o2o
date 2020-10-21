/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月26日
 * Description:MaintainUserInfoResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.maintain.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang
 */
@Data
@ApiModel
public class MaintainUserInfoResponseVo {

	@ApiModelProperty("描述")
	private String linkDesc="";

	@ApiModelProperty("用户id")
	private Integer userId;

	@ApiModelProperty("联系人")
	private String linkMan;

	@ApiModelProperty("联系手机")
	private String linkMobile;

	@ApiModelProperty("维修地址信息")
	private List<MaintainOrderInfoResponseVo> maintainList;

	public MaintainUserInfoResponseVo() {

		this.linkMan = "";
		this.linkMobile = "";
		this.maintainList = new ArrayList<>();
	}

}
