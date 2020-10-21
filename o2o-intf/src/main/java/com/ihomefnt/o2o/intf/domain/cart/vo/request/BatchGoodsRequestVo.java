package com.ihomefnt.o2o.intf.domain.cart.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 
 * @author Charl
 */
@Data
@ApiModel("购物车中商品记录请求")
public class BatchGoodsRequestVo extends HttpBaseRequest {
	
	@ApiModelProperty("多个购物商品记录ID（注意需传入list）")
    @NotNull(message = "购物商品记录ID不能为空")
    public List<Integer> recordIds;
}
