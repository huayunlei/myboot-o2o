package com.ihomefnt.o2o.intf.domain.art.vo.response;

import lombok.Data;

import java.util.List;

/**
 * 分类艺术品列表（分页）
 * @author ZHAO
 */
@Data
public class CategoryArtPageResponse {
	private List<CategoryArtResponse> categoryArtList;
	
	private Integer pageNo;//当前第几页
	
	private Integer pageSize;//每页显示多少条
	
	private Integer totalCount;//总条数
	
	private Integer totalPage;//总页数

}
