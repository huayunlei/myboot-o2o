package com.ihomefnt.o2o.intf.domain.activity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 * @date 2018/11/26
 */
@Data
@ApiModel("领券结果返回")
public class GetVoucherResponse {

    @ApiModelProperty("券码")
    private String voucherCode;

    @ApiModelProperty("券名称")
    private String voucherName;

    @ApiModelProperty("券金额")
    private BigDecimal voucherMoney;
}
