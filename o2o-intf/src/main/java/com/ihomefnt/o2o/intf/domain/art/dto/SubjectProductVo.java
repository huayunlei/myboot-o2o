package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 专题商品
 * @author ZHAO
 */
@Data
public class SubjectProductVo {
	private Integer productId;//艺术品ID

	private String productName;//艺术品名称
	
	private BigDecimal productPrice;//艺术品价格

	private String title;//标题名称

	private String productDesc;//艺术品描述

	private String imgUrl;//艺术品图片
	
	private String brandArtist;//品牌、艺术家
}
