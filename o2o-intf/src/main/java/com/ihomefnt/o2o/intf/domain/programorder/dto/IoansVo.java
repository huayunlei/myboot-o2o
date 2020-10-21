package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liyonggang
 * @create 2018-11-05 18:06
 */
@Data
@ApiModel("艾家贷详情")
public class IoansVo implements Serializable {
    private static final long serialVersionUID = -4709928613484434604L;
    @ApiModelProperty("贷款id")
    private Long loanId;
    @ApiModelProperty("订单编号")
    private Long orderNum;
    @ApiModelProperty("实际入账金额")
    private BigDecimal accountedAmount;
    @ApiModelProperty("申请贷款金额")
    private BigDecimal amount ;
    @ApiModelProperty("申请贷款时间")
    private String applyTimeStr ;
    @ApiModelProperty("贷款银行")
    private Long bankId  ;
    @ApiModelProperty("贷款银行名称")
    private String bankName  ;
    @ApiModelProperty("贷款状态")
    private Long loanStatus   ;
    @ApiModelProperty("贷款年限")
    private Long loanYears    ;
    @ApiModelProperty("年利率")
    private Number annualRate     ;
    @ApiModelProperty("贷款手续费")
    private BigDecimal poundage      ;
    @ApiModelProperty("贷款入账时间")
    private String accountedTimeStr;
    @ApiModelProperty("贷款状态字符串")
    private String loanStatusStr;
}
