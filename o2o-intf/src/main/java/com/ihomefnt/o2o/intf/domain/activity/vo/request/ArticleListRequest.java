package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询文章列表请求参数
 * @author ZHAO
 */
@Data
@ApiModel("查询文章列表请求参数")
public class ArticleListRequest{

	@ApiModelProperty("投稿类型：1精选投稿  2排名")
	private Integer searchType;

	@ApiModelProperty("每页多少条")
	private Integer pageSize;
	
	@ApiModelProperty("当前页")
    private Integer pageNo;
}
