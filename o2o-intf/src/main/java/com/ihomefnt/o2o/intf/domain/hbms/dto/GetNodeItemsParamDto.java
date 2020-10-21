package com.ihomefnt.o2o.intf.domain.hbms.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author wangjin
 *
 */
@Data
@ApiModel(description = "节点验收项请求参数信息")
public class GetNodeItemsParamDto extends HttpBaseRequest{

	@ApiModelProperty(value = "节点id")
	private String nodeId;

	@ApiModelProperty(value = "项目id")
	private String projectId;
}
