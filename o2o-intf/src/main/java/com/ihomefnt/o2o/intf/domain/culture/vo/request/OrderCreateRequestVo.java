package com.ihomefnt.o2o.intf.domain.culture.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author Charl
 */
@Data
@ApiModel("商品创建订单Vo")
public class OrderCreateRequestVo extends HttpBaseRequest {
	

	@ApiModelProperty("下单商品列表")
	@NotNull(message="下单商品不能为空")
	private List<OrderProductRequestVo> productList;
	
	@ApiModelProperty("订单合同额（总价）")
	@NotNull(message="订单总价不能为空")
	private BigDecimal contractMoney; // 订单合同额
	
	@ApiModelProperty("使用艾积分的数量")
	private Integer ajbAmount;// 使用的艾积分数量

	@ApiModelProperty("艾积分全额支付：true 是， false 否")
	private Boolean allPay; // true 艾积分全额支付 false
}
