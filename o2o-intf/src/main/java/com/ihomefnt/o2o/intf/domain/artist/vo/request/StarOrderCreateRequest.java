package com.ihomefnt.o2o.intf.domain.artist.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("小星星订单创建请求参数")
public class StarOrderCreateRequest extends HttpBaseRequest{

	/**
	 * 小星星订单商品明细
	 */
	@ApiModelProperty("小星星订单商品明细")
	private List<StarOrderDetailCreateRequest> productList; 
	
	@ApiModelProperty("订单合同额")
    private BigDecimal softContractgMoney; //订单合同额

	@ApiModelProperty("订单备注")
    private String remark;
	
	private Boolean shoppingCartPay; //true 购物车内结算
	
}
