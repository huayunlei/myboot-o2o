/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月20日
 * Description:PromotionComparator.java 
 */
package com.ihomefnt.o2o.intf.manager.util.programorder;

import java.util.Comparator;
import java.util.Date;

import com.ihomefnt.o2o.intf.domain.promotion.vo.response.PromotionResponse;

/**
 * @author zhang
 */
public class PromotionComparator implements Comparator<PromotionResponse> {

	public int compare(PromotionResponse o1, PromotionResponse o2) {
		Integer status1 = o1.getStatus();
		Integer status2 = o2.getStatus();
		Date startTime1 = o1.getStartTime();
		Date startTime2 = o2.getStartTime();
		Date endTime1 = o1.getEndTime();
		Date endTime2 = o2.getEndTime();
		if (status1 > status2) {
			return 1;
		} else if (status1 < status2) {
			return -1;
		} else {
			if (status1 == 1 || status1 == 2) {
				return endTime1.compareTo(endTime2);
			} else if (status1 == 3) {
				return startTime1.compareTo(startTime2);
			}
			return 0;
		}
	}

}
