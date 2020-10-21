/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年8月14日
 * Description:LogEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.log;

/**
 * @author zhang
 */
public enum LogEnum {

	LOG_ORTHER("其他", 0),

	LOG_HOMECARD("首页推荐版块", 1),

	LOG_HOME_ART("艾商城", 2),

	LOG_HOME_SHAREORDER("生活板块", 3),

	LOG_HOME_CONFIG("我的设置", 4),

	LOG_ADVERT_DOWNLOAD("广告页下载", 5),

	LOG_ADVERT_USE("广告页使用", 6),

	LOG_ADVERT_CLICK("广告页点击", 7),

	LOG_HOMELETTER_CLICK("三行家书活动页", 8),

	LOG_HOMELETTER_INFO_CLICK("三行家书个人主页", 9),

	LOG_GREETING_CLICK("定制贺卡", 10),
	
	LOG_MIRROR_SENTENCE_CLICK("魔镜每日一句点赞", 11),

	LOG_MIRROR_BODYFAT_CLICK("魔镜体脂详情", 12),

	LOG_MIRROR_WEATHER_CLICK("魔镜天气详情", 13),
	
	LOG_KUANIAN_HOME_CLICK("跨年管家首页", 14),
	
	LOG_KUANIAN_SENTENCE_CLICK("跨年管家本地攻略", 15),
	
	LOG_KUANIAN_MSGWALL_CLICK("跨年管家留言墙", 16),
	
	LOG_KUANIAN_PICWALL_CLICK("跨年管家照片墙", 17),
	
	LOG_KUANIAN_TRAVEL_CLICK("跨年管家专属行程", 18),
	
	LOG_KUANIAN_FACE_CLICK("跨年管家最美笑脸", 19),

	LOG_VERSION_UPDATE("版本升级", 20),
	
	LOG_VERSION_ERROR("版本升级出错", 21),
	
	LOG_AGENT_HOME("经纪人首页", 22),
	
	LOG_AGENT_RECOMMEND("经纪人推荐页", 23),
	
	LOG_AGENT_COMMISSION("经纪人佣金页", 24),
	
	LOG_AGENT_CUSTOMER("经纪人客户页", 25),
	
	LOG_AGENT_RULE("经纪人细则页", 26),
	
	LOG_AGENT_RANK("经纪人排行榜", 27),

	LOG_MIRROR_MUSIC_PLAY("魔镜音乐播放", 28),

	LOG_MIRROR_WEATHER_PLAY("魔镜天气播报", 29),

	LOG_AGENT_INTRODUCE("经纪人全品家", 30);

	private String msg;

	private int code;

	private LogEnum(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(int code) {
		for (LogEnum c : LogEnum.values()) {
			if (c.getCode() == code) {
				return c.msg;
			}
		}
		return LOG_ORTHER.getMsg();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
