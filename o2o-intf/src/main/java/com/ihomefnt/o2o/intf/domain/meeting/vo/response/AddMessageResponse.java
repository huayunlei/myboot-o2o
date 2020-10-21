package com.ihomefnt.o2o.intf.domain.meeting.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增留言响应体
 * @author czx
 */
@Data
@ApiModel("新增留言响应体")
public class AddMessageResponse {
	
	@ApiModelProperty("留言 ID")
	private String msgId;
}
