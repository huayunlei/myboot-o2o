package com.ihomefnt.o2o.service.service.designdemand;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.o2o.constant.DesignDemondEnum;
import com.ihomefnt.o2o.constant.FamilyOrderStatusEnum;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.designdemand.request.DesignDemandToolQueryRequest;
import com.ihomefnt.o2o.intf.domain.designdemand.request.QueryDesignDemandInfoRequest;
import com.ihomefnt.o2o.intf.domain.designdemand.response.*;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.AppHousePropertyResultDto;
import com.ihomefnt.o2o.intf.domain.homecard.dto.ApartmentInfoVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNAInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNARoomVo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.AppSolutionDesignResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.CommitDesignDemandVo;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.CommitDesignRequest;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.StyleRecordRequest;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.PersonalDesignResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.StyleRecordResponse;
import com.ihomefnt.o2o.intf.domain.program.dto.ProgramOpinionDetailDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderDetailDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.QueryMasterOrderIdByHouseIdResultDto;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.CommitDesignDnaRoom;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.request.StyleAnwserCommitRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.request.StyleQuestionAnwserCommitNewRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.request.StyleQuestionAnwserCommitRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.response.*;
import com.ihomefnt.o2o.intf.domain.user.dto.AppMasterOrderDetailDto;
import com.ihomefnt.o2o.intf.domain.user.dto.HbmsOrderDetailDto;
import com.ihomefnt.o2o.intf.domain.user.dto.HousePropertyInfoResultDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.personalneed.DesignTaskAppEnum;
import com.ihomefnt.o2o.intf.manager.constant.programorder.RoomUseEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.designdemand.DesignDemandToolProxy;
import com.ihomefnt.o2o.intf.proxy.designdemand.PersonalNeedProxy;
import com.ihomefnt.o2o.intf.proxy.designdemand.StyleQuestionAnwserProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardBossProxy;
import com.ihomefnt.o2o.intf.proxy.home.HouseProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProgramOpinionProxy;
import com.ihomefnt.o2o.intf.proxy.sms.SmsProxy;
import com.ihomefnt.o2o.intf.proxy.user.PersonalCenterProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.designDemand.DesignDemandToolService;
import com.ihomefnt.o2o.intf.service.designDemand.ProgramPersonalNeedService;
import com.ihomefnt.o2o.intf.service.designDemand.StyleQuestionAnwserService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class DesignDemandToolServiceImpl implements DesignDemandToolService {

    @Autowired
    private DesignDemandToolProxy designDemandToolProxy;

    @Autowired
    private HouseProxy houseProxy;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Autowired
    private PersonalNeedProxy personalNeedProxy;

    @Autowired
    private HomeCardBossProxy homeCardBossProxy;

    @Autowired
    private ProgramPersonalNeedService programPersonalNeedService;

    @Autowired
    private OrderProxy orderProxy;

    @Autowired
    private PersonalCenterProxy personalCenterProxy;

    @Autowired
    private SmsProxy smsProxy;

    @Autowired
    private StyleQuestionAnwserService styleQuestionAnwserService;

    @Autowired
    private StyleQuestionAnwserProxy styleQuestionAnwserProxy;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private ProgramOpinionProxy programOpinionProxy;

    private static final String CANT_SUBMIT_MESSAGE = "暂不能提交";


    @Override
    public DesignDemandToolOrderListResponse queryUserHouseListForDesignDemandTool(Integer userId) {
        List<AppHousePropertyResultDto> housePropertyResultDtoList = houseProxy.queryHouseByUserId(userId);
        if (CollectionUtils.isNotEmpty(housePropertyResultDtoList)) {
            DesignDemandToolOrderListResponse designDemandToolOrderListResponse = new DesignDemandToolOrderListResponse();
            List<QueryMasterOrderIdByHouseIdResultDto> queryMasterOrderIdByHouseIdResultDtos = productProgramOrderProxy.queryMasterOrderIdsByHouseIds(housePropertyResultDtoList.stream().map(appHousePropertyResultDto -> appHousePropertyResultDto.getHousePropertyInfoResultDto().getCustomerHouseId()).collect(toList()));
            if (CollectionUtils.isNotEmpty(queryMasterOrderIdByHouseIdResultDtos)) {
                Map<Integer, Integer> orderStatusMap = queryMasterOrderIdByHouseIdResultDtos.stream().collect(Collectors.toMap(QueryMasterOrderIdByHouseIdResultDto::getMasterOrderNum, QueryMasterOrderIdByHouseIdResultDto::getMasterOrderStatus));
                Set<Integer> orderIdSet = orderStatusMap.keySet();
                List<HousePropertyInfoResultDto> housePropertyInfoResultDtoList = housePropertyResultDtoList.stream().map(AppHousePropertyResultDto::getHousePropertyInfoResultDto).collect(toList());
                Map<Integer, HousePropertyInfoResultDto> houseOrderMap = Maps.newHashMap();
                for (QueryMasterOrderIdByHouseIdResultDto queryMasterOrderIdByHouseIdResultDto : queryMasterOrderIdByHouseIdResultDtos) {
                    for (HousePropertyInfoResultDto housePropertyInfoResultDto : housePropertyInfoResultDtoList) {
                        if (queryMasterOrderIdByHouseIdResultDto.getHouseId().intValue() == housePropertyInfoResultDto.getCustomerHouseId()) {
                            houseOrderMap.put(queryMasterOrderIdByHouseIdResultDto.getMasterOrderNum(), housePropertyInfoResultDto);
                            break;
                        }
                    }
                }
                Set<Integer> signOrderIdList = queryMasterOrderIdByHouseIdResultDtos.stream().filter(queryMasterOrderIdByHouseIdResultDto -> queryMasterOrderIdByHouseIdResultDto.getMasterOrderStatus().equals(FamilyOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus())).map(QueryMasterOrderIdByHouseIdResultDto::getMasterOrderNum).collect(Collectors.toSet());
                Map<Long, Integer> orderSubStatusMap = signOrderIdList.parallelStream().map(orderId -> orderProxy.queryOrderSummaryInfo(orderId)).collect(toMap(OrderDetailDto::getOrderNum, OrderDetailDto::getOrderSubStatus));
                Map<String, Object> dataMap = this.asynchronousQueryDataOrderData(orderIdSet);
                List<CommitDesignRequest> orderDesignDemandListForDraft = (List<CommitDesignRequest>) dataMap.get(ConcurrentTaskEnum.QUERY_DESIGN_DEMAND_DRAFT.name());
                Map<String, Object> param = Maps.newHashMap();
                param.put("orderNumList", orderIdSet);
                List<AppSolutionDesignResponseVo> designResponseVoList = (List<AppSolutionDesignResponseVo>) dataMap.get(ConcurrentTaskEnum.QUERY_DESIGN_DEMOND_FOR_ORDERLIST.name());
                if (CollectionUtils.isEmpty(designResponseVoList)) {
                    designResponseVoList = Lists.newArrayList();
                }
                List<HbmsOrderDetailDto> hbmsOrderDetailDtos = (List<HbmsOrderDetailDto>) dataMap.get(ConcurrentTaskEnum.BATCH_QUERY_MASTER_ORDER_DETAIL.name());
                if (CollectionUtils.isEmpty(hbmsOrderDetailDtos)) {
                    return null;
                }
                Map<Integer, HbmsOrderDetailDto> orderListMap = hbmsOrderDetailDtos.stream().collect(toMap(HbmsOrderDetailDto::getOrderNum, o -> o));
                Map<Integer, AppSolutionDesignResponseVo> appSolutionDesignResponseVoMap = designResponseVoList.stream().collect(Collectors.toMap(AppSolutionDesignResponseVo::getOrderNum, appSolutionDesignResponseVo -> appSolutionDesignResponseVo));
                Map<Integer, List<CommitDesignRequest>> draftGroupMap = new HashMap<>();
                if (orderDesignDemandListForDraft != null) {
                    draftGroupMap = orderDesignDemandListForDraft.stream().collect(Collectors.groupingBy(CommitDesignRequest::getOrderId));
                }
                Map<Integer, List<CommitDesignRequest>> finalDraftGroupMap = draftGroupMap;
                designDemandToolOrderListResponse.setHouseList(orderIdSet.stream().map(orderId -> {
                    HousePropertyInfoResultDto housePropertyInfoResultDto = houseOrderMap.get(orderId);
                    if (housePropertyInfoResultDto != null) {
                        List<CommitDesignRequest> commitDesignRequests = finalDraftGroupMap.get(orderId);
                        CommitDesignRequest commitDesignRequest = null;
                        if (CollectionUtils.isNotEmpty(commitDesignRequests)) {
                            commitDesignRequest = commitDesignRequests.get(0);
                        }
                        DesignDemandToolOrderListResponse.DesignDemandToolOrderSimpleInfo designDemandToolOrderSimpleInfo = new DesignDemandToolOrderListResponse.DesignDemandToolOrderSimpleInfo();
                        designDemandToolOrderSimpleInfo.setBuildingName(housePropertyInfoResultDto.getBuildingName());
                        designDemandToolOrderSimpleInfo.setHouseLayoutName(Lists.newArrayList(housePropertyInfoResultDto.getHousingNum(), housePropertyInfoResultDto.getUnitNum(), housePropertyInfoResultDto.getRoomNum()).stream().filter(StringUtils::isNotBlank).collect(Collectors.joining("-")));
                        designDemandToolOrderSimpleInfo.setOrderNum(orderId);
                        designDemandToolOrderSimpleInfo.setHouseTypeId(housePropertyInfoResultDto.getLayoutId());
                        AppSolutionDesignResponseVo appSolutionDesignResponseVo = appSolutionDesignResponseVoMap.get(orderId);
                        if (commitDesignRequest != null && (commitDesignRequest.getStatus().equals(111) || commitDesignRequest.getStatus().equals(112))) {
                            designDemandToolOrderSimpleInfo.setTaskStatus(commitDesignRequest.getStatus());//wcm有设计任务，就取wcm设计任务状态,否则取dolly设计任务状态
                            designDemandToolOrderSimpleInfo.setTaskStatusName(DesignDemondEnum.getEnumByCode(commitDesignRequest.getStatus()).getStatusStr());
                        } else if (appSolutionDesignResponseVo != null) {
                            designDemandToolOrderSimpleInfo.setTaskStatus(appSolutionDesignResponseVo.getTaskStatus());
                            designDemandToolOrderSimpleInfo.setTaskStatusName(DesignDemondEnum.getEnumByCode(appSolutionDesignResponseVo.getTaskStatus()).getStatusStr());
                        }
                        if (commitDesignRequests == null && appSolutionDesignResponseVo == null) {
                            designDemandToolOrderSimpleInfo.setTaskStatusName("尚未提交过");
                        }
                        HbmsOrderDetailDto hbmsOrderDetailDto = orderListMap.get(orderId);
                        if (hbmsOrderDetailDto != null && hbmsOrderDetailDto.getConfirmedAmount() != null && hbmsOrderDetailDto.getConfirmedAmount().compareTo(BigDecimal.ZERO) == 0) {
                            designDemandToolOrderSimpleInfo.setErrorStatus(1);
                            designDemandToolOrderSimpleInfo.setTaskStatusName(CANT_SUBMIT_MESSAGE);
                            designDemandToolOrderSimpleInfo.setErrorStatusStr("用户尚未交定金");
                        }
                        designDemandToolOrderSimpleInfo.setOrderStatus(orderStatusMap.get(orderId));
                        if (designDemandToolOrderSimpleInfo.getErrorStatus().equals(0)
                                && !designDemandToolOrderSimpleInfo.getOrderStatus().equals(FamilyOrderStatusEnum.ORDER_STATUS_DEPOSIT_PHASE.getStatus())
                                && !designDemandToolOrderSimpleInfo.getOrderStatus().equals(FamilyOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus())
                                && !designDemandToolOrderSimpleInfo.getOrderStatus().equals(FamilyOrderStatusEnum.ORDER_STATUS_CONTACT_STAGE.getStatus())
                                && !designDemandToolOrderSimpleInfo.getOrderStatus().equals(FamilyOrderStatusEnum.ORDER_STATUS_INTENTIONAL_PHASE.getStatus())) {
                            designDemandToolOrderSimpleInfo.setErrorStatus(2);
                            designDemandToolOrderSimpleInfo.setTaskStatusName(CANT_SUBMIT_MESSAGE);
                            designDemandToolOrderSimpleInfo.setErrorStatusStr("用户已签约完成");
                        } else if (designDemandToolOrderSimpleInfo.getOrderStatus().equals(FamilyOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus())) {
                            Integer subStatus = orderSubStatusMap.get(Long.parseLong(orderId.toString()));
                            if (FamilyOrderStatusEnum.ORDER_STATUS_SIGNED.getStatus().equals(subStatus)) {
                                designDemandToolOrderSimpleInfo.setTaskStatusName(CANT_SUBMIT_MESSAGE);
                                designDemandToolOrderSimpleInfo.setErrorStatusStr("用户已签约完成");
                                designDemandToolOrderSimpleInfo.setErrorStatus(2);
                            }
                        }
                        return designDemandToolOrderSimpleInfo;
                    }
                    return null;
                }).collect(toList()));
            }
            if (CollectionUtils.isNotEmpty(designDemandToolOrderListResponse.getHouseList())) {
                designDemandToolOrderListResponse.getHouseList().removeIf(designDemandToolOrderSimpleInfo -> designDemandToolOrderSimpleInfo.getOrderStatus().equals(FamilyOrderStatusEnum.ORDER_STATUS_CANCELED.getStatus()));
                for (DesignDemandToolOrderListResponse.DesignDemandToolOrderSimpleInfo designDemandToolOrderSimpleInfo : designDemandToolOrderListResponse.getHouseList()) {
                    designDemandToolOrderSimpleInfo.setTaskStatus(programPersonalNeedService.transferDesignStatus(designDemandToolOrderSimpleInfo.getTaskStatus()));
                }
            }
            return designDemandToolOrderListResponse;
        }
        return null;
    }

    public Map<String, Object> asynchronousQueryDataOrderData(Set<Integer> orderIdSet) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return designDemandToolProxy.queryDesignDemandDraft(Lists.newArrayList(orderIdSet), null, -2);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DESIGN_DEMAND_DRAFT.name();
            }
        });
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return personalNeedProxy.queryDesignDemondForOrderList(Lists.newArrayList(orderIdSet));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DESIGN_DEMOND_FOR_ORDERLIST.name();
            }
        });
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return personalCenterProxy.batchQueryMasterOrderDetail(Lists.newArrayList(orderIdSet));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.BATCH_QUERY_MASTER_ORDER_DETAIL.name();
            }
        });
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    @Override
    public List<PersonalDesignResponse> queryCommentRecord(DesignDemandToolQueryRequest request) {
        return programPersonalNeedService.queryStyleRecord(new StyleRecordRequest().setOrderId(request.getOrderId()), request.getUserId()).getStyleRecordList();
    }

    @Override
    public QueryDesignDemandInfoResponse queryDesignDemandInfo(QueryDesignDemandInfoRequest request) {

        QueryDesignDemandInfoResponse response = new QueryDesignDemandInfoResponse();
        if (request.getCommitRecordId() != null && !request.getCommitRecordId().equals(0) && request.getOrderId() != null) {
            // 取已提交记录
            response.setStyleQuestionList(styleQuestionAnwserService.queryQuestionAnwserDetail(request));
            // 暂时不显示第13个问题 准备何时开始装修
            response.setStyleQuestionList(response.getStyleQuestionList().stream().filter(o1 -> {
                if (o1.getId() != null) {
                    return !o1.getId().equals(13);
                }
                return true;
            }).collect(toList()));
            for (StyleQuestionSelectedResponse styleQuestionSelectedResponse : response.getStyleQuestionList()) {
                if (styleQuestionSelectedResponse.getId() != null && styleQuestionSelectedResponse.getId().equals(4)) {
                    response.setTaskStatus(styleQuestionSelectedResponse.getTaskStatus());
                    response.setTaskStatusStr(styleQuestionSelectedResponse.getTaskStatusStr());
                }
            }

            return response;
        } else if (request.getDesignDemandId() != null) {
            // 取未提交记录
            CommitDesignRequest designDemandInfo = designDemandToolProxy.queryDesignDemandInfo(request.getDesignDemandId());
            if (designDemandInfo == null) {
                return null;
            }
            AppMasterOrderDetailDto appMasterOrderDetailDto = personalCenterProxy.queryMasterOrderDetail(designDemandInfo.getOrderId());
            if (appMasterOrderDetailDto != null && appMasterOrderDetailDto.getCustomerInfo() != null) {
                response.setMobileNum(appMasterOrderDetailDto.getCustomerInfo().getUserMobile());
            }
            response.setTaskStatus(designDemandInfo.getStatus().equals(-2) ? 111 : designDemandInfo.getStatus());
            response.setCreateUserId(designDemandInfo.getCreateUserId());
            response.setTaskStatusStr(DesignDemondEnum.getEnumByCode(designDemandInfo.getStatus()).getStatusStr());
            Integer commitRecordId = designDemandInfo.getCommitRecordId();
            if (commitRecordId != null && !commitRecordId.equals(0)) {
                // 如果入参中没有commitRecordId，但是草稿中有，说明是用户确认后，点开了H5，此时取已提交记录
                request.setCommitRecordId(commitRecordId);
                response.setStyleQuestionList(styleQuestionAnwserService.queryQuestionAnwserDetail(request));
                // 暂时不显示第13个问题 准备何时开始装修
                response.setStyleQuestionList(response.getStyleQuestionList().stream().filter(o1 -> {
                    if (o1.getId() != null) {
                        return !o1.getId().equals(13);
                    }
                    return true;
                }).collect(toList()));
                for (StyleQuestionSelectedResponse styleQuestionSelectedResponse : response.getStyleQuestionList()) {
                    if (styleQuestionSelectedResponse.getId() != null && styleQuestionSelectedResponse.getId().equals(4)) {
                        response.setTaskStatus(styleQuestionSelectedResponse.getTaskStatus());
                        response.setTaskStatusStr(styleQuestionSelectedResponse.getTaskStatusStr());
                    }
                }
                return response;
            }

            List<StyleQuestionSelectedResponse> responses = new ArrayList<>();
            Integer version = 1;
            if (designDemandInfo != null && designDemandInfo.getVersion() != null) {
                version = designDemandInfo.getVersion();
            }
            List<StyleQuestionAnwserStepResponse> styleQuestionAnwsers = styleQuestionAnwserService.queryAllQuestionAnwserList(null, version);
            if (designDemandInfo == null) {
                return null;
            }
            Map<String, Object> obj = new HashMap<>();
            if (designDemandInfo.getDnaId() != null) {
                DNAInfoResponseVo dnaInfoResponse = homeCardBossProxy.getDnaDetailById(designDemandInfo.getDnaId());
                obj.put("dnaId", dnaInfoResponse.getDnaId());
                obj.put("dnaName", dnaInfoResponse.getDnaName());
                obj.put("dnaStyleName", dnaInfoResponse.getStyleName());
                obj.put("dnaHeadImgUrl", AliImageUtil.imageCompress(dnaInfoResponse.getHeadImgUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));

                List<CommitDesignDnaRoom> dnaRoomList = designDemandInfo.getTaskDnaRoomList();
                if (!dnaRoomList.isEmpty()) {
                    dnaRoomList.removeIf(commitDesignDnaRoom -> (commitDesignDnaRoom.getUserSelected() == null || commitDesignDnaRoom.getUserSelected() == 0));
                    dnaRoomList.parallelStream().forEach(commitDesignDnaRoom -> {
                        DNAInfoResponseVo dnaRoomResponse = homeCardBossProxy.getDnaDetailById(commitDesignDnaRoom.getDnaId());
                        if (dnaRoomResponse != null && CollectionUtils.isNotEmpty(dnaRoomResponse.getDnaRoomList())) {
                            for (DNARoomVo dnaRoomVo : dnaRoomResponse.getDnaRoomList()) {
                                if (dnaRoomVo.getRoomId().equals(commitDesignDnaRoom.getDnaRoomId())) {
                                    if (CollectionUtils.isNotEmpty(dnaRoomVo.getRoomPictureList())) {
                                        commitDesignDnaRoom.setDnaRoomPicUrl(AliImageUtil.imageCompress(dnaRoomVo.getRoomPictureList().get(0).getPictureURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                                    }
                                    commitDesignDnaRoom.setDnaRoomDesignIdea(dnaRoomVo.getRoomDescription());
                                    commitDesignDnaRoom.setRoomItemBrandList(dnaRoomVo.getRoomItemBrandList());
                                }
                            }
                        }
                        commitDesignDnaRoom.setDnaRoomUsageDesc(RoomUseEnum.getDescription(commitDesignDnaRoom.getRoomUsageId()));
                    });
                }
                obj.put("designDnaRoomList", dnaRoomList);
            }

            for (StyleQuestionAnwserStepResponse styleQuestionAnwser : styleQuestionAnwsers) {
                for (StyleQuestionAndAnwserResponse style : styleQuestionAnwser.getQuestionList()) {
                    // 跳过功能房问答，最后单独添加
                    if (style.getId() == 13) {
                        continue;
                    }
                    responses.add(initQuestions(style, designDemandInfo.getStyleQuestionAnwserList(), obj));
                    for (StyleAnwserResponse styleAnwser : style.getAnwserList()) {
                        if (styleAnwser == null || CollectionUtils.isEmpty(styleAnwser.getQuestionList())) {
                            continue;
                        }
                        for (StyleQuestionAndAnwserResponse questionAndAnwser : styleAnwser.getQuestionList()) {
                            responses.add(initQuestions(questionAndAnwser, designDemandInfo.getStyleQuestionAnwserList(), obj));
                        }
                    }
                }
            }
            responses.removeIf(styleQuestionSelectedResponse -> CollectionUtils.isEmpty(styleQuestionSelectedResponse.getAnwserList()));

            // 添加功能房问答
            List<Integer> roomUsageFilterIdList = new ArrayList<>();
            roomUsageFilterIdList.add(RoomUseEnum.getCode("主卧"));
            roomUsageFilterIdList.add(RoomUseEnum.getCode("次卧"));
            roomUsageFilterIdList.add(RoomUseEnum.getCode("儿童房"));
            roomUsageFilterIdList.add(RoomUseEnum.getCode("书房"));
            roomUsageFilterIdList.add(RoomUseEnum.getCode("老人房"));
            roomUsageFilterIdList.add(RoomUseEnum.getCode("榻榻米房"));
            List<StyleAnwserSelectedResponse> anwserList = new ArrayList<>();
            for (StyleQuestionAnwserCommitRequest styleQuestionAnwserCommitRequest : designDemandInfo.getStyleQuestionAnwserList()) {
                if (styleQuestionAnwserCommitRequest.getQuestionId() != null && styleQuestionAnwserCommitRequest.getQuestionId() == 2) {
                    for (StyleAnwserCommitRequest styleAnwserCommitRequest : styleQuestionAnwserCommitRequest.getAnwserList()) {
                        StyleAnwserSelectedResponse anwser = new StyleAnwserSelectedResponse();
                        anwser.setAnwserContent(styleAnwserCommitRequest.getAnwserContent());
                        anwserList.add(anwser);
                    }
                }
            }
            StyleQuestionSelectedResponse questionTwo = new StyleQuestionSelectedResponse();
            questionTwo.setQuestionDetail("希望拥有怎样的功能房？");
            questionTwo.setAnwserList(anwserList);
            if (CollectionUtils.isNotEmpty(anwserList)) {
                responses.removeIf(styleQuestionSelectedResponse -> styleQuestionSelectedResponse.getId() == 2);
                if (version == 1) {
                    responses.add(1, questionTwo);
                } else {
                    responses.add(getIndexByOrder(responses), questionTwo);
                }
            }
            StyleRecordResponse styleRecordResponse = programPersonalNeedService.queryStyleRecord(new StyleRecordRequest().setOrderId(request.getOrderId()), request.getUserId());
            if (styleRecordResponse != null && CollectionUtils.isNotEmpty(styleRecordResponse.getStyleRecordList())) {
                for (PersonalDesignResponse personalDesignResponse : styleRecordResponse.getStyleRecordList()) {
                    if (personalDesignResponse.getTaskStatus().equals(DesignTaskAppEnum.IN_DESIGN.getTaskStatus())){
                        response.setLastTaskIsUnderDesign(Boolean.TRUE);
                    }
                }
            }
            response.setStyleQuestionList(responses);
            return response;

        } else {
            return null;
        }
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

    public StyleQuestionSelectedResponse initQuestions(StyleQuestionAndAnwserResponse style,
                                                       List<StyleQuestionAnwserCommitRequest> styleQuestionAnwserCommitRequests,
                                                       Map obj) {
        List<StyleAnwserSelectedResponse> anwserList = new ArrayList<>();
        StyleQuestionSelectedResponse styleQuestion = new StyleQuestionSelectedResponse();
        styleQuestion.setQuestionDetail(style.getQuestionDetail());
        styleQuestion.setId(style.getId());
        styleQuestion.setCode(style.getCode());
        styleQuestion.setQuestionBrief(style.getQuestionBrief());
        styleQuestion.setQuestionDetail(style.getQuestionDetail());
        styleQuestion.setPid(style.getPid());
        styleQuestion.setSortBy(style.getSortBy());
        styleQuestion.setStep(style.getStep());
        styleQuestion.setCheckType(style.getCheckType());
        styleQuestion.setDescribe(style.getDescribe());
        styleQuestion.setRemark(style.getRemark());
        styleQuestion.setPAnwserId(style.getPAnwserId());
        styleQuestion.setIsFillSlef(style.getIsFillSlef());
        styleQuestion.setDeleteFlag(style.getDeleteFlag());
        styleQuestion.setCreateTime(style.getCreateTime());
        for (StyleQuestionAnwserCommitRequest answers : styleQuestionAnwserCommitRequests) {
            if (answers.getQuestionId() != null && answers.getQuestionId().equals(styleQuestion.getId())) {
                for (StyleAnwserCommitRequest answer : answers.getAnwserList()) {
                    StyleAnwserSelectedResponse answerSelected = new StyleAnwserSelectedResponse();
                    answerSelected.setQuestionId(answers.getQuestionId());
                    answerSelected.setAnwserType(0);
                    answerSelected.setAnwserContent(answer.getAnwserContent());
                    answerSelected.setAnwserId(answer.getAnwserId());
                    anwserList.add(answerSelected);
                }

            }
        }
        if (styleQuestion.getId() == 4 && !anwserList.isEmpty()) {
            anwserList.get(0).setObj(obj);
            anwserList.get(0).setAnwserType(1);
        }

        styleQuestion.setAnwserList(anwserList);
        return styleQuestion;
    }

    @Override
    public String addOrUpdateDesignDraft(CommitDesignRequest commitDesignRequest) {
        commitDesignRequest.setVersion(2);
        return designDemandToolProxy.addOrUpdateDesignDraft(commitDesignRequest).getId().toString();
    }

    /**
     * 小艾发送给用户待确认的设计需求
     *
     * @param request
     * @return
     */
    @Override
    public DesignDemandToolQueryRequest sendToUserAffirm(CommitDesignRequest request) {
        request.setStatus(112);
        String designDemandId = this.addOrUpdateDesignDraft(request);
        AppMasterOrderDetailDto appMasterOrderDetailDto = personalCenterProxy.queryMasterOrderDetail(request.getOrderId());
        DesignDemandToolQueryRequest designDemandToolQueryRequest = new DesignDemandToolQueryRequest().setOrderId(request.getOrderId()).setDesignDemandId(designDemandId);
        if (appMasterOrderDetailDto != null && appMasterOrderDetailDto.getHouseInfo() != null) {
            designDemandToolQueryRequest.setHouseTypeId(appMasterOrderDetailDto.getHouseInfo().getLayoutId());
            designDemandToolQueryRequest.setBuildingName(appMasterOrderDetailDto.getHouseInfo().getBuildingName());
            designDemandToolQueryRequest.setHouseNumber(appMasterOrderDetailDto.getHouseInfo().getShortHousePropertyInfo());
        }
        return designDemandToolQueryRequest;
    }

    /**
     * 用户确认设计需求
     *
     * @param request
     * @return
     */
    @Override
    public DesignDemandToolQueryRequest affirmDesignDemand(DesignDemandToolQueryRequest request) {
        request.setOpSource(1);
        return commonAffirmDesignDemand(request);
    }

    /**
     * 用户确认设计需求
     * <p>
     * 0.待设计 1.设计中 2.设计完成
     * DRAFT("待确认设计需求",-2),
     * DRAFT_COVER("被覆盖",-3),
     * UNNEED_DESIGN("无需分配",-1),
     * WAIT_DESIGN("待分配", 0),
     * DESIGNING("设计中", 1),
     * DESIGNED("设计完成", 2),
     * WAIT_ACCEPT("待确认",3),
     * INVALID("已失效",4),
     * AUDITING("审核中",5),
     * UNPASS("审核不通过",6),
     * FINALL_END("已终结",7)
     *
     * @param request
     * @return
     */
    @Override
    public DesignDemandToolQueryRequest affirmDesignDemandByXa(DesignDemandToolQueryRequest request) {
        // 仅-2状态能确认
        CommitDesignRequest designDemandInfo = designDemandToolProxy.queryDesignDemandInfo(request.getDesignDemandId());
        if (designDemandInfo == null || designDemandInfo.getStatus() == null ||
                designDemandInfo.getStatus() != -2) {
            throw new BusinessException(HttpReturnCode.XA_CONFIRM_FAIL, MessageConstant.XA_CONFIRM_FAIL);
        } else {
            request.setOpSource(2);
            return commonAffirmDesignDemand(request);
        }
    }

    public DesignDemandToolQueryRequest commonAffirmDesignDemand(DesignDemandToolQueryRequest request) {
        UserDto userByMobile = userProxy.getUserByMobile(request.getMobile());
        CommitDesignRequest designDemandInfoDto = designDemandToolProxy.queryDesignDemandInfo(request.getDesignDemandId());
        if (designDemandInfoDto != null && designDemandInfoDto != null) {
            CommitDesignRequest designDemandInfo = designDemandInfoDto;
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userByMobile == null ? null : userByMobile.getId());
            params.put("orderNum", designDemandInfo.getOrderId());
            params.put("mobile", request.getMobile());
            params.put("taskId", designDemandInfo.getId());
//            dolly涞源枚举
//            1 客户确认 ，2 运营确认，3 betaApp用户确认，4 betaApp运营确认
            Integer source = null;
            if (designDemandInfo.getSubmitSource() != null) {
                if (designDemandInfo.getSubmitSource().equals(1)) {
                    if (request.getOpSource().equals(1)) {
                        source = 1;
                    } else if (request.getOpSource().equals(2)) {
                        source = 2;
                    }
                } else if (designDemandInfo.getSubmitSource().equals(3)) {
                    if (request.getOpSource().equals(1)) {
                        source = 3;
                    } else if (request.getOpSource().equals(2)) {
                        source = 4;
                    }
                }
            }
            params.put("source", source);
            params.put("submitterUserId", designDemandInfo.getCreateUserId());
            if (StringUtils.isNotBlank(designDemandInfo.getBudget())) {
                params.put("budget", designDemandInfo.getBudget());
            }
            if (designDemandInfo.getDnaId() != null) {
                params.put("dnaId", designDemandInfo.getDnaId());
            }
            if (CollectionUtils.isNotEmpty(designDemandInfo.getDnaRoomList())) {
                List<CommitDesignDnaRoom> roomList = designDemandInfo.getDnaRoomList();
                for (CommitDesignDnaRoom room : roomList) {
                    if (RoomUseEnum.ROOM_MULTIPLE_FUNC.getCode().equals(room.getRoomUsageId())) {
                        room.setDnaId(605);
                    }
                }
                params.put("dnaRoomList", roomList);
            }
            List<StyleAnwserResponse> styleAnwserResponses = styleQuestionAnwserProxy.queryAllAnwserList(Maps.newHashMap());
            Map<Integer, String> anwseCodeMap = null;
            if (CollectionUtils.isNotEmpty(styleAnwserResponses)) {
                anwseCodeMap = styleAnwserResponses.stream().collect(Collectors.toMap(StyleAnwserResponse::getId, StyleAnwserResponse::getCode));
            }
            // 提交风格答案集
            List<StyleQuestionAnwserCommitRequest> questionAnwsers = designDemandInfo.getStyleQuestionAnwserList();
            if (CollectionUtils.isNotEmpty(questionAnwsers)) {

                StyleQuestionAnwserMapResponse questionAnwserMap = styleQuestionAnwserProxy.queryAllQuestionAnwserMap(designDemandInfo.getVersion() == null ? 1 : designDemandInfo.getVersion());
                if (null != questionAnwserMap && !org.springframework.util.CollectionUtils.isEmpty(questionAnwserMap.getStyleQuestionMap())
                        && !org.springframework.util.CollectionUtils.isEmpty(questionAnwserMap.getStyleAnwserMap())) {
                    Map<String, StyleQuestionDto> styleQuestionMap = questionAnwserMap.getStyleQuestionMap();

                    List<Map<String, Object>> remarkList = new ArrayList<>();
                    for (StyleQuestionAnwserCommitRequest item : questionAnwsers) {
                        if (request.getOrderId() != null) {
                            item.setOrderNum(request.getOrderId());
                        }
                        if (designDemandInfo.getOrderId() != null) {
                            item.setOrderNum(designDemandInfo.getOrderId());
                        }
                        item.setUserId(userByMobile == null ? null : userByMobile.getId());
                        Map<String, Object> remarkMap = new HashMap<>();
                        String questionId = "" + item.getQuestionId();
                        List<StyleAnwserCommitRequest> anwserCommitRequest = item.getAnwserList();
                        if (styleQuestionMap.containsKey(questionId) && CollectionUtils.isNotEmpty(anwserCommitRequest)) {
                            StyleQuestionDto questionDto = styleQuestionMap.get(questionId);
                            remarkMap.put("question", questionDto.getQuestionBrief());
                            remarkMap.put("questionCode", questionDto.getCode());
                            List<String> anwserList = new ArrayList<>();
                            List<Map<String, String>> answerMaps = Lists.newArrayList();
                            for (StyleAnwserCommitRequest anwser : anwserCommitRequest) {
                                anwserList.add(anwser.getAnwserContent());
                                Map<String, String> map = Maps.newHashMap();
                                map.put("anwserCode", anwseCodeMap.get(anwser.getAnwserId()));
                                map.put("anwser", anwser.getAnwserContent());
                                answerMaps.add(map);
                            }
                            remarkMap.put("answers", anwserList);
                            remarkMap.put("answerMaps", answerMaps);
                        }
                        if (remarkMap.get("question") != null) {
                            remarkList.add(remarkMap);
                        }
                    }
                    params.put("remark", JSON.toJSONString(remarkList, SerializerFeature.DisableCheckSpecialChar));
                }
            } else {
                params.put("remark", designDemandInfo.getRemark());
            }
            AppMasterOrderDetailDto appMasterOrderDetailDto = personalCenterProxy.queryMasterOrderDetail(designDemandInfo.getOrderId());
            HousePropertyInfoResultDto houseInfo = appMasterOrderDetailDto.getHouseInfo();
            Integer layoutId = houseInfo.getLayoutId();//原始户型id
            Map<String, Object> map = new HashMap<>();
            map.put("houseId", layoutId);

            if (designDemandInfo.getNewLayoutId() != null) {
                params.put("houseId", designDemandInfo.getNewLayoutId());
            } else {
                ApartmentInfoVo houseInfoByApartmentIdNew = homeCardBossProxy.getHouseInfoByApartmentIdNew(map);
                if (houseInfoByApartmentIdNew != null) {
                    params.put("houseId", houseInfoByApartmentIdNew.getHouseId());
                }
            }
            CommitDesignDemandVo commitResult = personalNeedProxy.commitDesignDemandByCustomerService(params);
            if (commitResult == null || commitResult.getErrorCode() != 1) {
                String msg = null;
                Integer errorCode = (commitResult == null || null == commitResult.getErrorCode()) ? -1 : commitResult.getErrorCode();
                switch (errorCode) {
                    case -1001:
                        msg = "您提交的设计需求已在设计中了";
                        break;
                    case -1002:
                        msg = "您已提交了设计需求哦";
                        break;
                    default:
                        msg = "提交失败";
                }
                throw new BusinessException(errorCode.longValue(), msg);
            }
            if (commitResult != null && 1 == commitResult.getErrorCode() && CollectionUtils.isNotEmpty(questionAnwsers)) {
                StyleQuestionAnwserCommitNewRequest questionAnwserRequest = new StyleQuestionAnwserCommitNewRequest();
                questionAnwserRequest.setStyleQuestionAnwserCommitRequestList(questionAnwsers);
                questionAnwserRequest.setTaskId(commitResult.getTaskId());
                DesignDemandToolQueryRequest result = new DesignDemandToolQueryRequest().setTaskId(commitResult.getTaskId()).setDesignDemandId(designDemandInfo.getDesignDemandId()).setCommitRecordId(commitResult.getTaskId()).setUserId(userByMobile == null ? null : userByMobile.getId()).setOrderId(designDemandInfo.getOrderId());
                if (appMasterOrderDetailDto != null) {
                    result.setHouseNumber(Lists.newArrayList(houseInfo.getHousingNum(), houseInfo.getUnitNum(), houseInfo.getRoomNum()).stream().filter(StringUtils::isNotBlank).collect(Collectors.joining("-")));
                    result.setBuildingName(houseInfo.getBuildingName());
                    result.setHouseTypeId(layoutId);
                }
                return result;
            }
        }
        return null;
    }

    @Override
    public SimpleDataForBetaAppResponse queryProgramOpAndDesignDemandByOrderId(DesignDemandToolQueryRequest request) {
        SimpleDataForBetaAppResponse response = new SimpleDataForBetaAppResponse();
        AppMasterOrderDetailDto appMasterOrderDetailDto = personalCenterProxy.queryMasterOrderDetail(request.getOrderId());
        if (appMasterOrderDetailDto != null && appMasterOrderDetailDto.getCustomerInfo() != null) {
            response.setUserId(appMasterOrderDetailDto.getCustomerInfo().getUserId());
        }
        List<ProgramOpinionDetailDto> programOpinionDetailList = programOpinionProxy.queryUnConfirmOpinionList(new ProgramOpinionRequest().setOrderNum(request.getOrderId()).setCreateUserId(request.getCreateUserId()));
        if (CollectionUtils.isNotEmpty(programOpinionDetailList)) {
            ProgramOpinionDetailDto programOpinionDetailDto = programOpinionDetailList.get(0);
            response.setReviseOpinionDtoSimpleInfo(new ReviseOpinionDtoSimpleInfo());
            response.getReviseOpinionDtoSimpleInfo().setOrderNum(programOpinionDetailDto.getOrderId());
            response.getReviseOpinionDtoSimpleInfo().setSolutionId(programOpinionDetailDto.getSolutionId());
            response.getReviseOpinionDtoSimpleInfo().setProgramOpinionId(programOpinionDetailDto.getId());
            if (appMasterOrderDetailDto != null) {
                response.getReviseOpinionDtoSimpleInfo().setUserId(appMasterOrderDetailDto.getCustomerInfo().getUserId());
            }
        }
        List<CommitDesignRequest> orderDesignDemandListForDraft = designDemandToolProxy.queryDesignDemandDraft(Lists.newArrayList(request.getOrderId()), request.getCreateUserId(), -2);
        if (orderDesignDemandListForDraft != null && CollectionUtils.isNotEmpty(orderDesignDemandListForDraft)) {
            CommitDesignRequest commitDesignRequest = orderDesignDemandListForDraft.get(0);
            response.setDesignDemandSimpleInfo(new DesignDemandSimpleInfo());
            response.getDesignDemandSimpleInfo().setCommitRecordId(commitDesignRequest.getId());
            response.getDesignDemandSimpleInfo().setCreateUserId(commitDesignRequest.getCreateUserId());
            response.getDesignDemandSimpleInfo().setOrderId(commitDesignRequest.getOrderId());
            response.getDesignDemandSimpleInfo().setDesignDemandId(commitDesignRequest.getId().toString());
            if (appMasterOrderDetailDto != null) {
                response.getDesignDemandSimpleInfo().setUserId(appMasterOrderDetailDto.getCustomerInfo().getUserId());
                if (appMasterOrderDetailDto.getHouseInfo() != null) {
                    response.getDesignDemandSimpleInfo().setHouseTypeId(appMasterOrderDetailDto.getHouseInfo().getLayoutId());
                }
            }
        }
        return response;
    }
}
