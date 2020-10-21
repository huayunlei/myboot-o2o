package com.ihomefnt.o2o.intf.domain.user.vo.response;

import lombok.Data;

/**
 * 实名认证信息
 * @author ZHAO
 */
@Data
public class IdCardCertifincationResponseVo {
	private Integer userId;//用户id
	
	private String realName;//用户真实姓名
	
	private String idCardNum;//用户身份证
	
	private Boolean idCardCertification;//是否已认证

	public IdCardCertifincationResponseVo() {
		this.userId = -1;
		this.realName = "";
		this.idCardNum = "";
		this.idCardCertification = false;
	}
}
