package com.ihomefnt.o2o.service.proxy.designdemand;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.style.vo.request.StyleQuestionAnwserCommitNewRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDdcServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.designdemand.StyleQuestionAnwserProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author huayunlei
 * @created 2018年11月21日 下午6:40:18
 * @desc 风格问题答案WCM服务代理
 */
@Service
public class StyleQuestionAnwserProxyImpl implements StyleQuestionAnwserProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public Integer commitStyleQuestionAnwser(StyleQuestionAnwserCommitNewRequest questionAnwsers) {
        ResponseVo<Integer> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.COMMIT_STYLE_QUESTION_ANWSER, questionAnwsers,
                    new TypeReference<ResponseVo<Integer>>() {
                    });
        } catch (Exception e) {
            return -1;
        }

        if (responseVo != null && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return -1;
    }

    @Override
    public List<StyleQuestionAnwserStepResponse> queryAllQuestionAnwserList(Integer version) {
        ResponseVo<List<StyleQuestionAnwserStepResponse>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.QUERY_ALL_QUESTION_ANWSER_LIST, version,
                    new TypeReference<ResponseVo<List<StyleQuestionAnwserStepResponse>>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo != null && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public StyleQuestionAnwserMapResponse queryAllQuestionAnwserMap(Integer version) {
        ResponseVo<StyleQuestionAnwserMapResponse> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.QUERY_ALL_QUESTION_ANWSER_MAP, version,
                    new TypeReference<ResponseVo<StyleQuestionAnwserMapResponse>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo != null && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public StyleQuestionSelectedNewResponse queryQuestionAnwserDetail(Integer commitRecordId,Integer orderNum) {
        JSONObject param = new JSONObject();
        if (null != commitRecordId && commitRecordId > 0) {
            param.put("taskId", commitRecordId);
        }
        param.put("orderNum", orderNum);
        ResponseVo<StyleQuestionSelectedNewResponse> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.QUERY_QUESTION_ANWSER_DETAIL, param,
                    new TypeReference<ResponseVo<StyleQuestionSelectedNewResponse>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo != null && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public StyleQuestionSelectedNewResponse queryQuestionAnwserDetailLatest(Integer orderNum, Integer userId) {
        JSONObject param = new JSONObject();
        if (null != orderNum && orderNum > 0) {
            param.put("orderNum", orderNum);
        }
        if (null != userId && userId > 0) {
            param.put("userId", userId);
        }
        ResponseVo<StyleQuestionSelectedNewResponse> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.QUERY_QUESTION_ANWSER_DETAIL_LATEST, param,
                    new TypeReference<ResponseVo<StyleQuestionSelectedNewResponse>>() {
                    });
        } catch (Exception e) {
            throw new BusinessException(HttpReturnCode.WCM_FAILED, MessageConstant.FAILED);
        }
        if (responseVo == null) {
            throw new BusinessException(HttpReturnCode.WCM_FAILED, MessageConstant.FAILED);
        }
        return responseVo.getData();
    }

    @Override
    public List<StyleCommitRecordResponse> queryStyleCommitRecordList(Integer orderId) {
        JSONObject param = new JSONObject();
        if (null != orderId && orderId > 0) {
            param.put("orderNum", orderId);
        }
        ResponseVo<List<StyleCommitRecordResponse>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.QUERY_STYLE_COMMIT_RECORD_LIST, param,
                    new TypeReference<ResponseVo<List<StyleCommitRecordResponse>>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo != null && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<StyleAnwserResponse> queryAllAnwserList(Map param) {
        return ((ResponseVo<List<StyleAnwserResponse>>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.QUERY_ALL_ANWSER_LIST, param, new TypeReference<ResponseVo<List<StyleAnwserResponse>>>() {
        })).getData();
    }
}

