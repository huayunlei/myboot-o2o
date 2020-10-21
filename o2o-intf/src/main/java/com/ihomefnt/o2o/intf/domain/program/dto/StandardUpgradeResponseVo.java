/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月3日
 * Description:StandardUpgradeResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class StandardUpgradeResponseVo implements Serializable{

	private Integer spuId;// 标准升级项spuId

	private String name;// 标准升级项商品名称

	private Integer categoryId;// 类目id

	private String categoryName;// 类目名称

	private List<String> usableRooms;// 适用空间名称（空间标识对应的名称不是空间用途）
	
	private String roomDesc ="";//所属空间描述

	private List<StandardUpgradeSkuResponseVo> skuInfos;// 标准升级项sku信息

	public String getRoomDesc() {
		if (CollectionUtils.isNotEmpty(usableRooms)) {
			int i = 0;
			roomDesc += "此项升级包含:";
			for (String room : usableRooms) {
				i++;
				roomDesc += room;
				if (usableRooms.size() > i) {
					roomDesc += "、";
				}	            
			}				
		}
		return roomDesc;
	}
}
