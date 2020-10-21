package com.ihomefnt.o2o.intf.manager.constant.agent;

/**
 * 经纪人登录
 * @author ZHAO
 */
public enum AgentLoginEnum {
	PHONE_ERROR(-1, 7L, "请输入正确的手机号码"),FAILED(0, 4L, "系统错误，请联系艾佳生活"),SUCCESS(1, 1L, "登录成功"),
	VERIFY_ERROR(2, 2L, "短信验证码错误"),VERIFY_AGAIN(3, 3L, "请重新获取短信验证码"),
	VERIFY_EXPIRED(4, 5L, "请重新获取短信验证码"),USER_NOAGENT(5, 6L, "您还不是经纪人");
	
	private Integer code;//后端返回结果
	
	private Long result;//返回给前端的code
	
	private String value;//返回给前端的value

	public static AgentLoginEnum getValue(Integer code){
		for(AgentLoginEnum agentLoginEnum : AgentLoginEnum.values()){
			if(agentLoginEnum.getCode().equals(code)){
				return agentLoginEnum;
			}
		}
		return FAILED;
	}
	
	AgentLoginEnum(Integer code, Long result, String value){
		this.code = code;
		this.result = result;
		this.value = value;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
