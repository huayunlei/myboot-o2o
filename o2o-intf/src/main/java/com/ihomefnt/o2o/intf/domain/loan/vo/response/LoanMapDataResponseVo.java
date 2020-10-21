package com.ihomefnt.o2o.intf.domain.loan.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 */
@ApiModel("年限及利率")
@Data
public class LoanMapDataResponseVo {

    @ApiModelProperty("贷款年限")
    private Integer year;

    @ApiModelProperty("利率")
    private BigDecimal interestRate;
}
