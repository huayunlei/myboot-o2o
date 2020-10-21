package com.ihomefnt.o2o.intf.domain.homebuild.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 根据分区查询楼栋号等信息
 * Author: ZHAO
 * Date: 2018年5月9日
 */
@Data
@ApiModel("根据分区查询楼栋号等信息请求参数")
public class ZoneSearchRequest extends HttpBaseRequest{
	@ApiModelProperty("分区ID")
	private Integer zoneId;
	@ApiModelProperty("楼栋ID")
	private Integer buildingId;
	@ApiModelProperty("单元号")
	private String unitNo;
}
