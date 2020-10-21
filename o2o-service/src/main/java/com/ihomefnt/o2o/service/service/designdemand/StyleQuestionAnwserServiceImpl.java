package com.ihomefnt.o2o.service.service.designdemand;

import com.ihomefnt.o2o.constant.DesignDemondEnum;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.AppSolutionDesignResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.StyleRecordRequest;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.PersonalDesignResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.StyleRecordResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.style.vo.request.QuerySelectedQusAnsRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.personalneed.DesignTaskAppEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.designdemand.PersonalNeedProxy;
import com.ihomefnt.o2o.intf.proxy.designdemand.StyleQuestionAnwserProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.service.designDemand.ProgramPersonalNeedService;
import com.ihomefnt.o2o.intf.service.designDemand.StyleQuestionAnwserService;
import com.ihomefnt.o2o.intf.service.home.HomeV510PageService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import com.ihomefnt.o2o.service.service.home.HomeV510PageServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class StyleQuestionAnwserServiceImpl implements StyleQuestionAnwserService {

    @Autowired
    private StyleQuestionAnwserProxy styleQuestionAnwserProxy;
    @Autowired
    private PersonalNeedProxy personalNeedProxy;
    @Autowired
    private HomeV510PageService homeV510PageService;

    private String dnaQuestionCode = "104";

    private static final String SPECIAL_MESSAGE = "为了更好地了解您的需求，我们更新了问卷中部分问题，因此修改设计需求时有些问题您可能需要重新回答。";
    @Autowired
    private ProgramPersonalNeedService programPersonalNeedService;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Override
    public List<StyleQuestionAnwserStepResponse> queryAllQuestionAnwserList(HttpBaseRequest request, Integer version) {

        List<StyleQuestionAnwserStepResponse> styleQuestionAnwserStepResponses = styleQuestionAnwserProxy.queryAllQuestionAnwserList(version);
        if (version == 2) {//新版本问答列表步骤跟老的不一致
            styleQuestionAnwserStepResponses.get(1).getQuestionList().addAll(styleQuestionAnwserStepResponses.get(0).getQuestionList().stream().filter(o1 ->
                    o1.getCode().equals("102")).collect(toList()));
            styleQuestionAnwserStepResponses.get(0).getQuestionList().removeIf(o1 -> o1.getCode().equals("102"));

            styleQuestionAnwserStepResponses.get(2).getQuestionList().addAll(styleQuestionAnwserStepResponses.get(1).getQuestionList().stream().filter(o1 ->
                    o1.getCode().equals("103") || o1.getCode().equals("104")).collect(toList()));
            styleQuestionAnwserStepResponses.get(1).getQuestionList().removeIf(o1 -> o1.getCode().equals("103") || o1.getCode().equals("104"));

            styleQuestionAnwserStepResponses.get(3).getQuestionList().addAll(styleQuestionAnwserStepResponses.get(2).getQuestionList().stream().filter(o1 ->
                    o1.getCode().equals("105")).collect(toList()));
            styleQuestionAnwserStepResponses.get(2).getQuestionList().removeIf(o1 -> o1.getCode().equals("105"));

            styleQuestionAnwserStepResponses.get(4).getQuestionList().addAll(styleQuestionAnwserStepResponses.get(3).getQuestionList().stream().filter(o1 ->
                    o1.getCode().equals("121") || o1.getCode().equals("111") || o1.getCode().equals("112") || o1.getCode().equals("113")).collect(toList()));

            styleQuestionAnwserStepResponses.get(3).getQuestionList().removeIf(o1 -> o1.getCode().equals("121") || o1.getCode().equals("111") || o1.getCode().equals("112") || o1.getCode().equals("113"));

            styleQuestionAnwserStepResponses.get(4).getQuestionList().sort(Comparator.comparing(StyleQuestionAndAnwserResponse::getSortBy));
        }
        return styleQuestionAnwserStepResponses;
    }


    @Override
    public List<StyleQuestionSelectedResponse> queryQuestionAnwserDetail(QuerySelectedQusAnsRequest request) {
        HttpUserInfoRequest userDto = request.getUserInfo();
        List<StyleQuestionSelectedResponse> selectedStyleQuestionList = null;
        StyleQuestionSelectedNewResponse styleQuestionSelectedNewResponse = null;
        if (request.getCommitRecordId() != null && request.getCommitRecordId() > 0) {
            //查询指定
            styleQuestionSelectedNewResponse = styleQuestionAnwserProxy.queryQuestionAnwserDetail(request.getCommitRecordId(), request.getOrderId());
        } else if (request.getOrderId() != null && userDto != null) {
            //查询最新
            styleQuestionSelectedNewResponse = styleQuestionAnwserProxy.queryQuestionAnwserDetailLatest(request.getOrderId(), userDto.getId());
        }
        if (styleQuestionSelectedNewResponse == null) {
            throw new BusinessException(HttpReturnCode.SUCCESS, MessageConstant.DATE_EMPTY);
        }
        selectedStyleQuestionList = styleQuestionSelectedNewResponse.getStyleQuestionSelectedResponseList();

        if (CollectionUtils.isNotEmpty(selectedStyleQuestionList)) {
            StyleQuestionSelectedResponse questionTwo = new StyleQuestionSelectedResponse();

            Integer osType = request.getOsType();
            Integer width = request.getWidth();
            if (osType == null || osType == 0) {
                osType = 1;
            }
            if (width == null) {
                width = 750;
            }

            selectedStyleQuestionList = selectedStyleQuestionList.stream().filter(o1 -> !(o1.getId() != null && o1.getId().equals(13) && (CollectionUtils.isEmpty(o1.getAnwserList()) || o1.getAnwserList().get(0) == null || o1.getAnwserList().get(0).getAnwserContent() == null))).collect(toList());
            for (StyleQuestionSelectedResponse vo : selectedStyleQuestionList) {
                // DNA样板间问题 需要单独赋值
                if (dnaQuestionCode.equals(vo.getCode())) {
                    Map<String, Object> obj = new HashMap<>();
                    AppSolutionDesignResponseVo designResponseVo = null;
                    if (request.getOrderId() != null && request.getOrderId() > 0) {
                        Map<String, Object> params = new HashMap<>();
                        params.put("orderNum", request.getOrderId());
                        if (styleQuestionSelectedNewResponse.getTaskId() > 0) {
                            params.put("taskId", styleQuestionSelectedNewResponse.getTaskId());
                        }
                        designResponseVo = personalNeedProxy.queryDesignDemond(params);
                    } else {
                        Map<String, Object> params = new HashMap<>();
                        params.put("userId", userDto.getId());
                        if (styleQuestionSelectedNewResponse.getTaskId() > 0) {
                            params.put("taskId", styleQuestionSelectedNewResponse.getTaskId());
                        }
                        List<AppSolutionDesignResponseVo> list = personalNeedProxy.queryDesignDemondHistory(params);
                        if (CollectionUtils.isNotEmpty(list)) {
                            designResponseVo = list.get(0);
                        }
                    }
                    if (null != designResponseVo) {
                        obj.put("dnaId", designResponseVo.getDnaId());
                        obj.put("dnaName", designResponseVo.getDnaName());
                        obj.put("dnaStyleName", designResponseVo.getDnaStyle());
                        obj.put("dnaHeadImgUrl", AliImageUtil.imageCompress(designResponseVo.getDnaHeadImg(), osType, width, ImageConstant.SIZE_MIDDLE));
                        // 组装dna空间信息
                        homeV510PageService.assembleDnaRoomList(obj, designResponseVo.getTaskDnaRoomList(), questionTwo, request.getOsType(), request.getWidth());
                        // 转换设计任务状态
                        vo.setTaskStatus(programPersonalNeedService.transferDesignStatus(designResponseVo.getTaskStatus()));
                        if (designResponseVo.getTaskStatus() != null) {
                            vo.setTaskStatusStr(DesignDemondEnum.getEnumByCode(designResponseVo.getTaskStatus()).getStatusStr());
                        }
                    }

                    // 查询DNA信息
                    List<StyleAnwserSelectedResponse> anwserList = new ArrayList<>();
                    StyleAnwserSelectedResponse anwser = new StyleAnwserSelectedResponse();
                    if (null != vo.getAnwserList() && null != vo.getAnwserList().get(0)) {
                        StyleAnwserSelectedResponse styleAnwser = vo.getAnwserList().get(0);
                        anwser.setAnwserId(styleAnwser.getAnwserId());
                        anwser.setAnwserContent(styleAnwser.getAnwserContent());
                        anwser.setQuestionId(styleAnwser.getQuestionId());
                        vo.setAnwserList(null);
                    }
                    anwser.setObj(obj);
                    anwser.setAnwserType(1);
                    anwserList.add(0, anwser);
                    vo.setAnwserList(anwserList);
                    break;
                }
            }
            if (null != questionTwo && !StringUtil.isNullOrEmpty(questionTwo.getQuestionDetail()) && !containsGnf(selectedStyleQuestionList)) {
                selectedStyleQuestionList.add(1, questionTwo);
            } else if (null != questionTwo && !StringUtil.isNullOrEmpty(questionTwo.getQuestionDetail()) && containsGnf(selectedStyleQuestionList)) {
                selectedStyleQuestionList.removeIf(styleQuestionSelectedResponse -> (styleQuestionSelectedResponse.getCode() != null && styleQuestionSelectedResponse.getCode().equals("102")));
                selectedStyleQuestionList.add(getIndexByOrder(selectedStyleQuestionList), questionTwo);
            }
        }

        return selectedStyleQuestionList;
    }


    /**
     * 获取插入index
     *
     * @param selectedStyleQuestionList
     * @return
     */
    private Integer getIndexByOrder(List<StyleQuestionSelectedResponse> selectedStyleQuestionList) {
        Integer index = 1;
        for (StyleQuestionSelectedResponse styleQuestionSelectedResponse : selectedStyleQuestionList) {
            if (styleQuestionSelectedResponse.getCode() != null && (styleQuestionSelectedResponse.getCode().equals("118") || styleQuestionSelectedResponse.getCode().equals("116"))) {
                index++;
            }
            if (styleQuestionSelectedResponse.getCode() != null && (styleQuestionSelectedResponse.getCode().equals("119") || styleQuestionSelectedResponse.getCode().equals("117"))) {
                index++;
            }
        }
        return index;
    }

    /**
     * 是否包含功能房，是包含 否不包含
     *
     * @param selectedStyleQuestionList
     * @return
     */
    private boolean containsGnf(List<StyleQuestionSelectedResponse> selectedStyleQuestionList) {
        boolean contain = true;
        if (CollectionUtils.isNotEmpty(selectedStyleQuestionList)) {
            for (StyleQuestionSelectedResponse styleQuestionSelectedResponse : selectedStyleQuestionList) {
                if (styleQuestionSelectedResponse.getCode().equals("102")) {
                    contain = true;
                    break;
                } else {
                    contain = false;
                }
            }
        }
        return contain;

    }

    @Autowired
    private ProductProgramProxy productProgramProxy;

    /**
     * 查询设计任务简单信息
     *
     * @param request
     */
    @Override
    public QuestionAnwserSimpleInfoResponse queryQuestionAnwserSimpleInfo(QuerySelectedQusAnsRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNum", request.getOrderId());
        Integer specialFlag = 0;
        String specialMessage = null;
        if (request.getCommitRecordId() != null && request.getCommitRecordId() != 0) {
            StyleQuestionSelectedNewResponse styleQuestionSelectedNewResponse = styleQuestionAnwserProxy.queryQuestionAnwserDetail(request.getCommitRecordId(), request.getOrderId());
            if (styleQuestionSelectedNewResponse != null && (styleQuestionSelectedNewResponse.getVersion() == null || styleQuestionSelectedNewResponse.getVersion() <= 1)
                    && request.getAppVersion() != null && !VersionUtil.mustUpdate(request.getAppVersion(), "5.5.3")) {//低版本问题在高版本app上修改
                specialFlag = 1;
                specialMessage = SPECIAL_MESSAGE;
            }
            params.put("taskId", request.getCommitRecordId());
        }
        QuestionAnwserSimpleInfoResponse response = new QuestionAnwserSimpleInfoResponse();
        AppSolutionDesignResponseVo designResponseVo = personalNeedProxy.queryDesignDemond(params);
        if (request.getUserInfo() != null && designResponseVo != null && designResponseVo.getSolutionId() != null) {
            List<Integer> solutionReadListByUserId = productProgramProxy.queryUserSolutionReadListByUserId(request.getUserInfo().getId());
            response.setSolutionsHasRead(solutionReadListByUserId.contains(designResponseVo.getSolutionId()));
        }
        if (request.getOrderId() != null) {
            AppOrderBaseInfoResponseVo appOrderBaseInfoResponseVo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());
            if (appOrderBaseInfoResponseVo != null) {
                response.setIsConfirmed(appOrderBaseInfoResponseVo.getPreConfirmed());
                response.setAllMoney(appOrderBaseInfoResponseVo.getAllMoney());
            }
        }
        if (designResponseVo != null) {
            DesignTaskAppEnum designTaskAppEnumByTaskStatus = HomeV510PageServiceImpl.getDesignTaskAppEnumByTaskStatus(designResponseVo.getTaskStatus());
            if (designTaskAppEnumByTaskStatus != null) {
                response.setOrderNum(request.getOrderId())
                        .setTaskStatus(designTaskAppEnumByTaskStatus.getTaskStatus())
                        .setTaskStatusStr(designTaskAppEnumByTaskStatus.getTaskStatusStr())
                        .setSolutionId(designResponseVo.getSolutionId())
                        .setSpecialFlag(specialFlag)
                        .setSpecialMessage(specialMessage);
            }
        }
        StyleRecordResponse styleRecordResponse = programPersonalNeedService.queryStyleRecord(new StyleRecordRequest().setOrderId(request.getOrderId()), null);
        if (styleRecordResponse != null && CollectionUtils.isNotEmpty(styleRecordResponse.getStyleRecordList())) {
            for (PersonalDesignResponse personalDesignResponse : styleRecordResponse.getStyleRecordList()) {
                if (personalDesignResponse.getTaskStatus().equals(DesignTaskAppEnum.IN_DESIGN.getTaskStatus())) {
                    response.setLastTaskIsUnderDesign(Boolean.TRUE);
                    break;
                }
            }
        }
        return response;
    }

}
