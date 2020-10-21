package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author ZHAO
 */
@Data
@ApiModel("用戶特定方案请求参数")
public class HttpProductProgramRequest extends HttpBaseRequest {
	@ApiModelProperty("楼盘ID")
	private Integer houseProjectId;

	@ApiModelProperty("分区ID")
	private Integer zoneId;
	
	@ApiModelProperty("户型ID")
	private Integer houseTypeId;

	@Deprecated
	@ApiModelProperty("房产ID")
	private Integer houseId;

	@ApiModelProperty("房产id")
	private Integer customerHouseId;

	@ApiModelProperty("方案名称")
	private String solutionName;

}
