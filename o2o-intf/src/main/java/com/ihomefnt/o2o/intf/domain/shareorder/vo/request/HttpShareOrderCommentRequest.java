package com.ihomefnt.o2o.intf.domain.shareorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户评论请求
 * 
 * @author Charl
 */
@Data
@ApiModel(value = "发表评论请求")
public class HttpShareOrderCommentRequest extends HttpBaseRequest {

	@ApiModelProperty("晒家id")
	private String shareOrderId;

	@ApiModelProperty("被回复评论id")
	private String commentId;

	@ApiModelProperty("发表评论或者发表回复")
	private String comment;

	@ApiModelProperty("晒家类型:0 表示老晒家, 1 表示专题")
	private int type;
}
