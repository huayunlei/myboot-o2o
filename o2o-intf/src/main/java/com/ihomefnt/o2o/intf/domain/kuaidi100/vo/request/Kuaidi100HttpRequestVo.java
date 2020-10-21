package com.ihomefnt.o2o.intf.domain.kuaidi100.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("快递100参数")
@Data
public class Kuaidi100HttpRequestVo {

	@ApiModelProperty("运输公司编码")
	@NotNull(message = "运输公司编码不能为空")
	private String logisticCompanyCode; //物流公司编码

	@ApiModelProperty("快递单号")
	@NotNull(message = "快递单号")
	private String logisticNum; //物流单号

	@ApiModelProperty("收获人手机号，顺丰快递必填")
	private String phone;
}
