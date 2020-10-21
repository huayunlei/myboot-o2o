package com.ihomefnt.o2o.intf.domain.hbms.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "项目工艺流程节点信息")
public class GetSurveyorProjectNodeResponseVo {
	@ApiModelProperty(value = "节点施工是否满意0-否，1-是,2-待确认")
	private String confirm;
	@ApiModelProperty(value = "节点Id")
	private String nodeId;
	@ApiModelProperty(value = "节点名称")
	private String nodeName;	
	@ApiModelProperty(value = "是否已打款0-否，1-是")
	private String pay;	

	@ApiModelProperty(value = "节点进度")
	private String progress;
	
	@ApiModelProperty(value = "工艺列表")
	private List<GetSurveyorProjectCraftReponse> projectCraftList;

	//5 节点完成
	@ApiModelProperty(value = "状态0-未激活 1-已激活")
	private Integer status;
}
