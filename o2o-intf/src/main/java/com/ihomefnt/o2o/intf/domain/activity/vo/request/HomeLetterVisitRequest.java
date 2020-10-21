package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 记录浏览日志请求参数
 * @author ZHAO
 */
@Data
@ApiModel("记录浏览日志请求参数")
public class HomeLetterVisitRequest {
	@ApiModelProperty("文章ID")
    private Integer articleId;
	
	@ApiModelProperty("微信openId")
    private String openId;
	
	@ApiModelProperty("浏览类型：8活动首页  9家书个人主页")
    private Integer visitType;
}
