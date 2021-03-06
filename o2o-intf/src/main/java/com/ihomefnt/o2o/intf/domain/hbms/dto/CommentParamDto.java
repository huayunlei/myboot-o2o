package com.ihomefnt.o2o.intf.domain.hbms.dto;

import com.ihomefnt.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author wangjin
 *
 */
@Data
@ApiModel(description = "施工点评参数信息")
public class CommentParamDto extends HttpBaseRequest{

	@ApiModelProperty(value = "评论")
	private String comment;

	@ApiModelProperty(value = "分数")
	private String score;

	@ApiModelProperty(value = "订单id")
	private String orderId;
}
