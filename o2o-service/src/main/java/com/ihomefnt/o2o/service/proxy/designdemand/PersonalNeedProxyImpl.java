package com.ihomefnt.o2o.service.proxy.designdemand;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.AppSolutionDesignResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.CommitDesignDemandVo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.DnaInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.DnaRoomInfoResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDdcServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.DollyWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.designdemand.PersonalNeedProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 个性化需求
 * Author: ZHAO
 * Date: 2018年5月26日
 */
@Slf4j
@Service
public class PersonalNeedProxyImpl implements PersonalNeedProxy {
    @Resource
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public boolean checkUserDemond(Map<String, Object> params) {
        ResponseVo<Boolean> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.CHECK_USER_DEMOND_NEW, params,
                    new TypeReference<ResponseVo<Boolean>>() {
                    });
        } catch (Exception e) {
            return false;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return false;
    }

    @Override
    public CommitDesignDemandVo commitDesignDemand(Map<String, Object> params) {
        ResponseVo<CommitDesignDemandVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.COMMIT_DESIGN_DEMAND, params,
                    new TypeReference<ResponseVo<CommitDesignDemandVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public AppSolutionDesignResponseVo queryDesignDemond(Map<String, Object> params) {
        ResponseVo<AppSolutionDesignResponseVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.QUERY_DESIGN_DEMAND_INFO, params,
                    new TypeReference<ResponseVo<AppSolutionDesignResponseVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<DnaInfoResponseVo> randomQueryDna(Integer dnaAmount) {
        ResponseVo<List<DnaInfoResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(DollyWebServiceNameConstants.RANDOM_QUERY_DNA, dnaAmount,
                    new TypeReference<ResponseVo<List<DnaInfoResponseVo>>>() {
                    });
        } catch (Exception e) {
            return Collections.emptyList();
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return Collections.emptyList();
    }

    @Override
    public List<AppSolutionDesignResponseVo> queryDesignDemondHistory(Map<String, Object> params) {
        ResponseVo<List<AppSolutionDesignResponseVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.QUERY_DESIGN_DEMOND_HISTORY, params,
                    new TypeReference<ResponseVo<List<AppSolutionDesignResponseVo>>>() {
                    });
        } catch (Exception e) {
            return Collections.emptyList();
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return Collections.emptyList();
    }


    @Override
    public PageModel<DnaRoomInfoResponse> queryDnaRoomByUsageId(Map<String, Object> params) {
        ResponseVo<PageModel<DnaRoomInfoResponse>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_DNA_ROOM_BY_USAGEID, params,
                    new TypeReference<ResponseVo<PageModel<DnaRoomInfoResponse>>>() {
                    });
        } catch (Exception e) {
            return null;
        }
        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<AppSolutionDesignResponseVo> queryDesignDemondForOrderList(List<Integer> orderIdList) {
        return ((ResponseVo<List<AppSolutionDesignResponseVo>>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.BATCH_QUERY_DESIGN_TASKSTATUS_BYORDERNUM_LIST,
                Maps.newHashMap("orderNumList", orderIdList), new TypeReference<ResponseVo<List<AppSolutionDesignResponseVo>>>() {
        })).getData();
    }
    @Override
    public CommitDesignDemandVo commitDesignDemandByCustomerService(Map<String, Object> params) {
        return ((ResponseVo<CommitDesignDemandVo>)strongSercviceCaller.post(AladdinDdcServiceNameConstants.COMMIT_DESIGN_DEMAND_BYCUSTOMER_SERVICE, params,
                    new TypeReference<ResponseVo<CommitDesignDemandVo>>() {
                    })).getData();
    }
}
