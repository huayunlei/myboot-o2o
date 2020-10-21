package com.ihomefnt.o2o.intf.domain.lechange.dto;

import com.ihomefnt.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class LechangeParam extends HttpBaseRequest{
	
	private String deviceId; //乐檬设备id
	
	private String accessToken; //乐檬管理员账户token
	
	private String phone; //用户手机号码
	
	private String token; //乐檬普通用户token
	
	private String smsCode;  //短信验证码
	
	private Integer count; //要显示的设备数量
	
	private Integer nextDeviceid; //下一个设备id

}
