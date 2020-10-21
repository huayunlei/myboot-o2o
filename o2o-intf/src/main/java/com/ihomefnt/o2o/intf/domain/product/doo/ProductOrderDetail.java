package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.util.List;
@Data
public class ProductOrderDetail {

	 private Long productId;//商品id
	 private String name;//商品名称
	 private List<String> pictureUrlOriginal;//商品图片
	 private String pictureUrls;//商品图片
	 private Double productPrice;//当前价格
	 private Integer  productCount;//商品的数量
	 private String deliveryTime;
}
