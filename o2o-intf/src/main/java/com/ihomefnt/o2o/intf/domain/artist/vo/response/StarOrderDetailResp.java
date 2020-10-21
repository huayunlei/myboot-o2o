package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 小星星订单商品详情
 * Author: ZHAO
 * Date: 2018年10月14日
 */
@Data
public class StarOrderDetailResp {
	private Integer fidProduct;//商品ID  == SKUID
	
	private Integer productAmount;//商品数量
	
	private Boolean commentResultTag;//是否已评价 的 true表示已评价
	
	private BigDecimal productAmountPrice;//商品总价
	
	private String productName;//商品名称
	
	private String headImage;//商品头图
	
	private BigDecimal productPrice;//商品单价
	
	private String selectAttr;//用户选择属性
	
	private String markWord;// 配文信息

	private String goodName;//SKU对应商品名称
	
	private Integer goodId;//SKU对应商品ID

	private Integer originalWorkId;//原始作品ID

	private String originalWorkName;//原始作品名称
}
