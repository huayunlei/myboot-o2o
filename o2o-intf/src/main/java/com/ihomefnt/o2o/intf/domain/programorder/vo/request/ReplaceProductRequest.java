package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @author ZHAO
 */
@ApiModel("产品方案-空间商品信息参数")
@Accessors(chain = true)
@Data
public class ReplaceProductRequest {
	@ApiModelProperty("原商品SkuId")
	private Integer skuId;
	
	@ApiModelProperty("新商品SkuId")
	private Integer newSkuId;
	
	@ApiModelProperty("家具类型")
	private Integer furnitureType;


}
