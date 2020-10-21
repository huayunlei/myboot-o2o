package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("货物包裹")
public class CargoPackageVo {
	
	@ApiModelProperty("主键id")
	private Integer id; //(integer, optional),
	@ApiModelProperty("货物包裹id")
	private Integer cargoId; //(integer, optional),
	@ApiModelProperty("货物包裹名称")
	private String name; //(string, optional),
	@ApiModelProperty("包裹体积")
	private String packingVolume; //(string, optional),
	@ApiModelProperty("创建时间")
	private String createTime; //(string, optional),
	@ApiModelProperty("更新时间")
	private String updateTime; //(string, optional)
	
}
