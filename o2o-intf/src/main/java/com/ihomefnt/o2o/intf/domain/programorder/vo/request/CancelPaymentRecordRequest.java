package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("取消支付流水记录请求参数")
public class CancelPaymentRecordRequest extends HttpBaseRequest{
	@ApiModelProperty("订单ID")
    private Integer orderId;

    @ApiModelProperty("支付类型")
    private Integer payType;

    @ApiModelProperty("支付结果编号")
    private String resultCode;
}
