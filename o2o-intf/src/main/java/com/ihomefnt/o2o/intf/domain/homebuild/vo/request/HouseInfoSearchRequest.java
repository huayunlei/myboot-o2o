package com.ihomefnt.o2o.intf.domain.homebuild.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("HouseInfoSearchRequest")
public class HouseInfoSearchRequest extends HttpBaseRequest{

	@ApiModelProperty("户型ID")
	private Integer layoutId;

}
