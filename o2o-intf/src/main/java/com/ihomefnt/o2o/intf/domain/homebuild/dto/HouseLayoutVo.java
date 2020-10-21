package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@ApiModel("方案户型信息")
@Data
public class HouseLayoutVo implements Serializable {

	@ApiModelProperty("户型图")
	private String apartmentUrl;

	@ApiModelProperty("空间标示图")
	private String apartmentMarkUrl;

	@ApiModelProperty("面积")
	private BigDecimal area;

	@ApiModelProperty("厅")
	private Integer hall;

	@ApiModelProperty("卧室")
	private Integer chamber;

	@ApiModelProperty("厨房")
	private Integer kitchen;

	@ApiModelProperty("阳台")
	private Integer balcony;

	@ApiModelProperty("卫")
	private Integer toilet;

	@ApiModelProperty("储藏间")
	private Integer storage;

	@ApiModelProperty("衣帽间")
	private Integer cloak;

	@ApiModelProperty("户型位置：0东西通用、1东边户、2西边户")
	private Integer location;

}
