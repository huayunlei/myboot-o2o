package com.ihomefnt.o2o.intf.domain.main.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */

@ApiModel("贷款信息")
@Data
@Accessors(chain = true)
public class LoanInfo {

    @ApiModelProperty("贷款Id")
    private Long loanId;

    @ApiModelProperty("贷款状态")
    private Integer loanStatus;

    @ApiModelProperty("贷款状态描述")
    private String loanStatusStr;

    @ApiModelProperty("爱家贷数量")
    private Integer loanCount;
}
