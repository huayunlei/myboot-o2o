package com.ihomefnt.o2o.intf.domain.loan.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/6/22
 */
@Data
@ApiModel("爱家贷-查询爱家贷详情")
public class LoanDetailRequestVo extends HttpBaseRequest {

    private Long loanId;
}
