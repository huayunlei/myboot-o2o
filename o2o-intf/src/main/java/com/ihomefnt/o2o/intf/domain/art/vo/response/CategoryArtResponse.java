package com.ihomefnt.o2o.intf.domain.art.vo.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 艾商城首页分类艺术品信息
 * @author ZHAO
 */
@Data
public class CategoryArtResponse {
	private Integer artId;//艺术品ID
	
	private String artName;//艺术品名称
	
	private String headImgUrl;//艺术品头图
	
	private List<Integer> categoryList;//品类标签集合

	private BigDecimal price;//价格
	
	private String author;//作者
}
