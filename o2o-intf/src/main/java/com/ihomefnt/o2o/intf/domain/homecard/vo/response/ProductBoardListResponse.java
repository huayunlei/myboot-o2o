package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * APP3.0新版首页产品版块数据集合返回值
 * @author ZHAO
 */
@Data
public class ProductBoardListResponse {
	private List<ProductBoardResponse> productList;//产品集合
	
	private Integer pageNo = 1;//当前第几页
	
	private Integer pageSize = 10;//每页显示多少条
	
	private Integer totalCount = 0;//总共多少条
	
	private Integer totalPage = 0;//总共多少页
}
