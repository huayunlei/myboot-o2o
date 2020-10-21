package com.ihomefnt.o2o.intf.domain.meeting.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增留言请求体
 * @author czx
 */
@Data
@ApiModel("新增留言请求体")
public class AddMessageRequest {
	
	@ApiModelProperty("留言 ID")
    private String memberId;
	
	@ApiModelProperty("发布内容")
	private String content;
}
