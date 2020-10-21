package com.ihomefnt.o2o.intf.manager.constant.activity;

/**
 * 投票结果
 * @author ZHAO
 */
public enum HomeLetterVoteEnum {
	SUCCESS(1L, 1L, "您已为TA投上了宝贵一票～"),FILAED(2L, 2L, "投票失败，请重试"),
	ARTICLE_NOTEXITS(3L, 3L, "家书暂未发表，换一篇看看吧～"),ARTICLE_VOTE(4L, 4L, "一天只能给TA投一次哦，请明日再来"),
	ACTIVITY_END(5L, 5L, "活动已结束，近期公布活动结果"),OPENID_NULL(6L, 6L, "获取不到您的微信号，请尝试重新打开链接")
	,ORTHER_NULL(7L, 7L, "您的微信信息异常，请尝试重新打开链接");

	private Long code;//后端返回结果
	
	private Long result;//返回给前端的code
	
	private String value;//返回给前端的value

	public static HomeLetterVoteEnum getValue(Long code){
		for(HomeLetterVoteEnum homeLetterVoteEnum : HomeLetterVoteEnum.values()){
			if(homeLetterVoteEnum.getCode().equals(code)){
				return homeLetterVoteEnum;
			}
		}
		return FILAED;
	}
	
	HomeLetterVoteEnum(Long code, Long result, String value){
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
