package com.ihomefnt.o2o.intf.domain.meeting.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取留言请求体
 * 
 * @author czx
 */
@Data
@ApiModel("获取留言请求体")
public class QueryMessageRequest {
	
	@ApiModelProperty("每页多少条")
	private Integer pageSize;
	
	@ApiModelProperty("当前页")
	private Integer pageNo;
}
