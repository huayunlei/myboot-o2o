package com.ihomefnt.o2o.intf.domain.artist.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("添加评论")
public class StarOrderAddCommentRequest {

	@ApiModelProperty("登录标识")
	@NotNull(message="token不能为空")
	private String accessToken;
	
	@ApiModelProperty("订单明细id")
	@NotNull(message="订单明细id不能为空")
	private Integer orderDetailId;
	
	@ApiModelProperty("评论内容")
	@NotNull(message="评论内容")
	private String comment;

}
