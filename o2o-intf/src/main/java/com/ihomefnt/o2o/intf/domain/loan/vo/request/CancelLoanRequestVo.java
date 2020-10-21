package com.ihomefnt.o2o.intf.domain.loan.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/6/21
 */
@Data
@ApiModel("爱家贷-取消爱家贷")
public class CancelLoanRequestVo extends HttpBaseRequest {

    @ApiModelProperty("艾佳贷id")
    private Integer loanId;

    @ApiModelProperty("原因id")
    private Integer reasonId;

    @ApiModelProperty("原因")
    private String reason;
}
