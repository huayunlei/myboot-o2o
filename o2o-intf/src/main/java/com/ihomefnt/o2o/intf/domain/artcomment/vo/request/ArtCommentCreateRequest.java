/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月8日
 * Description:ArtCommentCreateRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.artcomment.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhang
 */
@Data
@ApiModel("评价创建参数")
public class ArtCommentCreateRequest extends HttpBaseRequest {

	@ApiModelProperty("用户打星")
	@NotNull(message = "请选择评分")
	private Integer userStar;

	@ApiModelProperty("发表内容")
	@NotNull(message = "请先输入内容")
	private String userComment;

	@ApiModelProperty("订单Id")
	@NotNull(message = "订单Id不能为空")
	private Integer orderId;

	@ApiModelProperty("商品Id")
	@NotNull(message = "商品Id不能为空")
	private Integer productId;

	@ApiModelProperty("是否发表到晒家:true是 false否")
	@NotNull(message = "是否发表到晒家标识不能为空")
	private Boolean toShareOrderTag;

	@ApiModelProperty("发表图片")
	private List<String> images;

	@ApiModelProperty("订单类型  （5 艺术品类型 6文旅商品 15小星星艺术类型）")
	private Integer orderType;

}
