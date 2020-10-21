package com.ihomefnt.o2o.intf.domain.maintain.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 判断正在处理中的报修记录
 * @author ZHAO
 */
@Data
@ApiModel("判断请求参数")
public class MaintainJudgeRequestVo extends HttpBaseRequest{
	@ApiModelProperty("订单ID")
	private Integer orderId;
}
