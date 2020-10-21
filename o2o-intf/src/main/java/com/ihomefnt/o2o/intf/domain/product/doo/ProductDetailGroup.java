package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.util.List;
@Data
public class ProductDetailGroup {
	private String groupName;//组名
	private List<ProductDetail> details;//具体属性
}
