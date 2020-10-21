package com.ihomefnt.o2o.intf.domain.bankcard.dto;

import lombok.Data;

@Data
public class BankCheckUserDto {
	
	private String bankcard;
	private String realName;
	private String cardNo;
	private String mobile;
	private Integer userId;
	private String module;
	
	private String sign;
	
}
