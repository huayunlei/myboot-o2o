package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询用户投稿信息请求参数
 * @author ZHAO
 */
@Data
@ApiModel("查询用户投稿信息请求参数")
public class ArticleInfoRequest {
	@ApiModelProperty("文章ID")
    private Integer articleId;
	
	@ApiModelProperty("微信openId")
    private String openId;
}
