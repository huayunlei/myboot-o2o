package com.ihomefnt.o2o.intf.domain.style.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("风格问题答案集")
public class StyleQuestionAnwserStepResponse {
	
	@ApiModelProperty("步骤")
	private Integer step;//步骤
	
	@ApiModelProperty("问题答案集")
	private List<StyleQuestionAndAnwserResponse> questionList;// 问题答案集

}
