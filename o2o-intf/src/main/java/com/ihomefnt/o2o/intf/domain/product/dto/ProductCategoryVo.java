package com.ihomefnt.o2o.intf.domain.product.dto;

import lombok.Data;

@Data
public class ProductCategoryVo {
	
	private Integer typeOneId ;// 一级类目ID,
	private String typeOneName;// 一级类目名称,
	private Integer typeTwoId;// 二级类目ID,
	private String typeTwoName;// 二级类目名称,
	private Integer typeThreeId;// 三级类目ID,
	private String typeThreeName;// 三级类目名称,
	private Integer typeFourId;// 四级类目ID,
	private String typeFourName;// 四级类目名称,
	private Integer categoryId;// 分类id,
	private String categoryName;// 分类名称,
	private Integer firstCategoryId;// 
	private Integer secondCategoryId;// 
	private Integer thirdCategoryId;// 
	private Integer fourthCategoryId;// 
	private String firstCategoryName;// 
	private String secondCategoryName;// 
	private String thirdCategoryName;// 
	private String fourthCategoryName;// 
}
