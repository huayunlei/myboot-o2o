package com.ihomefnt.o2o.intf.domain.right.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by acer on 2018/9/5.
 */
@Data
@ApiModel("CheckOrderRightsResponseVo")
public class CheckOrderRightsResponseVo {

	@ApiModelProperty("是否成功")
	private boolean hope = true;

	@ApiModelProperty("0-成功 1-互斥 2-时间过期 3-活动失效 4:订单类型不符仅支持全品家订单")
	private Integer code = 0;
}
