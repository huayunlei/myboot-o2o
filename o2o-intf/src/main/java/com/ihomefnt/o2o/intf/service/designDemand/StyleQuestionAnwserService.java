package com.ihomefnt.o2o.intf.service.designDemand;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.request.QuerySelectedQusAnsRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.response.QuestionAnwserSimpleInfoResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionAnwserStepResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionSelectedResponse;

import java.util.List;

public interface StyleQuestionAnwserService {

    /**
     * 查询所有问题答案
     *
     * @return
     */
    List<StyleQuestionAnwserStepResponse> queryAllQuestionAnwserList(HttpBaseRequest request,Integer version);

    /**
     * 查询订单已选问题答案详情
     *
     * @param request
     * @return
     */
    List<StyleQuestionSelectedResponse> queryQuestionAnwserDetail(QuerySelectedQusAnsRequest request);

    /**
     * 设计任务简单信息
     *
     * @param request
     * @return
     */
    QuestionAnwserSimpleInfoResponse queryQuestionAnwserSimpleInfo(QuerySelectedQusAnsRequest request);
}
