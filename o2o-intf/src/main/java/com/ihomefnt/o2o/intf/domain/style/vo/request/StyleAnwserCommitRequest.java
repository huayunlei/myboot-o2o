package com.ihomefnt.o2o.intf.domain.style.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("风格答案对象")
@Data
public class StyleAnwserCommitRequest {
	@ApiModelProperty("风格答案id")
	private Integer anwserId;
	
	@ApiModelProperty("风格答案信息")
	private String anwserContent;
}
