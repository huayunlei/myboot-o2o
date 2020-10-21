package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liyonggang
 * @create 2019-03-28 16:13
 */
@Data
public class CreateOrderAndLoanRequest extends HttpBaseRequest {

    @ApiModelProperty("订单编号 必传")
    private Integer orderNum;

    @ApiModelProperty("贷款金额 必传")
    private BigDecimal amount;

    @ApiModelProperty("贷款年限 必传")
    private Integer loanYears;

    @ApiModelProperty("贷款银行 必传")
    private Integer bankId;

    @ApiModelProperty("贷款申请人姓名")
    private String applyer;

    @ApiModelProperty("贷款申请人身份证号")
    private String applyerIdNum;

    @ApiModelProperty("房产id")
    private Integer houseId;
}
