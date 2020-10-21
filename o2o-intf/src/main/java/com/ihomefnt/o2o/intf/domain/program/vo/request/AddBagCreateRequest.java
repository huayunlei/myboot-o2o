package com.ihomefnt.o2o.intf.domain.program.vo.request;

import lombok.Data;

@Data
public class AddBagCreateRequest {
	private Integer skuId;//商品SkuId
	
	private Integer skuCount;//商品数量
}
