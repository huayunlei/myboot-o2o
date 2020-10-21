package com.ihomefnt.o2o.intf.domain.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "分页查询对象")
public class PageModel<T> {
	
	@ApiModelProperty("结果集")
	private List<T> list;

	@ApiModelProperty("总记录数")
	private long totalRecords;

	@ApiModelProperty("每页数目")
	private int pageSize;

	@ApiModelProperty("当前第几页")
	private int pageNo;

	@ApiModelProperty("总页数")
	private long totalPages;

	@ApiModelProperty("当前页后是否还有数据")
	private boolean hasNext;
}
