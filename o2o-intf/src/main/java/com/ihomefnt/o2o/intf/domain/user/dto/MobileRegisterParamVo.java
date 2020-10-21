package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

@Data
public class MobileRegisterParamVo {

	private String mobile;// 手机号码

	private int source;// 用户来源

	private String pValue;// p值

}
