package com.ihomefnt.o2o.intf.domain.main.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/7/17
 */
@ApiModel("首页节点内容请求参数")
@Data
public class NodeContentRequest extends HttpBaseRequest {

	@ApiModelProperty("订单号")
	private Integer orderId;

	@ApiModelProperty("当前节点Id")
	private Integer currentNodeId;

	@ApiModelProperty("焦点节点Id")
	private Integer focusNodeId;

	@ApiModelProperty("楼盘项目Id")
	private Integer houseProjectId;

	@ApiModelProperty("户型Id")
	private Integer houseTypeId;

	@ApiModelProperty("版本号标记 1不露出已下线方案 2露出已下线方案")
	private Integer version = 1;
}
