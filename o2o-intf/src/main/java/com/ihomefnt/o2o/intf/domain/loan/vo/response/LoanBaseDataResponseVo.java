package com.ihomefnt.o2o.intf.domain.loan.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xiamingyu
 */
@ApiModel("爱家贷利率查询返回")
@Data
public class LoanBaseDataResponseVo {

    @ApiModelProperty("银行Id")
    private Long bankId;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("年限利率对应表")
    private List<LoanMapDataResponseVo> loanMapDataList;

}
