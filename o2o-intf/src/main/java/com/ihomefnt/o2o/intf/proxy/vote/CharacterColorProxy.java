package com.ihomefnt.o2o.intf.proxy.vote;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaInfoDto;
import com.ihomefnt.o2o.intf.domain.vote.dto.QuestionnaireResultDto;
import com.ihomefnt.o2o.intf.domain.vote.vo.request.QuestionRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.request.QuestionnaireRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.OrderSimpleInfoResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.QuestionnaireResponse;
import org.codehaus.jackson.type.TypeReference;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-10-18 14:50
 */
public interface CharacterColorProxy {


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
     List<QuestionnaireResultDto> addQuestionnaire(QuestionnaireRequest request);

    /**
     * 根据方案id查询dna
     * @param solutionId
     * @return
     */
    List<DnaInfoDto> queryDnaBysolutionId(Long solutionId);

    /**
     * 查询订单简单信息
     * @param orderNum
     * @return
     */
    OrderSimpleInfoResponse queryOrderSimpleInfo(Integer orderNum);
}
