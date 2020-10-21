/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月13日
 * Description:ArtistRegisterRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("设计师注册请求参数")
public class ArtistRegisterRequest extends HttpBaseRequest {

	@ApiModelProperty("微信openId")
	private String openId;

	@ApiModelProperty("验证码")
	private String smsCode;

	@ApiModelProperty("姓名")
	private String userName;

	@ApiModelProperty("设计师头像")
	private String image;
}
