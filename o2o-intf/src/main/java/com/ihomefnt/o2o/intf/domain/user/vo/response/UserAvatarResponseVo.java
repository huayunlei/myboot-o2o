package com.ihomefnt.o2o.intf.domain.user.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/** 
* @ClassName: UserAvatarResponseVo 
* @Description: 用户头像response
* @author huayunlei
* @date Feb 20, 2019 1:36:39 PM 
*  
*/
@Data
@ApiModel("UserAvatarResponseVo")
public class UserAvatarResponseVo {

	/**
     *用户头像
     */
	@ApiModelProperty("用户头像")
    private String userImage;
	
}
