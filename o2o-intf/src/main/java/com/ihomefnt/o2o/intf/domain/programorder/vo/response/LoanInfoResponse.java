package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-12-05 17:48
 */
@Data
@ApiModel("查询爱家贷简单信息")
public class LoanInfoResponse {

    @ApiModelProperty("是否可申请爱家贷 0 不可 1可以")
    private Integer loanAble = 0;

    @ApiModelProperty("名下爱家贷数量")
    private Integer loanCount = 0;

    public LoanInfoResponse(Integer loanAble, Integer loanCount) {
        this.loanAble = loanAble;
        this.loanCount = loanCount;
    }

    public LoanInfoResponse() {
    }
}
