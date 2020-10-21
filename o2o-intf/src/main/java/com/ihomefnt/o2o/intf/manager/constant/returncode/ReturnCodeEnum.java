package com.ihomefnt.o2o.intf.manager.constant.returncode;

public enum ReturnCodeEnum {
	
	/**
	 * account-web  1012
	 */
	ACCOUNT_AJB_QUERY_FAIL(101201L, "艾佳币查询失败！")
	;
	
	
	private Long code;
	private String msg;
	
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private ReturnCodeEnum(Long code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
