package com.ihomefnt.o2o.intf.domain.art.vo.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HttpArtworkOrderResponse {

	private long artworkId;  //艺术品id
	
	private String name;  //艺术品名称
	
	private BigDecimal price;  //艺术品单价
	
	private long stock; //艺术品库存
	
	private String headImg;  //艺术品头图
	
	private Integer ajbAccount; //艾积分可用的总额
	
	private BigDecimal ajbMoney; //艾积分换算成人民币的金额
	
	private Integer exRate; //艾积分换算成人民币的汇率

	private String productId;//新版本艾商城商品id
	
}
