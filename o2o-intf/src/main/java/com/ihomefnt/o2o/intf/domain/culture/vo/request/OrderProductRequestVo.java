package com.ihomefnt.o2o.intf.domain.culture.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单商品Vo
 * @author Charl
 */
@Data
@ApiModel("订单商品Vo")
public class OrderProductRequestVo {
	
	@ApiModelProperty("商品id")
	private Integer productId;
	
	@ApiModelProperty("商品數量")
	private Integer productCount;
	
	@ApiModelProperty("商品單價（售價）")
	private BigDecimal productPrice;
	 
	@ApiModelProperty("是否為樣品：1.样品 0.非样品（默认）")
	private Integer sample;
	   
	@ApiModelProperty("商品备注")
	private String remark;
}
