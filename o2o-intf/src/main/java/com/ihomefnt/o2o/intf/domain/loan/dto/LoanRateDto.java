package com.ihomefnt.o2o.intf.domain.loan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 */
@Data
public class LoanRateDto {

    @ApiModelProperty("银行ID")
    private Long bankId;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("贷款期限")
    private Integer loanTerm;

    @ApiModelProperty("贷款利率")
    private BigDecimal loanRate;

    @ApiModelProperty("还款方式")
    private Integer loanType;//1、分期分摊 2、期次一次性

    @ApiModelProperty("公司id")
    private Integer companyId;//公司id
}
