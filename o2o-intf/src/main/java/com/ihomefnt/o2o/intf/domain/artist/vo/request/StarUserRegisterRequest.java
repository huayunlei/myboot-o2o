package com.ihomefnt.o2o.intf.domain.artist.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("小星星用户注册请求参数")
public class StarUserRegisterRequest {

	@ApiModelProperty("微信号")
	private String wechatId;

	@ApiModelProperty("微信昵称")
	private String wecharNickName;
	
	@ApiModelProperty("头像")
	private String uImg;//头像
	
	@ApiModelProperty("encryptedData")
    private String encryptedData;

	@ApiModelProperty("iv")
    private String iv;

	@ApiModelProperty("loginSessionKey")
    private String loginSessionKey;
	
	private String mobile;
	
}
