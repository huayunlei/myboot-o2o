package com.ihomefnt.o2o.intf.manager.constant.agent;

/**
 * 申请体验金
 * @author ZHAO
 */
public enum ApplyExperienceGoldEnum {
	SYSTEM_FAILED(-1, -1L, "系统错误，请联系艾佳生活"),SUCCESS(0, 1L, "体验金已立即打到您的微信账号零钱钱包，请留意微信通知。您的微信将作为佣金收益账号，请妥善保管。"),
	PARAM_LEGAL(1, 2L, "系统错误，请联系艾佳生活"),USER_DISABLE(2, 3L, "请先申请成为经纪人，并通过审核。"),WECHAT_EXIST(3, 4L, "您的微信号已领取过体验金啦，不可以代他人领取。您的用户的交易佣金今后会打款到您的微信钱包。"),
	MOBILE_EXIST(4, 5L, "您已经领取过体验金啦。期待您成为销售达人，用户的交易佣金会及时打款到您的微信钱包。"),USER_NULL(5, 6L, "请先申请成为经纪人，并通过审核。"),
	SMS_ERROR(6, 7L, "短信验证码错误"),SMS_ABATE(7, 8L, "短信验证码失效，请重新获取"),
	SMS_OVERDUE(8, 9L, "短信验证码过期，请重新获取"),USER_EXIST(9, 10L, "您的微信号已被他人使用，请截图联系艾佳生活"),
	USER_INAUDIT(10, 11L, "请先申请成为经纪人，并通过审核。"),SMS_NULL(11, 12L, "获取不到您的微信号，请在“艾佳生活”公众号中，打开“成为经纪人”页面，领取体验金。");
	
	private Integer code;//后端返回结果
	
	private Long result;//返回给前端的code
	
	private String value;//返回给前端的value

	public static ApplyExperienceGoldEnum getValue(Integer code){
		for(ApplyExperienceGoldEnum applyExperienceGoldEnum : ApplyExperienceGoldEnum.values()){
			if(applyExperienceGoldEnum.getCode().equals(code)){
				return applyExperienceGoldEnum;
			}
		}
		return PARAM_LEGAL;
	}
	
	ApplyExperienceGoldEnum(Integer code, Long result, String value){
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
