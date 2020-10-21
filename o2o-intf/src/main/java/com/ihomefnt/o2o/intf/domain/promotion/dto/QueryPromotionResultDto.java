package com.ihomefnt.o2o.intf.domain.promotion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 28360 on 2017/9/18.
 */
@Data
@ApiModel("查询有效活动返回")
public class QueryPromotionResultDto {

	@ApiModelProperty("已参加活动列表")
	private List<AppQueryActResultDto> joinedActs;

	@ApiModelProperty("可参加活动列表")
	private List<AppQueryActResultDto> canJoinActs;

	@ApiModelProperty("不可参加活动列表")
	private List<AppQueryActResultDto> canNotJoinActs;
}
