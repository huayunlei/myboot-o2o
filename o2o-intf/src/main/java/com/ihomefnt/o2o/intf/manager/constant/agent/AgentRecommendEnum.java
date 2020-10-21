package com.ihomefnt.o2o.intf.manager.constant.agent;

/**
 * 经纪人推荐客户
 * @author ZHAO
 */
public enum AgentRecommendEnum {
	SUCCESS(0, 1L, "注意：请推荐客户在72小时之内下载并注册“艾佳生活”APP，注册成功后，他将成为您的有效客户。"),PARAM_LEGAL(1, 2L, "参数校验失败"),
	EXIST_USER_LATENT(3, 3L, "您已经登记过该客户啦！请推荐TA在72小时之内下载并注册“艾佳生活”APP，注册成功后，他将成为您的有效客户。"),
	FAILED(2, 4L, "系统错误，请联系艾佳生活"),EXIST_USER_DEAL(4, 5L, "您已经登记过该客户啦！不用重复推荐"),
	USER_LATENT(5, 6L, "再等等！该客户已被其他经纪人登记，如果客户未能在72小时内及时注册，你还可以再次推荐他~"),
	USER_DEAL(6, 7L, "很遗憾晚了一步！该客户已被其他经纪人成功推荐~"),
	USER_OUTNUM(7, 8L, "休息一会！您今天的推荐人数已经达到上限了，请明天再推荐吧"),AGENT_NOTEXIST(8, 9L, "您的经纪人账号出现异常，请截图联系艾佳生活"),
	AGENT_AGAINRECOMMEND(9, 10L, "您的重新推荐次数已达上限"),AGAIN_USER_OUTNUM(10, 11L, "休息一会！您今天的重新推荐人数已经达到上限了，请明天再推荐吧");
	
	private Integer code;//后端返回结果
	
	private Long result;//返回给前端的code
	
	private String value;//返回给前端的value

	public static AgentRecommendEnum getValue(Integer code){
		for(AgentRecommendEnum agentRecommendEnum : AgentRecommendEnum.values()){
			if(agentRecommendEnum.getCode().equals(code)){
				return agentRecommendEnum;
			}
		}
		return FAILED;
	}
	
	AgentRecommendEnum(Integer code, Long result, String value){
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
