package com.ihomefnt.o2o.intf.domain.hbms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author wangjin
 *
 */
@Data
@ApiModel(description = "施工点评信息")
public class GetCommentResultDto {
	
	@ApiModelProperty(value = "评论")
	private String comment;
	
	@ApiModelProperty(value = "分数")
	private String score;
}
