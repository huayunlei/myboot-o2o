package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(description = "特定用户资格校验结果返回")
public class SpecificUserDecisionResultDto {
	@ApiModelProperty(value = "不可看列表")
	private List<SpecificUserResultBaseDto> noViewDtoList;

	@ApiModelProperty(value = "可看列表")
	private List<SpecificUserResultBaseDto> viewDtoList;

	@ApiModelProperty(value = "可下单列表")
	private List<SpecificUserResultBaseDto> orderDtoList;

}
