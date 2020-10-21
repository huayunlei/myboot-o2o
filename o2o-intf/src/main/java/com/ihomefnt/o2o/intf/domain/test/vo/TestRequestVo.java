package com.ihomefnt.o2o.intf.domain.test.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Data
@ApiModel("Test请求vo")
public class TestRequestVo {
	
	@ApiModelProperty("id")
	private Long id;

}
