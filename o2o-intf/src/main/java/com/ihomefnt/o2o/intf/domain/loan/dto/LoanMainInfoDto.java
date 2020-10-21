package com.ihomefnt.o2o.intf.domain.loan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiamingyu
 * @date 2018/6/22
 */
@Data
public class LoanMainInfoDto {

    @ApiModelProperty("贷款id")
    private Long loanId;

    @ApiModelProperty("贷款状态")
    private Integer frontStatus;

    @ApiModelProperty("贷款状态字符串")
    private String frontStatusStr;

    @ApiModelProperty("申请时间")
    private Date applyTime;

    @ApiModelProperty("申请时间")
    private String applyTimeStr;

    @ApiModelProperty("入账日期字符串")
    private String accountedTimeStr;

    @ApiModelProperty("入账日期")
    private Date accountedTime;

    @ApiModelProperty("取消时间")
    private Date updateTime;

    @ApiModelProperty("取消时间字符串")
    private String updateTimeStr;

    @ApiModelProperty("贷款金额")
    private BigDecimal amount;

    @ApiModelProperty("贷款申请人姓名")
    private String applyer;

    @ApiModelProperty("贷款申请人身份证号")
    private String applyerIdNum;
}
