package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

/**
 * 艺术品品类
 * @author ZHAO
 */
@Data
public class FrameArtVo {
	private Integer frameId;//样式ID
	
	private Integer categoryCode;//类别代码
	
	private String categoryName;//类别名称
	
	private Integer artId;//艺术品ID
	
	private String artName;//艺术品名称
	
	private String categoryTitle;//类别标题
	
	private String artImg;//艺术品图片
	
	private Integer sortBy;//排序
}
