package com.ihomefnt.o2o.intf.domain.hbms.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "监理端项目工艺列表")
public class GetSurveyorProjectCraftReponse {
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@ApiModelProperty(value = "开工时间")
	private Date beginTime;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	@ApiModelProperty(value = "是否完成 0-否 1-是")
	private String isFinish;

	@ApiModelProperty(value = "工艺名称")
	private String name;

}
