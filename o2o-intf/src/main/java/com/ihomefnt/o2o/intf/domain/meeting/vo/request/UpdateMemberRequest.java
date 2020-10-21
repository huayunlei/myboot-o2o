package com.ihomefnt.o2o.intf.domain.meeting.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改家庭成员请求体
 * @author czx
 */
@Data
@ApiModel("修改家庭成员请求体")
public class UpdateMemberRequest {
	
	@ApiModelProperty("微信openId")
	private String openId;

	@ApiModelProperty("昵称")
	private String nickName;

	@ApiModelProperty("头像 URL")
	private String url;
}
