/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月12日
 * Description:OrderInfoSearchDto.java
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @author zhang
 */
@Data
public class OrderInfoSearchDto {

	// 每页多少条数据
	@NotBlank(message = "pageSize不能为空")
	private int pageSize;

	// 第几页
	@NotBlank(message = "pageNo不能为空")
	private int pageNo;

	// 用户ID
	private Integer userId;

	// 订单类型
	private Integer orderType;

	// 订单状态,总状态
	private Integer state;

	/**
	 * 顾客电话
	 */
	private String customerTel;

	/**
	 * 顾客电话模糊
	 */
	private String customerTelLike;

	// 订单类型
	private List<Integer> orderTypes;

	// 订单状态,总状态
	private List<Integer> states;

	// 排序
	private String orderBy;
	//订单是否有效 0-否 1-是
	private Integer mark;

	public OrderInfoSearchDto() {
	}

	public OrderInfoSearchDto(int pageSize,int pageNo, Integer userId, Integer mark) {
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.userId = userId;
		this.mark = mark;
	}
}
