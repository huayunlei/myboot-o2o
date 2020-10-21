package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询投票记录请求参数
 * @author ZHAO
 */
@Data
@ApiModel("查询投票记录请求参数")
public class QueryVoteRecordRequest {
	@ApiModelProperty("文章ID")
    private Integer articleId;

	@ApiModelProperty("每页多少条")
	private Integer pageSize;
	
	@ApiModelProperty("当前页")
    private Integer pageNo;
}
