package com.ihomefnt.o2o.intf.domain.right.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("权益弹窗参数")
public class OrderRightPopupRequest extends HttpBaseRequest {

	@ApiModelProperty("订单编号")
    private Integer orderNum;
	
	@ApiModelProperty("等级id")
    private Integer gradeId;

}
