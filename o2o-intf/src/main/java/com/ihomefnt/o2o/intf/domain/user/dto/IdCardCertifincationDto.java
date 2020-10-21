package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

/**
 * 实名认证
 * @author ZHAO
 */
@Data
public class IdCardCertifincationDto {
	private Integer userId;//用户id
	
	private String realName;//用户真实姓名
	
	private String idCard;//用户身份证
	
	private Boolean authentication;//是否已认证

}
