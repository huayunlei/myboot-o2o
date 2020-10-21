/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月16日
 * Description:BankErrorCode.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.pay;

/**
 * @author zhang
 */
public enum BankErrorCode {
	/**
	 * 银行状态 0 正常 2 维护中
	 */

	NOT_SUPPROT("无法获取此银行服务，请核对号码是否正确或尝试其他银行卡或使用其他付款方式", 102620L),
	
	NOT_VALID("暂不支持该卡或卡号输入有误,请核实号码是否正确或更换其他银行卡",102612L),
	
	NOT_CARD_BIN("暂不支持该卡或卡号输入有误,请核实号码是否正确或更换其他银行卡",102651L),

	NOT_MONEY("本次付款金额已超限，请尝试其他银行卡或使用其他付款方式",102616L);
	
	
	private String msg;

	private Long code;

	private BankErrorCode(String msg, Long code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(Long code) {
		for (BankErrorCode c : BankErrorCode.values()) {
			if (c.getCode().compareTo(code)== 0 ) {
				return c.msg;
			}
		}
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

}
