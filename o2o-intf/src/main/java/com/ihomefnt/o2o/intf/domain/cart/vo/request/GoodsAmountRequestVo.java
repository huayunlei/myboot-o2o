package com.ihomefnt.o2o.intf.domain.cart.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 购物车增加商品数量
 * @author Charl
 */
@Data
@ApiModel("增加商品数量请求参数")
public class GoodsAmountRequestVo extends HttpBaseRequest {
	
	@ApiModelProperty("购物商品记录ID")
	@NotNull(message = "购物商品记录ID不能为空")
	public Integer recordId;

}
