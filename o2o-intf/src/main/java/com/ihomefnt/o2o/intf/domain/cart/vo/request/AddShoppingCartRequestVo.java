package com.ihomefnt.o2o.intf.domain.cart.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加购物车request
 * @author Charl
 */
@ApiModel(value="添加购物车Request",description="添加购物车请求")
@Data
public class AddShoppingCartRequestVo extends HttpBaseRequest{
	
	@ApiModelProperty("商品id")
    @NotNull(message = "商品id不为空 艾商城skuid或者艺术品规格id specificationId")
	private String goodsId;
	
	@ApiModelProperty("商品类型")
	private int goodsType;

	@ApiModelProperty("商品数量 默认为1")
	private Integer goodsAmount =1;

}
