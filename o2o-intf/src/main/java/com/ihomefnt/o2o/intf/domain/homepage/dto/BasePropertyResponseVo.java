package com.ihomefnt.o2o.intf.domain.homepage.dto;

import lombok.Data;

/**
 * 首页产品版块筛选条件
 * @author ZHAO
 */
@Data
public class BasePropertyResponseVo {
	private Integer propertyType;//属性类型:1系列 2风格 3空间标识 4空间用途
	
	private Integer propertyId;// 属性id
	
	private String propertyName;//属性名称

	private Integer roomClassifyType;//空间分类类型(1厅2室3厨4卫5阳台6储藏间7衣帽间)

}
