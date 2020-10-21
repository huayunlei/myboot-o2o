package com.ihomefnt.o2o.intf.domain.style.vo.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("风格问题答案对象")
@Data
public class StyleQuestionAnwserCommitRequest {

	@ApiModelProperty("订单编号")
	private Integer orderNum;
	@ApiModelProperty("用户id")
	private Integer userId;
	@ApiModelProperty("风格问题id")
	private Integer questionId;
	@ApiModelProperty("风格问题答案集")
	private List<StyleAnwserCommitRequest> anwserList;

}
