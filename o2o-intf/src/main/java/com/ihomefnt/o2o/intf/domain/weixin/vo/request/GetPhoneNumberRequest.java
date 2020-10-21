package com.ihomefnt.o2o.intf.domain.weixin.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取手机号
 * @author ZHAO
 */
@Data
@ApiModel("获取用户手机号请求参数")
public class GetPhoneNumberRequest {
	@ApiModelProperty("code")
    private String code;
	
	@ApiModelProperty("encryptedData")
    private String encryptedData;

	@ApiModelProperty("iv")
    private String iv;

	@ApiModelProperty("loginSessionKey")
    private String loginSessionKey;
}
