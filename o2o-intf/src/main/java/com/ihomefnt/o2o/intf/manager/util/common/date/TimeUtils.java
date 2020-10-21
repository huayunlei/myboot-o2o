/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月20日
 * Description:TimeUtils.java 
 */
package com.ihomefnt.o2o.intf.manager.util.common.date;

/**
 * @author zhang
 */
public class TimeUtils {

	public static String getLeftTime(long diff) {
		long nd = 1000L * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000L * 60 * 60;// 一小时的毫秒数
		long nm = 1000L * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long day = diff / nd;// 计算差多少天
		long hour = diff % nd / nh;// 计算相差剩余多少小时
		long min = diff % nd % nh / nm;// 计算差多少分钟
		long sec = diff % nd % nh % nm / ns;// 计算差多少秒
		StringBuffer str = new StringBuffer();
		if (day > 0) {
			str.append(day);
			str.append("天");
			str.append(hour);
			str.append("小时");
			str.append(min);
			str.append("分钟");
			str.append(sec);
			str.append("秒");
		} else {
			if (hour > 0) {
				str.append(hour);
				str.append("小时");
				str.append(min);
				str.append("分钟");
				str.append(sec);
				str.append("秒");
			} else {
				if (min > 0) {
					str.append(min);
					str.append("分钟");
					str.append(sec);
					str.append("秒");
				} else {
					str.append(sec);
					str.append("秒");
				}

			}
		}
		return str.toString();
	}

}
