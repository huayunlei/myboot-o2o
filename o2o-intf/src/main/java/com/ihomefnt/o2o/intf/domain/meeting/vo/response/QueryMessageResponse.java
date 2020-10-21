package com.ihomefnt.o2o.intf.domain.meeting.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取留言响应体
 * @author czx
 */
@Data
@ApiModel("获取留言响应体")
public class QueryMessageResponse {
	
	@ApiModelProperty("昵称")
	private String nickName;

	@ApiModelProperty("头像 URL")
	private String url;
	
	@ApiModelProperty("发布内容")
	private String content;
	
	@ApiModelProperty("发布时间")
	private String msgTime;

}
