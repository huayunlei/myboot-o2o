package com.ihomefnt.o2o.intf.manager.constant.agent;

/**
 * 经纪人注册
 * @author ZHAO
 */
public enum AgentRegisterEnum {
	//后台返回
	/*value="返回码:-1-系统错误 0-注册成功 1-参数不合法 2-禁用 3-用户中心返回null 4-审核中 5-审核通过 6-换微信号登陆 7-微信号已经存在 8-用户中心：注册失败 9-用户中心：账号已存在 10-验证码错误 11-验证码请重新获取 12-验证码已过期"*/
	
	SUCCESS(0, 1L, "您的申请已提交，艾佳生活将会尽快审核，请留意短信提醒。"),PARAM_LEGAL(1, 2L, "短信验证码错误"),
	USER_DISABLE(2, 3L, "您不能申请经纪人，请联系艾佳生活"),USER_NULL(3, 4L, "系统错误，请联系艾佳生活"),
	USER_INAUDIT(4, 5L, "您已经申请过，正在审批中，留意短信通知"),AGENT_EXIST(5, 6L, "您已经申请通过啦，请在微信小程序中“艾佳生活经纪人”登录使用"),
	MOBILE_EXIST(6, 7L, "您的手机号已经申请过啦，同一个手机号不能再次注册。"),WECHAT_EXIST(7, 8L, "您的微信号已经申请过啦，同一个微信号不能再次注册。"),
	USER_FAILED(8, 9L, "系统错误，请联系艾佳生活"),USER_EXIST(9, 10L, "您已经申请过，留意短信通知"),
	SMS_ERROR(10, 11L, "短信验证码错误"),SMS_NULL(11, 12L, "请输入短信验证码"),
	SMS_OVERDUE(12, 13L, "请重新获取短信验证码"),SYSTEM_FAILED(-1, 14L, "系统错误，请联系艾佳生活"),IDCARD_FAILED(13, 15L, "姓名、身份证号不匹配");
	
	private Integer code;//后端返回结果
	
	private Long result;//返回给前端的code
	
	private String value;//返回给前端的value

	public static AgentRegisterEnum getValue(Integer code){
		for(AgentRegisterEnum agentRegisterEnum : AgentRegisterEnum.values()){
			if(agentRegisterEnum.getCode().equals(code)){
				return agentRegisterEnum;
			}
		}
		return USER_FAILED;
	}
	
	AgentRegisterEnum(Integer code, Long result, String value){
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
