package com.ihomefnt.o2o.intf.domain.weixin.vo.request;

import lombok.Data;

@Data
public class HttpGetKRequest {

	private String shareUrl;
	
	private String appId;
	
	private String secret;
	
	private String wxType;//微信程序名：happyhouse
	
}
