package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询可选软装请求参数")
public class OptionalSoftRequest extends HttpBaseRequest{
	@ApiModelProperty("空间Id")
	private Integer roomId;
	
	@ApiModelProperty("商品Id")
	private Integer skuId;
}
