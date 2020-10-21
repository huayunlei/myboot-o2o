package com.ihomefnt.o2o.intf.domain.loan.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author xiamingyu
 */
@Data
public class LoanListResponseVo {

    private List<LoanDetailDataResponseVo> loanList;
}
