package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 发表文章请求参数
 * @author ZHAO
 */
@Data
@ApiModel("发表文章请求参数")
public class PublishArticleRequest {
	@ApiModelProperty("微信openId")
    private String openId;
	
	@ApiModelProperty("投稿人手机号")
    private String mobile;
	
	@ApiModelProperty("微信头像")
    private String headImgUrl;
	
	@ApiModelProperty("微信昵称")
	private String nickName;

	@ApiModelProperty("投稿内容")
	private String content;
}
