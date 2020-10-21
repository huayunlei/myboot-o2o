package com.ihomefnt.o2o.intf.proxy.investigate;

import com.ihomefnt.o2o.intf.domain.investigate.dto.InvestigateCommitDto;
import com.ihomefnt.o2o.intf.domain.investigate.vo.response.InvestigateQueryResponse;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/4 11:00 上午
 */
public interface InvestigateProxy {
    InvestigateQueryResponse queryInvestigateInfo(Integer investigateId);

    void commitInvestigate(InvestigateCommitDto commitDto);
}
