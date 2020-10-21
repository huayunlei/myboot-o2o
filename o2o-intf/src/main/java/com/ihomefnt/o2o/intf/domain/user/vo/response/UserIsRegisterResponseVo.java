package com.ihomefnt.o2o.intf.domain.user.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/** 
* @ClassName: UserIsRegisterResponseVo 
* @Description: 用户是否注册response
* @author huayunlei
* @date Feb 20, 2019 1:37:06 PM 
*  
*/
@Data
@ApiModel("UserIsRegisterResponseVo")
public class UserIsRegisterResponseVo {

	/**
     *用户头像
     */
	@ApiModelProperty("是否注册")
    private boolean registerResult;
	
	@ApiModelProperty("描述")
    private String desc;
	
}
