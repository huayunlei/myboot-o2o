package com.ihomefnt.o2o.intf.domain.homecard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="HouseInfoQueryRequest",description="HouseInfoQueryRequest")
public class HouseInfoQueryRequest extends HttpBaseRequest {

	@ApiModelProperty("户型id ")
	private Integer layoutId;
}
