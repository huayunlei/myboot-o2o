package com.ihomefnt.o2o.intf.manager.constant.activity;

/**
 * 发表结果
 * @author ZHAO
 */
public enum HomeLetterPublishEnum {
	SUCCESS(1L, 1L, "发表成功，快分享给您的朋友吧！"),FILAED(2L, 2L, "发表失败，请重试"),
	ARTICLE_NOTEXITS(3L, 3L, "家书暂未发表，我们期待重新投稿～"),ARTICLE_VOTE(4L, 4L, "一个微信号只能投稿一次哦，快去让您的朋友给您投票吧～"),
	ACTIVITY_END(5L, 5L, "活动已结束，近期公布活动结果"),PARAM_CONTENT(6L, 6L, "请输入您的三行家书～"),
	PARAM_MOBILE(7L, 7L, "请输入手机号，作为评选后的联系方式^_^"),OPENID_NULL(8L, 8L, "获取不到您的微信号,请尝试重新打开链接");

	private Long code;//后端返回结果
	
	private Long result;//返回给前端的code
	
	private String value;//返回给前端的value

	public static HomeLetterPublishEnum getValue(Long code){
		for(HomeLetterPublishEnum homeLetterVoteEnum : HomeLetterPublishEnum.values()){
			if(homeLetterVoteEnum.getCode().equals(code)){
				return homeLetterVoteEnum;
			}
		}
		return FILAED;
	}
	
	HomeLetterPublishEnum(Long code, Long result, String value){
		this.code = code;
		this.result = result;
		this.value = value;
	}
	
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
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
