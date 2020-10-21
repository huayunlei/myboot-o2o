package com.ihomefnt.o2o.intf.domain.meeting.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增家庭成员请求体
 * @author czx
 */
@Data
@ApiModel("新增家庭成员请求体")
public class AddMemberRequest {
	
	@ApiModelProperty("家庭 ID")
    private String familyId;
	
	@ApiModelProperty("微信code")
	private String code;

	@ApiModelProperty("昵称")
	private String nickName;

	@ApiModelProperty("头像 URL")
	private String url;
}
