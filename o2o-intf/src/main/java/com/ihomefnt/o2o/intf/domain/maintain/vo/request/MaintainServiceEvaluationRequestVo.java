package com.ihomefnt.o2o.intf.domain.maintain.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务评价请求参数
 * @author ZHAO
 */
@Data
@ApiModel("服务评价请求参数")
public class MaintainServiceEvaluationRequestVo extends HttpBaseRequest{

	@ApiModelProperty("报修任务ID")
	private Integer taskId;

	@ApiModelProperty("总体满意度")
	private Integer overallSatisfactionStar;
	
	@ApiModelProperty("客服人员服务星级")
	private Integer customerServiceStar;
	
	@ApiModelProperty("维修人员服务星级")
	private Integer maintainServiceStar;
	
	@ApiModelProperty("维修工期满意度")
	private Integer maintainTimeStar;
	
	@ApiModelProperty("维修质量满意度")
	private Integer maintainQualityStar;

	@ApiModelProperty("评价内容")
	private String evaluationContent;
}
