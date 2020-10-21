package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询空间方案请求参数
 * @author ZHAO
 */
@Data
@ApiModel("空间查询请求参数")
public class HttpSpaceProgramRequest extends HttpBaseRequest{
	@ApiModelProperty("楼盘ID")
	private Integer houseProjectId;
	
	@ApiModelProperty("户型ID")
	private Integer houseTypeId;

	@ApiModelProperty("房产ID")
	private Integer houseId;
}
