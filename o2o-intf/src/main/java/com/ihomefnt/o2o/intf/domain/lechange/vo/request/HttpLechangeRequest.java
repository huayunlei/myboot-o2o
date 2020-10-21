package com.ihomefnt.o2o.intf.domain.lechange.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class HttpLechangeRequest extends HttpBaseRequest {

	private String adminToken; //管理员token
	
	private String phone; //手机号码

}
