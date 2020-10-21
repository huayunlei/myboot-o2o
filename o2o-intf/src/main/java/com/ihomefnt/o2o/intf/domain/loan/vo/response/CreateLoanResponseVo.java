package com.ihomefnt.o2o.intf.domain.loan.vo.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2018/6/26
 */
@ApiModel("艾佳贷")
@Data
@Accessors(chain = true)
public class CreateLoanResponseVo {
    private Integer loanId;
}
