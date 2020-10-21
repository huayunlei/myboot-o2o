package com.ihomefnt.o2o.intf.domain.order.vo.response;

import lombok.Data;

@Data
public class HttpWeChatParamResponse {

	private String appid;
	private String partnerid;
	private String prepayid;
	private String packagestr;
	private String noncestr;
	private String timestamp;
	private String sign;
	private String singType;
}
