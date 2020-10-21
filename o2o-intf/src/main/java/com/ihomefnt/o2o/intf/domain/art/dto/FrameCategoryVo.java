package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

/**
 * 艺术品样式
 * @author ZHAO
 */
@Data
public class FrameCategoryVo {
	private Integer id;
	
	private String frameName;//样式名称
	
	private Integer categoryNum;//品类数量
	
	private Integer sortBy;//排序
}
