package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 产品方案增配包信息
 * @author ZHAO
 */
@Data
public class SolutionAddBagInfoVo implements Serializable {
	
	private String categoryName;
	
	private Integer skuId;//skuId
	
	private String skuHeadImgURL;//SKU头图地址
	
	private String skuName;//SKU名称
	
	private Integer skuCount;//SKU数量
	
	private BigDecimal skuUnitPrice;//SKU单价
	
	private Integer type ;// 增配包类型：0.软装 1.硬装

	private String desc;//商品描述
	
}
