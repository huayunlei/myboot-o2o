package com.ihomefnt.o2o.service.service.vote;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.vote.dto.AnalysisResultDto;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaInfoDto;
import com.ihomefnt.o2o.intf.domain.vote.dto.QuestionnaireResultDto;
import com.ihomefnt.o2o.intf.domain.vote.dto.SignedDnaDto;
import com.ihomefnt.o2o.intf.domain.vote.vo.request.QuestionRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.request.QuestionnaireRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.AnalysisResultResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.OrderSimpleInfoResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.QuestionnaireResponse;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.vote.CharacterColorProxy;
import com.ihomefnt.o2o.intf.service.vote.CharacterColorService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wanyunxin
 * @create 2019-10-18 14:49
 */
@Service
@SuppressWarnings("all")
public class CharacterColorServiceImpl implements CharacterColorService {

    @Autowired
    private CharacterColorProxy characterColorProxy;

    @NacosValue(value = "${app.character.color.result}", autoRefreshed = true)
    private String characterColorResult;


    private static final Logger LOG = LoggerFactory.getLogger(CharacterColorServiceImpl.class);


    private static List colorList= Arrays.asList("Yellow","Blue","Red","Green");


    @NacosValue(value = "${app.character.color.background}", autoRefreshed = true)
    private String BACK_GROUND_IMG;


    /**
     *
     *
     * @param signedDnaDto
     */
    private void sendAiMessage(SignedDnaDto signedDnaDto) {
        try {

        } catch (Exception e) {
            LOG.error("signed.dna task topic send error message :", e);
        }
    }

    /**
     * 问答详情查询
     * @param request
     * @return
     */
    @Override
    public QuestionnaireResponse queryQuestionAnswerList(QuestionRequest request) {
        QuestionnaireResponse questionnaireResponse = characterColorProxy.queryQuestionAnswerList(request);
        if(questionnaireResponse==null) {
            throw new BusinessException(MessageConstant.QUERY_FAILED);
        }
        questionnaireResponse.setBackgroundImg(BACK_GROUND_IMG);
        if(CollectionUtils.isNotEmpty(questionnaireResponse.getQuestionList())){
            for (QuestionnaireResponse.QuestionListBean questionListBean : questionnaireResponse.getQuestionList()) {
                questionListBean.setQuestionImg(AliImageUtil.imageCompress(questionListBean.getQuestionImg(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                if(CollectionUtils.isNotEmpty(questionListBean.getAnswerList())){
                    for (QuestionnaireResponse.QuestionListBean.AnswerListBean answerListBean : questionListBean.getAnswerList()) {
                        answerListBean.setAnswerImg(AliImageUtil.imageCompress(answerListBean.getAnswerImg(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                    }
                }
            }
        }
        return questionnaireResponse;
    }


    /**
     * 提交问答
     * @param request
     * @return
     */
    @Override
    public AnalysisResultResponse addQuestionnaire(QuestionnaireRequest request) {
        AnalysisResultResponse analysisResultResponse = new AnalysisResultResponse();
        if(CollectionUtils.isEmpty(request.getSelectedAnswerList())){
            throw new BusinessException("所选答案为空！");
        }
        Collections.sort(request.getSelectedAnswerList(), Comparator.comparing(QuestionnaireRequest.SelectedAnswerListBean::getQuestionId));
        if(request.getOrderNum()!=null){
            OrderSimpleInfoResponse orderSimpleInfoResponse = characterColorProxy.queryOrderSimpleInfo(request.getOrderNum());
            if(orderSimpleInfoResponse!=null && orderSimpleInfoResponse.getSolutionInfo()!=null && orderSimpleInfoResponse.getSolutionInfo().getSolutionId()!=null){
                List<DnaInfoDto> dnaInfoDtos = characterColorProxy.queryDnaBysolutionId(orderSimpleInfoResponse.getSolutionInfo().getSolutionId());
                request.setDnaIdList(dnaInfoDtos.stream().map(dnaInfoDto -> dnaInfoDto.getDnaId()).collect(Collectors.toList()));
            }
        }
        if(request.getUserInfo()!=null){
            request.setUserId(request.getUserInfo().getId());
        }
        List<QuestionnaireResultDto> questionnaireResultDtos = characterColorProxy.addQuestionnaire(request);
        if(CollectionUtils.isNotEmpty(questionnaireResultDtos)){
            //性格颜色数量相同，则优先级为黄>蓝>红>绿
            Double aDouble = questionnaireResultDtos.stream().map(i -> i.getLabelRate()).max(Double::compareTo).orElse(0.0);
            int count=0;
            for (QuestionnaireResultDto questionnaireResultDto : questionnaireResultDtos) {
                if(questionnaireResultDto.getLabelRate().compareTo(aDouble)==0){
                    count++;
                }
            }
            if(count>=1){
                questionnaireResultDtos.removeIf(questionnaireResultDto -> questionnaireResultDto.getLabelRate().compareTo(aDouble)!=0);
                if(CollectionUtils.isNotEmpty(questionnaireResultDtos) && questionnaireResultDtos.size()>1){
                    setListOrder(colorList,questionnaireResultDtos);
                }
                QuestionnaireResultDto questionnaireResultDto = questionnaireResultDtos.get(0);
                List<AnalysisResultDto> characterColorList = JsonUtils.json2list(characterColorResult, AnalysisResultDto.class);
                for (AnalysisResultDto analysisResultDto : characterColorList) {
                    if(questionnaireResultDto.getLabelDetail().equals(analysisResultDto.getColor())){
                        List<AnalysisResultResponse> familyPlaceList = analysisResultDto.getFamilyPlaceList();

                        switch (analysisResultDto.getColor()){
                            case "Yellow":
                                //黄色的用户有两种角色：天尊和掌门人，出现的概率分别为50%、50%
                                if(familyPlaceList.size()>1){
                                    int i = new Random().nextInt(10);
                                    if(i<=4){
                                        return familyPlaceList.get(0);
                                    }
                                    return familyPlaceList.get(1);
                                }
                                return familyPlaceList.get(0);
                            case "Blue":
                                //蓝色的用户有两种角色：智多星和独行侠，出现的概率分别为60%、40%
                                if(familyPlaceList.size()>1){
                                    int i = new Random().nextInt(10);
                                    if(i<=5){
                                        return familyPlaceList.get(0);
                                    }
                                    return familyPlaceList.get(1);
                                }
                                return familyPlaceList.get(0);
                            case "Red":
                                //红色的用户有三种角色：牛魔王40%，家庭头牌40%，威武将军20%
                                if(familyPlaceList.size()>1){
                                    int i = new Random().nextInt(10);
                                    if(i<=3){
                                        return familyPlaceList.get(0);
                                    }
                                    if(i<=7){
                                        return familyPlaceList.get(1);
                                    }
                                    return familyPlaceList.get(2);
                                }
                                return familyPlaceList.get(0);
                            case "Green":
                                //绿色的用户有两种角色：小公主60%，白龙马40%
                                if(familyPlaceList.size()>1){
                                    int i = new Random().nextInt(10);
                                    if(i<=5){
                                        return familyPlaceList.get(0);
                                    }
                                    return familyPlaceList.get(1);
                                }
                                return familyPlaceList.get(0);
                        }
                        return familyPlaceList.get(0);
                    }
                }

            }
        }else{
            throw new BusinessException("提交失败！");
        }
        return analysisResultResponse;
    }

    public void setListOrder(List<String> colorList, List<QuestionnaireResultDto> questionnaireResultDtos) {
        //按照Posts的Id来排序
        Collections.sort(questionnaireResultDtos, ((o1, o2) -> {
            int io1 = colorList.indexOf(o1.getLabelDetail());
            int io2 = colorList.indexOf(o2.getLabelDetail());

            if (io1 != -1) {
                io1 = questionnaireResultDtos.size() - io1;
            }
            if (io2 != -1) {
                io2 = questionnaireResultDtos.size() - io2;
            }

            return io2 - io1;
        }));
    }

}
