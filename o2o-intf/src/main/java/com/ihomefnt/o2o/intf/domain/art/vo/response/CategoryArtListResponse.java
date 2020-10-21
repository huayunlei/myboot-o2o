package com.ihomefnt.o2o.intf.domain.art.vo.response;

import lombok.Data;

import java.util.List;

/**
 * 艾商城首页分类艺术品集合返回数据
 * @author ZHAO
 */
@Data
public class CategoryArtListResponse {
	private Integer frameType;//框架类型，艺术品显示数量   1、2、3、4
	
	private List<CategoryResponse> categoryList;//分类艺术品集合
}
