package com.ihomefnt.o2o.intf.domain.loan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author xiamingyu
 * @date 2018/6/21
 */
@Data
public class LoanInfoDto {
    @ApiModelProperty("贷款id")
    private Long loanId;

    @ApiModelProperty("贷款状态")
    private Integer frontStatus;

    @ApiModelProperty("贷款状态字符串")
    private String frontStatusStr;

    @ApiModelProperty("置家顾问")
    private Long adviser;

    @ApiModelProperty("置家顾问姓名")
    private String adviserName;

    @ApiModelProperty("置家顾问电话")
    private String adviserMobile;

    @ApiModelProperty("款项类型")
    private Integer paymentType;

    @ApiModelProperty("款项类型字符串")
    private String paymentTypeStr;

    @ApiModelProperty("银行")
    private String bankName;

    @ApiModelProperty("贷款金额")
    private BigDecimal amount;

    @ApiModelProperty("贷款年限")
    private Integer loanYears;

    @ApiModelProperty("年利率")
    private Float annualRate;

    @ApiModelProperty("申请时间字符串")
    private String applyTimeStr;

    @ApiModelProperty("申请时间")
    private Date applyTime;

    @ApiModelProperty("取消时间字符串")
    private String updateTimeStr;

    @ApiModelProperty("取消时间")
    private Date updateTime;

    @ApiModelProperty("取消原因id app现只有2")
    private Integer reasonId;

    @ApiModelProperty("取消原因")
    private String reason;

    @ApiModelProperty("入账日期字符串")
    private String accountedTimeStr;

    @ApiModelProperty("入账日期")
    private Date accountedTime;

    @ApiModelProperty("转为下款中时间字符串")
    private String entertainTimeStr;

    @ApiModelProperty("转为下款中时间")
    private Date entertainTime;

    @ApiModelProperty("订单编号")
    private Long orderNum;

    @ApiModelProperty("入账金额")
    private BigDecimal accountedAmount;
}
