/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年3月4日
 * Description:PageResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.shareorder.dto;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
	/**
	 * 返回的主体数据
	 */
	private List<T> list = Lists.newArrayList();
	/**
	 * 当前页码
	 */
	private int pageNo = 1;
	/**
	 * 每页的数据条数
	 */
	private int pageSize = 10;
	/**
	 * 总记录数
	 */
	private int totalCount = 0;

	/**
	 * 总页数
	 */
	private int totalPage = 1;
}
