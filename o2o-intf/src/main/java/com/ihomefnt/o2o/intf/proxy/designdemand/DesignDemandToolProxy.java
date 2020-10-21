package com.ihomefnt.o2o.intf.proxy.designdemand;

import com.ihomefnt.o2o.intf.domain.designdemand.request.DesignDemandToolQueryRequest;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.CommitDesignRequest;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-13 16:12
 */
public interface DesignDemandToolProxy {
    List<CommitDesignRequest> queryDesignDemandDraft(List<Integer> orderIdList,Integer createUserId,Integer taskStatus);

    CommitDesignRequest queryDesignDemandInfo(String designDemandId);

    DesignDemandToolQueryRequest addOrUpdateDesignDraft(CommitDesignRequest commitDesignRequest);
}
