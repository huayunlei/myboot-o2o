package com.ihomefnt.o2o.intf.domain.right.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("权益弹窗-response")
@Data
public class OrderRightPopupResponse {

	@ApiModelProperty("订单编号")
    private Integer orderNum;
	
	@ApiModelProperty("等级id")
    private Integer gradeId;
	
	@ApiModelProperty("弹窗标识-1弹窗，0不弹窗")
    private Integer popupFlag;

}
