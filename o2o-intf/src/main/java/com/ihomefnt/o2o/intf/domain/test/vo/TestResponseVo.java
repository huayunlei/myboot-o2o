package com.ihomefnt.o2o.intf.domain.test.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Data
@ApiModel("TestResponsevo")
public class TestResponseVo {
	
	@ApiModelProperty("name")
	private String name;

}
