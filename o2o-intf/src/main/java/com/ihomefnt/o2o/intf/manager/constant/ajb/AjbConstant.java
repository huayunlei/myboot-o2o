/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月8日
 * Description:AjbConstant.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.ajb;

/**
 * @author zhang
 */
public interface AjbConstant {
	
	String SYS_ADMIN_MOBILE_KEY = "wcmSysAdmin"; // 系统管理员手机号码编码

	String SYS_ADMIN_MOBILE_DEFAULT = "18888888002"; // 系统管理员手机号码

	int STICKY_POST_YES = 1;// 精华贴标识为精华

	String SHARE_ORDER_CODE = "3";// 发表晒家奖励艾积分的活动编码

	Integer SHARE_ORDER_MONEY = 200;// 发表晒家奖励艾积分的奖励200艾积分

	Integer Share_ORDER_TIMES = 20;// ;//发表晒家奖励艾积分的奖励20次

	int ACTIVITY_OVER = -1;// 活动过期标识

	String AJB_TITLE = "发表晒家奖励艾积分";

	String AJB_CONTENT = "您发表的晒家被置为精华，奖励的200个艾积分已到账啦，点击查看";

	Long NOTICE_SUB_TYPE_AJB = 13L;

	int SEND_RESULT_FAIL = -1;

}
