/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月19日
 * Description:ActPageResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.util.List;

/**
 * 活动列表
 * 
 * @author zhang
 */
@Data
public class ActPageResponseVo {

	private List<ActResponseVo> joinedActs;// 已参加活动列表

	private List<ActResponseVo> canJoinActs;// 可参加活动列表

}
