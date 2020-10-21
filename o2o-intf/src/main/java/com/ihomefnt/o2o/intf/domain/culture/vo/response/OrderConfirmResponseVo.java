package com.ihomefnt.o2o.intf.domain.culture.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@ApiModel("订单确认Vo")
public class OrderConfirmResponseVo {
	
	@ApiModelProperty("商品id")
	private Integer itemId;
	
	@ApiModelProperty("商品名称")
	private String itemName;
	
	@ApiModelProperty("商品头图")
	private String itemHeadImg;
	
	@ApiModelProperty("商品库存")
	private Integer stock;
	
	@ApiModelProperty("商品售价")
	private BigDecimal itemSellPrice;
	
	@ApiModelProperty("商品原价（暂不处理）")
	private BigDecimal itemOriginPrice;
	
	@ApiModelProperty("可用艾积分数量")
	private Integer ajbAccount; //艾积分可用的总额
	
	@ApiModelProperty("可用艾积分抵扣人民币金额")
	private BigDecimal ajbMoney; //艾积分换算成人民币的金额
	
	@ApiModelProperty("可用艾积分抵换多少次")
	private Integer ajbTime;
	
	@ApiModelProperty("艾积分抵用总次数")
	private Integer ajbTotalTime;
	
	@ApiModelProperty("艾积分转换成人民币的汇率")
	private Integer exRate;
}
