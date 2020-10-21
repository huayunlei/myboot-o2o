package com.ihomefnt.o2o.intf.service.investigate;

import com.ihomefnt.o2o.intf.domain.investigate.vo.request.InvestigateCommitRequest;
import com.ihomefnt.o2o.intf.domain.investigate.vo.request.InvestigateQueryRequest;
import com.ihomefnt.o2o.intf.domain.investigate.vo.response.InvestigateQueryResponse;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/4 11:00 上午
 */
public interface InvestigateService {
    InvestigateQueryResponse queryInvestigateInfo(InvestigateQueryRequest request);

    void commitInvestigate(InvestigateCommitRequest request);
}
