package com.ihomefnt.o2o.intf.domain.homebuild.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询房产详情信息请求参数")
public class BuildingSearchRequest extends HttpBaseRequest{

	@ApiModelProperty("房产ID 废弃，新版本使用 customerHouseId")
	@Deprecated
	private Integer houseId;

	@ApiModelProperty("1查询所有  2过滤已取消的")
	private Integer type;

	@ApiModelProperty("是否查询经纪人房产信息 true(查询))")
	private boolean queryAgentHouseInfo;
	@ApiModelProperty("房产id")
	private Integer customerHouseId;
}
