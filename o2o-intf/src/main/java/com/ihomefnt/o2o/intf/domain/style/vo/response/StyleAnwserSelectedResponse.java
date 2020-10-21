package com.ihomefnt.o2o.intf.domain.style.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("已选问题的答案")
@Data
public class StyleAnwserSelectedResponse {
	
	@ApiModelProperty("风格问题ID")
	private Integer questionId;//风格问题ID
	
	@ApiModelProperty("答案ID")
	private Integer anwserId;//答案ID
	
	@ApiModelProperty("答案内容")
	private String anwserContent;//答案内容
	
	@ApiModelProperty("答案类型 0文本答案  1外部对象答案")
	private Integer anwserType = 0;//答案类型
	
	@ApiModelProperty("外部答案内容（答案类型为1时）")
	private Object obj;//答案内容
}
