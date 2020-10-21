package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

/**
 * 商品信息
 * @author ZHAO
 */
@Data
public class ItemInfo {
	private String category;//类别

	private String name;//名称

	private Integer quantity;//数量

	private String measure;//计量单位

	private String imgUrl;//图片URL

	private String itemDesciption;//描述

	private String unit;//计量单位

	public ItemInfo() {
		this.category = "";
		this.name = "";
		this.quantity = 0;
		this.measure = "";
		this.imgUrl = "";
		this.itemDesciption = "";
		this.unit = "";
	}

}
