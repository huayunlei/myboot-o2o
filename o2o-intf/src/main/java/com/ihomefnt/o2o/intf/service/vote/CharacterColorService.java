package com.ihomefnt.o2o.intf.service.vote;

import com.ihomefnt.o2o.intf.domain.vote.vo.request.QuestionRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.request.QuestionnaireRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.AnalysisResultResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.QuestionnaireResponse;


/**
 * @author wanyunxin
 * @create 2019-10-18 14:49
 */
public interface CharacterColorService {

    /**
     * 问答详情查询
     * @param request
     * @return
     */
    QuestionnaireResponse queryQuestionAnswerList(QuestionRequest request);

    /**
     * 提交问答
     * @param request
     * @return
     */
    AnalysisResultResponse addQuestionnaire(QuestionnaireRequest request);

}
