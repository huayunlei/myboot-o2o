package com.ihomefnt.o2o.intf.service.designDemand;

import com.ihomefnt.o2o.intf.domain.designdemand.request.DesignDemandToolQueryRequest;
import com.ihomefnt.o2o.intf.domain.designdemand.request.QueryDesignDemandInfoRequest;
import com.ihomefnt.o2o.intf.domain.designdemand.response.DesignDemandToolOrderListResponse;
import com.ihomefnt.o2o.intf.domain.designdemand.response.QueryDesignDemandInfoResponse;
import com.ihomefnt.o2o.intf.domain.designdemand.response.SimpleDataForBetaAppResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.CommitDesignRequest;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.PersonalDesignResponse;

import java.util.List;

public interface DesignDemandToolService {
    /**
     * 订单房产列表
     *
     * @param userId
     * @return
     */
    DesignDemandToolOrderListResponse queryUserHouseListForDesignDemandTool(Integer userId);

    /**
     * 提交记录列表
     *
     * @param request
     * @return
     */
    List<PersonalDesignResponse> queryCommentRecord(DesignDemandToolQueryRequest request);

    /**
     * 根据ID查询设计任务详情
     *
     * @param request
     * @return
     */
    QueryDesignDemandInfoResponse queryDesignDemandInfo(QueryDesignDemandInfoRequest request);

    /**
     * 新增或更新设计任务草稿
     *
     * @param commitDesignRequest
     * @return
     */
    String addOrUpdateDesignDraft(CommitDesignRequest commitDesignRequest);

    /**
     * 发送给用户确认
     *
     * @param request
     * @return
     */
    DesignDemandToolQueryRequest sendToUserAffirm(CommitDesignRequest request);

    /**
     * 用户确认设计需求
     *
     * @param request
     * @return
     */
    DesignDemandToolQueryRequest affirmDesignDemand(DesignDemandToolQueryRequest request);

    /**
     * 小艾确认设计需求
     *
     * @param request
     * @return
     */
    DesignDemandToolQueryRequest affirmDesignDemandByXa(DesignDemandToolQueryRequest request);

    /**
     * 提供给betaApp使用
     * @param request
     * @return
     */
    SimpleDataForBetaAppResponse queryProgramOpAndDesignDemandByOrderId(DesignDemandToolQueryRequest request);
}
