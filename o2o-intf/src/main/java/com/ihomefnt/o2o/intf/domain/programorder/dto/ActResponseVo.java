/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月19日
 * Description:ActResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 活动实体类
 * 
 * @author zhang
 */
@Data
public class ActResponseVo {

	private Integer actCode;// 活动ID

	private String actName;// 活动名称

	private Integer actType;// 活动类型 1：满减

	private Integer actStatus;// 活动状态 1:新建2:上线3:暂停 4:结束

	private String actDesc;// 活动文本描述

	private Date startTime;// 活动开始时间

	private Date endTime;// 活动结束时间

	private BigDecimal promotionAmount;// 优惠金额
	
	private String rewardRuleDesc;// 活动奖励规则描述

	private List<Integer> mutexPromotions;// 与本活动互斥的活动ID

	private Integer isSuccessJoin;// 是否成功参加:0:否，1:是

}
