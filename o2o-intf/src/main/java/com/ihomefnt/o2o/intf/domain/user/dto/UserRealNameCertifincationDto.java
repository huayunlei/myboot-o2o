package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

/** 
* @ClassName: UserRealNameCertifincationRequestDto 
* @Description: 用户实名认证dto 
* @author huayunlei
* @date Feb 20, 2019 2:09:31 PM 
*  
*/
@Data
public class UserRealNameCertifincationDto {

	private Integer userId;
	private String realName;
	private String idCard;
}
