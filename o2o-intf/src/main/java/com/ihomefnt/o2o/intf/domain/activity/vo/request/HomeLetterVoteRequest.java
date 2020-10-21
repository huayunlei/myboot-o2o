package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 投票请求参数
 * @author ZHAO
 */
@Data
@ApiModel("投票请求参数")
public class HomeLetterVoteRequest {
	@ApiModelProperty("文章ID")
    private Integer articleId;
	
	@ApiModelProperty("微信openId")
    private String openId;

	@ApiModelProperty("微信头像")
    private String headImgUrl;
	
	@ApiModelProperty("微信昵称")
	private String nickName;
}
