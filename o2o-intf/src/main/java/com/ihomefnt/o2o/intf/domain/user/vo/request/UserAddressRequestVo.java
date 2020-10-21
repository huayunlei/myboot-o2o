package com.ihomefnt.o2o.intf.domain.user.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Data
@ApiModel("UserAddressRequestVo")
public class UserAddressRequestVo {
	
	/**
     *用户id
     */
	@ApiModelProperty("用户ID")
    private Integer userId;

}
