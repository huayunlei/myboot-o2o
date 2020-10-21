package com.ihomefnt.o2o.intf.domain.loan.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 */
@Data
@ApiModel("订单号查询信息")
public class LoanListRequestVo extends HttpBaseRequest {

    @ApiModelProperty("订单号")
    private Long orderId;
}
