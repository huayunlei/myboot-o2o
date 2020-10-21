/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月8日
 * Description:ArtCommentListRequest.java 
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
@ApiModel("评价列表参数")
public class ArtCommentListRequest extends HttpBaseRequest {

	@ApiModelProperty("商品Id")
	@NotNull(message = "请先输入商品Id")
	private Integer productId;

	@ApiModelProperty("当前页码")
	private Integer pageNo;

	@ApiModelProperty("每页的数据条数")
	private Integer pageSize;
}
