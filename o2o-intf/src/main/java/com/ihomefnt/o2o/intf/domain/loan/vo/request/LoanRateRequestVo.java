package com.ihomefnt.o2o.intf.domain.loan.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("根据订单号查询对应爱家贷利率")
@Data
public class LoanRateRequestVo extends HttpBaseRequest {

        @ApiModelProperty("订单号")
        private Integer orderId;
}
