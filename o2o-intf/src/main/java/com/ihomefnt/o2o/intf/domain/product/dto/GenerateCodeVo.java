package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("生成一维码请求vo")
public class GenerateCodeVo {
	
	@ApiModelProperty("订单id")
	private Integer orderId;
	
	@ApiModelProperty("用户id")
	private Integer userId;
	
	@ApiModelProperty("商品id列表")
	private List<Integer> productIds;

}
