package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "艺术家入参")
public class HttpArtistHomeRequest extends HttpBaseRequest {

	@ApiModelProperty(value = "艺术家id")
	private Long artistId;

	
}
