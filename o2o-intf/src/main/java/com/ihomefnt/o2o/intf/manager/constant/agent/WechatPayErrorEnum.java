package com.ihomefnt.o2o.intf.manager.constant.agent;

/**
 * 微信打款失败信息
 * @author ZHAO
 */
public enum WechatPayErrorEnum {
	NOAUTH("NOAUTH", "没有权限", "抱歉，支付异常（NOAUTH），请截图联系艾佳生活"),
	AMOUNT_LIMIT("AMOUNT_LIMIT", "付款金额不能小于最低限额", "抱歉，支付异常（AMOUNT_LIMIT），请截图联系艾佳生活"),
	PARAM_ERROR("PARAM_ERROR", "参数错误", "抱歉，支付异常（PARAM_ERROR），请截图联系艾佳生活"),
	OPENID_ERROR("OPENID_ERROR", "Openid错误", "获取不到您的微信号，请在“艾佳生活”公众号中，打开“成为经纪人”页面，领取体验金"),
	NOTENOUGH("NOTENOUGH", "余额不足", "抱歉，支付异常（NOTENOUGH），请截图联系艾佳生活"),
	SYSTEM_ERROR("SYSTEMERROR", "系统繁忙，请稍后再试", "抱歉，支付异常（SYSTEMERROR），请截图联系艾佳生活"),
	NAME_MISMATCH("NAME_MISMATCH", "姓名校验出错", "打款失败，请使用您在微信钱包中认证的真实姓名"),
	SIGN_ERROR("SIGN_ERROR", "签名错误", "抱歉，支付异常（SIGN_ERROR），请截图联系艾佳生活"),
	XML_ERROR("XML_ERROR", "Post内容出错", "抱歉，支付异常（XML_ERROR），请截图联系艾佳生活"),
	FATAL_ERROR("FATAL_ERROR", "两次请求参数不一致", "抱歉，支付异常（FATAL_ERROR），请截图联系艾佳生活"),
	CA_ERROR("CA_ERROR", "证书出错", "抱歉，支付异常（CA_ERROR），请截图联系艾佳生活"),
	V2_ACCOUNT_SIMPLE_BAN("V2_ACCOUNT_SIMPLE_BAN", "无法给非实名用户付款", "打款失败，请先在微信钱包中进行实名认证。"),
	ORTHER("ORTHER", "其他情况", "抱歉，支付异常（ORTHER），请截图联系艾佳生活");
	
	
	private String code;//后端返回结果
	
	private String desc;//微信支付错误描述
	
	private String value;//返回给前端的value

	public static WechatPayErrorEnum getValue(String code){
		for(WechatPayErrorEnum wechatPayErrorEnum : WechatPayErrorEnum.values()){
			if(wechatPayErrorEnum.getCode().equals(code)){
				return wechatPayErrorEnum;
			}
		}
		return ORTHER;
	}
	
	WechatPayErrorEnum(String code, String desc, String value){
		this.code = code;
		this.desc = desc;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
