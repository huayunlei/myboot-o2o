package com.ihomefnt.o2o.service.proxy.designdemand;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.o2o.intf.domain.designdemand.request.DesignDemandToolQueryRequest;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.CommitDesignRequest;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDdcServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.proxy.designdemand.DesignDemandToolProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-13 16:13
 */
@Repository
public class DesignDemandToolProxyImpl implements DesignDemandToolProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public List<CommitDesignRequest> queryDesignDemandDraft(List<Integer> orderIdList,Integer createUserId,Integer taskStatus) {
        return ((ResponseVo<List<CommitDesignRequest>>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.QUERY_DESIGN_DEMOND_HISTORY, Maps.newHashMap("orderNumList", orderIdList,"userId",createUserId,"taskStatus",taskStatus), new TypeReference<ResponseVo<List<CommitDesignRequest>>>() {
        })).getData();
    }

    @Override
    public CommitDesignRequest queryDesignDemandInfo(String taskId) {
        return ((ResponseVo<CommitDesignRequest>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.QUERY_DESIGN_DEMAND_INFO, Maps.newHashMap("taskId", taskId), new TypeReference<ResponseVo<CommitDesignRequest>>() {
        })).getData();
    }

    @Override
    public DesignDemandToolQueryRequest addOrUpdateDesignDraft(CommitDesignRequest commitDesignRequest) {
        return ((ResponseVo<DesignDemandToolQueryRequest>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.ADD_OR_UPDATE_DESIGN_DEMAND, commitDesignRequest, new TypeReference<ResponseVo<DesignDemandToolQueryRequest>>() {
        })).getData();
    }
}
