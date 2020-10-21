package com.ihomefnt.o2o.intf.domain.user.vo.response;

import lombok.Data;

/**
 * 实名认证结果
 * @author ZHAO
 */
@Data
public class CertificationResultResponseVo {
	private Boolean result;
	
	private String msg;
	
	private Integer code;
}
