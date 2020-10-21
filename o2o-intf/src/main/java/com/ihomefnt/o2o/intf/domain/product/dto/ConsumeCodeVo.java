package com.ihomefnt.o2o.intf.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 唯一吗
 * @author Charl
 */
@Data
@ApiModel("唯一码Vo")
public class ConsumeCodeVo {
	
	@ApiModelProperty("主键id")
	@JsonProperty("codeId")
	private Integer id; // (integer, optional),
	
	@ApiModelProperty("订单id")
	private Integer orderId; // (integer, optional): 订单ID,
	
	@ApiModelProperty("商品id")
	private Integer productId; // (integer, optional): 商品ID,
	
	@ApiModelProperty("商户id")
	private Integer sellerId; // (integer, optional): 商户ID,
	
	@ApiModelProperty("用户id")
	private Integer userId; // (integer, optional): 用户ID,
	
	@ApiModelProperty("一维码")
	private String code; // (string, optional): 一维码,
	
	@ApiModelProperty("一维码状态：0：未使用 1：已使用")
	private Integer status; // (integer, optional): 状态（0：未使用 1：已使用）,
	
	@ApiModelProperty("创建时间")
	private String createTime; // (string, optional): 创建时间,
	
	@ApiModelProperty("更新时间")
	private String updateTime; // (string, optional): 更新时间
}
