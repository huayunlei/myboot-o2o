package com.ihomefnt.o2o.service.proxy.vote;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaInfoDto;
import com.ihomefnt.o2o.intf.domain.vote.dto.QuestionnaireResultDto;
import com.ihomefnt.o2o.intf.domain.vote.vo.request.QuestionRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.request.QuestionnaireRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.OrderSimpleInfoResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.QuestionnaireResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AirecServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.DollyWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.proxy.vote.CharacterColorProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-10-18 14:51
 */
@Repository
public class CharacterColorProxyImpl implements CharacterColorProxy {

    @Autowired
    private StrongSercviceCaller sercviceCaller;

    /**
     * 问答详情查询
     * @param request
     * @return
     */
    @Override
    public QuestionnaireResponse queryQuestionAnswerList(QuestionRequest request) {
        return ((ResponseVo<QuestionnaireResponse>) sercviceCaller.post(AirecServiceNameConstants.QUERY_QUESTION, request, new TypeReference<ResponseVo<QuestionnaireResponse>>() {
        })).getData();
    }

    /**
     * 提交问卷
     * @param request
     * @return
     */
    @Override
    public List<QuestionnaireResultDto> addQuestionnaire(QuestionnaireRequest request) {
        return ((ResponseVo<List<QuestionnaireResultDto>>) sercviceCaller.post(AirecServiceNameConstants.COMMIT_QUESTIONNAIRE, request, new TypeReference<ResponseVo<List<QuestionnaireResultDto>>>() {
        })).getData();
    }

    /**
     * 根据方案id查询dna
     * @param solutionId
     * @return
     */
    @Override
    public List<DnaInfoDto> queryDnaBysolutionId(Long solutionId) {
        return ((ResponseVo<List<DnaInfoDto>>) sercviceCaller.post(DollyWebServiceNameConstants.QUERY_DNA_SIMPLEINFO_BY_SOLUTIONID, Maps.newHashMap("solutionId", solutionId), new TypeReference<ResponseVo<List<DnaInfoDto>>>() {
        })).getData();
    }

    /**
     * 查询订单简单信息
     * @param orderNum
     * @return
     */
    @Override
    public OrderSimpleInfoResponse queryOrderSimpleInfo(Integer orderNum) {
        return ((ResponseVo<OrderSimpleInfoResponse>) sercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ORDER_SIMPLE_INFO, Maps.newHashMap("orderId", orderNum), new TypeReference<ResponseVo<OrderSimpleInfoResponse>>() {
        })).getData();
    }
}
