package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

/**
 * 全品家大订单用户信息
 * @author ZHAO
 */
@Data
public class AladdinCustomerInfoVo {
	private Integer customerId;//客户id
	
	private String name;//姓名
	
	private String gender;//性别,1:男，2:女
	
	private String mobile;//手机号
	
	private String identifyNum;//身份证号
	
	private String spareName;//备用联系人
	
	private String spareMobile;//备用联系人电话
	
	private Integer userId;//用户ID
}
