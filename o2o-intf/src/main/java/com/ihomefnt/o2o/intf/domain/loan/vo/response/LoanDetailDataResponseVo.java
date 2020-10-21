package com.ihomefnt.o2o.intf.domain.loan.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 */
@Data
@ApiModel("贷款信息")
public class LoanDetailDataResponseVo {

    @ApiModelProperty("贷款id")
    private Long loanId;

    @ApiModelProperty("贷款状态")
    private Integer loanStatus;

    @ApiModelProperty("贷款状态字符串")
    private String loanStatusStr;

    @ApiModelProperty("取消原因")
    private String cancelReason;

    @ApiModelProperty("置家顾问信息")
    private HomeConsultantResponseVo homeConsultant;

    @ApiModelProperty("款项类型")
    private String loanType;

    @ApiModelProperty("贷款银行")
    private String bank;

    @ApiModelProperty("贷款金额")
    private BigDecimal loanMoney;

    @ApiModelProperty("贷款年限")
    private Integer year;

    @ApiModelProperty("贷款利率")
    private Float interestRate;

    @ApiModelProperty("申请日期")
    private String applyTime;

    @ApiModelProperty("取消日期")
    private String cancelTime;

    @ApiModelProperty("订单号")
    private Long orderId;

    @ApiModelProperty("银行审批通过时间")
    private String entertainTime;

    @ApiModelProperty("入账日期")
    private String accountedTime;

    @ApiModelProperty("是否可以重新申请")
    private Boolean reApply;

    @ApiModelProperty("贷款申请人姓名")
    private String applyer;

    @ApiModelProperty("贷款申请人身份证号")
    private String applyerIdNum;

    @ApiModelProperty("入账金额")
    private BigDecimal accountedAmount;
}
