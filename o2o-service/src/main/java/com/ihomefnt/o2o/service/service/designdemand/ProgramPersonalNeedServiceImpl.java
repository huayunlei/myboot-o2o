package com.ihomefnt.o2o.service.service.designdemand;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.ihomefnt.cms.utils.ModelMapperUtil;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.o2o.constant.DesignDemondEnum;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.art.dto.KeywordVo;
import com.ihomefnt.o2o.intf.domain.common.http.CopyWriterConstant;
import com.ihomefnt.o2o.intf.domain.homecard.dto.*;
import com.ihomefnt.o2o.intf.domain.homecard.vo.response.HouseRoomVo;
import com.ihomefnt.o2o.intf.domain.main.vo.SolutionInfo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.*;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.*;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.*;
import com.ihomefnt.o2o.intf.domain.program.dto.KeywordListResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomPictureInfo;
import com.ihomefnt.o2o.intf.domain.program.dto.StyleRemarkResultDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderDetailDto;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.CommitDesignDnaRoom;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.CopyWriterAndValue;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.FamilyOrderPayResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.request.StyleAnwserCommitRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.request.StyleQuestionAnwserCommitNewRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.request.StyleQuestionAnwserCommitRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleAnwserResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleCommitRecordResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionAnwserMapResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionDto;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.personalneed.*;
import com.ihomefnt.o2o.intf.manager.constant.programorder.RoomUseEnum;
import com.ihomefnt.o2o.intf.manager.constant.user.UserConstants;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.IntegerUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.*;
import com.ihomefnt.o2o.intf.manager.util.order.FamilyOrderStatus;
import com.ihomefnt.o2o.intf.proxy.designdemand.PersonalNeedProxy;
import com.ihomefnt.o2o.intf.proxy.designdemand.StyleQuestionAnwserProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardBossProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.program.KeywordWcmProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.designDemand.ProgramPersonalNeedService;
import com.ihomefnt.o2o.service.manager.config.ApiConfig;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: ZHAO
 * Date: 2018年5月25日
 */
@Service
public class ProgramPersonalNeedServiceImpl implements ProgramPersonalNeedService {
    private static final Logger LOG = LoggerFactory.getLogger(ProgramPersonalNeedServiceImpl.class);

    @Autowired
    private PersonalNeedProxy personalNeedProxy;

    @Autowired
    private KeywordWcmProxy keywordWcmProxy;

    @Autowired
    OrderProxy orderProxy;

    @Autowired
    ApiConfig apiConfig;

    @Resource
    private ServiceCaller serviceCaller;

    @Autowired
    private StyleQuestionAnwserProxy styleQuestionAnwserProxy;

    @Autowired
    UserProxy userProxy;

    @Autowired
    private HomeCardBossProxy homeCardBossProxy;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Override
    public boolean checkUserDemond(Integer orderId, Integer userId) {
        Map<String, Object> params = new HashMap<>();
        if (orderId != null && orderId > 0L) {
            params.put("orderNum", orderId);
        }
        if (IntegerUtil.isNotEmpty(userId)) {
            params.put("userId", userId);
        }
        return personalNeedProxy.checkUserDemond(params);
    }

    @Override
    public CommitDesignDemandVo commitDesignDemand(CommitDesignRequest request, Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("orderNum", request.getOrderId());
        params.put("mobile", request.getMobileNum());
        params.put("createUserId", userId);
        params.put("houseId", request.getNewLayoutId());
        if (StringUtils.isNotBlank(request.getBudget())) {
            params.put("budget", request.getBudget());
        }
        if (request.getDnaId() != null) {
            params.put("dnaId", request.getDnaId());
        }
        if (CollectionUtils.isNotEmpty(request.getDnaRoomList())) {
            // TODO 临时处理  20190109
            List<CommitDesignDnaRoom> roomList = request.getDnaRoomList();
            for (CommitDesignDnaRoom room : roomList) {
                if (RoomUseEnum.ROOM_MULTIPLE_FUNC.getCode().equals(room.getRoomUsageId())) {
                    room.setDnaId(605);
                }
            }
            params.put("dnaRoomList", roomList);
        }
        Integer version = 1;
        if (request.getAppVersion() != null && VersionUtil.mustUpdate(request.getAppVersion(), "5.5.3")) {//老版本
            version = 1;
        } else {
            version = 2;
        }
        // 提交风格答案集
        List<StyleQuestionAnwserCommitRequest> questionAnwsers = request.getStyleQuestionAnwserList();
        if (CollectionUtils.isNotEmpty(questionAnwsers)) {
            StyleQuestionAnwserMapResponse questionAnwserMap = styleQuestionAnwserProxy.queryAllQuestionAnwserMap(version);
            LOG.info("commitDesignDemand.queryAllQuestionAnwserMap response {} ", JsonUtils.obj2json(questionAnwserMap));
            List<StyleAnwserResponse> styleAnwserResponses = styleQuestionAnwserProxy.queryAllAnwserList(Maps.newHashMap());
            Map<Integer, String> anwseCodeMap = null;
            if (CollectionUtils.isNotEmpty(styleAnwserResponses)) {
                anwseCodeMap = styleAnwserResponses.stream().collect(Collectors.toMap(StyleAnwserResponse::getId, StyleAnwserResponse::getCode));
            }
            if (null != questionAnwserMap && !org.springframework.util.CollectionUtils.isEmpty(questionAnwserMap.getStyleQuestionMap())
                    && !org.springframework.util.CollectionUtils.isEmpty(questionAnwserMap.getStyleAnwserMap())) {
                Map<String, StyleQuestionDto> styleQuestionMap = questionAnwserMap.getStyleQuestionMap();

                List<Map<String, Object>> remarkList = new ArrayList<>();
                for (StyleQuestionAnwserCommitRequest item : questionAnwsers) {
                    item.setOrderNum(request.getOrderId());
                    item.setUserId(userId);
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
                params.put("remark", JsonUtils.obj2JsonFeature(remarkList, SerializerFeature.DisableCheckSpecialChar));
            }
        } else {
            params.put("remark", request.getRemark());
        }
        CommitDesignDemandVo commitResult = personalNeedProxy.commitDesignDemand(params);

        if (commitResult != null && 1 == commitResult.getErrorCode() && CollectionUtils.isNotEmpty(questionAnwsers)) {
            StyleQuestionAnwserCommitNewRequest questionAnwserRequest = new StyleQuestionAnwserCommitNewRequest();
            questionAnwserRequest.setStyleQuestionAnwserCommitRequestList(questionAnwsers);
            questionAnwserRequest.setTaskId(commitResult.getTaskId());
            questionAnwserRequest.setVersion(version);
            styleQuestionAnwserProxy.commitStyleQuestionAnwser(questionAnwserRequest);
            commitResult.setStyleCommitRecordId(commitResult.getTaskId());
        }

        return commitResult;
    }

    @Override
    public PersonalDesignResponse queryDesignDemond(Integer orderId, Integer width, String mobile, Integer osType) {
        PersonalDesignResponse designResponse = new PersonalDesignResponse();

        if (width != 0) {
            width = width * ImageSize.WIDTH_PER_SIZE_38 / ImageSize.WIDTH_PER_SIZE_100;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("orderNum", orderId);
        params.put("mobile", mobile);
        OrderDetailDto orderDetailDto = orderProxy.queryOrderSummaryInfo(orderId);
        SolutionInfo solutionInfo = productProgramOrderProxy.querySolutionInfo(orderId);
        AppSolutionDesignResponseVo designResponseVo = personalNeedProxy.queryDesignDemond(params);
        if (solutionInfo != null) {
            designResponse.setIsLoan(solutionInfo.getContainLoan().equals(1));
        }
        if (orderDetailDto != null) {
            designResponse.setOrderSubStatus(orderDetailDto.getOrderSubStatus());
            if (((orderDetailDto.getOrderStatus().equals(13) || orderDetailDto.getOrderStatus().equals(14)) && orderDetailDto.getFundAmount().compareTo(BigDecimal.ZERO) > 0) || orderDetailDto.getOrderStatus().equals(15)) {
                designResponse.setOrderStatus(15);
            } else if (orderDetailDto.getOrderStatus().equals(16)) {
                designResponse.setOrderStatus(16);
            } else if (orderDetailDto.getOrderStatus().equals(12) || orderDetailDto.getOrderStatus().equals(17) || orderDetailDto.getOrderStatus().equals(2)) {
                designResponse.setOrderStatus(17);
            } else {
                designResponse.setOrderStatus(-1);
            }
        }
        if (designResponseVo != null && StringUtils.isNotBlank(designResponseVo.getUserTags())) {
            designResponse.setOrderId(orderId);
            if (StringUtils.isNotBlank(designResponseVo.getBudget())) {
                designResponse.setBudget(designResponseVo.getBudget());
            }
            if (StringUtils.isNotBlank(designResponseVo.getRemark())) {
                designResponse.setRemark(designResponseVo.getRemark());
            }
            if (StringUtils.isNotBlank(designResponseVo.getHardQuality())) {
                designResponse.setHardQuality(designResponseVo.getHardQuality());
            }
            if (StringUtils.isNotBlank(designResponseVo.getUserTags())) {
                designResponse.setUserTags(JsonUtils.json2list(designResponseVo.getUserTags(), String.class));
            }
            if (CollectionUtils.isNotEmpty(designResponseVo.getProspectPictureList())) {
                List<ArtisticEntity> artisticList = new ArrayList<>();//意境集合
                for (DNAProspectPictureVo pictureVo : designResponseVo.getProspectPictureList()) {
                    ArtisticEntity artisticEntity = new ArtisticEntity();
                    if (StringUtils.isNotBlank(pictureVo.getPictureTag())) {
                        artisticEntity.setArtistic(pictureVo.getPictureTag());
                    }
                    if (StringUtils.isNotBlank(pictureVo.getPictureURL())) {
                        artisticEntity.setArtisticImgUrl(AliImageUtil.imageCompress(pictureVo.getPictureURL(), osType, width, ImageConstant.SIZE_MIDDLE));
                    }
                    artisticList.add(artisticEntity);
                }
                designResponse.setArtisticList(artisticList);
            }
            designResponse.setTaskDnaRoomList(designResponseVo.getTaskDnaRoomList());
        }
        return designResponse;
    }

    @Override
    public List<PersonalArtisticResponse> randomQueryDna(Integer width) {
        List<PersonalArtisticResponse> artisticResponses = new ArrayList<>();

        Integer artisticWidth = 0;
        if (width != 0) {
            artisticWidth = width * ImageSize.WIDTH_PER_SIZE_38 / ImageSize.WIDTH_PER_SIZE_100;
        }

        List<DnaInfoResponseVo> dnaInfoResponseVos = personalNeedProxy.randomQueryDna(2);
        if (CollectionUtils.isNotEmpty(dnaInfoResponseVos)) {
            for (DnaInfoResponseVo dnaInfoResponseVo : dnaInfoResponseVos) {
                PersonalArtisticResponse artisticResponse = new PersonalArtisticResponse();
                artisticResponse.setDnaId(dnaInfoResponseVo.getDnaId());
                if (StringUtils.isNotBlank(dnaInfoResponseVo.getDnaName())) {
                    artisticResponse.setDnaName(dnaInfoResponseVo.getDnaName());
                }
                if (StringUtils.isNotBlank(dnaInfoResponseVo.getStyleName())) {
                    artisticResponse.setStyle(dnaInfoResponseVo.getStyleName());
                }
                List<DNAProspectPictureVo> prospectPictureList = dnaInfoResponseVo.getProspectPictureList();//意境图列表
                if (CollectionUtils.isNotEmpty(prospectPictureList)) {
                    List<ArtisticEntity> artisticList = new ArrayList<>();//意境集合
                    for (DNAProspectPictureVo pictureVo : prospectPictureList) {
                        ArtisticEntity artisticEntity = new ArtisticEntity();
                        if (StringUtils.isNotBlank(pictureVo.getPictureTag())) {
                            artisticEntity.setArtistic(pictureVo.getPictureTag());
                        }
                        if (StringUtils.isNotBlank(pictureVo.getPictureURL())) {
                            artisticEntity.setArtisticImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(pictureVo.getPictureURL(), artisticWidth, -1));
                        }
                        artisticList.add(artisticEntity);
                    }
                    artisticResponse.setArtisticList(artisticList);
                }

                List<DNARoomPictureVo> roomPictureList = dnaInfoResponseVo.getRoomPictureList();//空间效果图列表
                if (CollectionUtils.isNotEmpty(roomPictureList)) {
                    List<RoomPictureInfo> roomImageList = new ArrayList<>();//空间照片集合
                    for (DNARoomPictureVo dnaRoomPictureVo : roomPictureList) {
                        if (StringUtils.isNotBlank(dnaRoomPictureVo.getPictureURL())) {
                            RoomPictureInfo roomPictureInfo = new RoomPictureInfo();
                            roomPictureInfo.setPictureURL(QiniuImageUtils.compressImageAndSamePicTwo(dnaRoomPictureVo.getPictureURL(), width, -1));
                            if (StringUtils.isNotBlank(dnaRoomPictureVo.getRoomUsageName())) {
                                roomPictureInfo.setRoomUsageName(dnaRoomPictureVo.getRoomUsageName());
                            }
                            if (dnaRoomPictureVo.getIsFirst() != null && dnaRoomPictureVo.getIsFirst().equals(1)) {
                                //首图
                                artisticResponse.setHeadImgUrl(roomPictureInfo.getPictureURL());
                            }
                            roomImageList.add(roomPictureInfo);
                        }
                    }
                    artisticResponse.setRoomImageList(roomImageList);
                }
                artisticResponses.add(artisticResponse);
            }
        }
        return artisticResponses;
    }

    @Override
    public List<String> queryPersonalTagList() {
        List<String> tagList = new ArrayList<>();

        List<String> param = new ArrayList<>();
        param.add("个性标签");
        KeywordListResponseVo listResponseVo = keywordWcmProxy.getKeywordList(param);
        if (listResponseVo != null && CollectionUtils.isNotEmpty(listResponseVo.getKeywordList())) {
            for (KeywordVo keywordVo : listResponseVo.getKeywordList()) {
                if (keywordVo != null && CollectionUtils.isNotEmpty(keywordVo.getWords())) {
                    for (String wordStr : keywordVo.getWords()) {
                        tagList.add(wordStr);
                    }
                }
            }
        }
        return tagList;
    }

    /**
     * 查询设计需求记录列表
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public StyleRecordResponse queryStyleRecord(StyleRecordRequest request,Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNum", request.getOrderId());
        Map<String, Object> resultMap = concurrentStyleRecordAndOrderInfo(params);
        List<AppSolutionDesignResponseVo> recordList = (List<AppSolutionDesignResponseVo>) resultMap.get(ConcurrentTaskEnum.QUERY_DESIGN_DEMOND_HISTORY_INFO.name());

        OrderDetailDto orderDetailDto = (OrderDetailDto) resultMap.get(ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name());

        SolutionInfo solutionInfo = (SolutionInfo) resultMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name());
        FamilyOrderPayResponse familyOrderPayResponse = queryPayBaseInfo(orderDetailDto);
        StyleRecordResponse response = null;
        if (recordList != null) {
            // 根据订单查询风格问题提交记录
            List<StyleCommitRecordResponse> styleCommitRecordList = styleQuestionAnwserProxy.queryStyleCommitRecordList(request.getOrderId());
            response = new StyleRecordResponse();
            if (isFinalAndPaidAmountEmpty(familyOrderPayResponse)) {
                if (familyOrderPayResponse.getFinalOrderPrice().getValue().compareTo(familyOrderPayResponse.getPaidAmount()) == 0) {
                    response.setAllMoney(1);
                } else if (familyOrderPayResponse.getFinalOrderPrice().getValue().compareTo(familyOrderPayResponse.getPaidAmount()) == -1) {
                    response.setAllMoney(2);
                }
            }
            List<PersonalDesignResponse> designResponseList = new ArrayList<>();
            List<Integer> solutionReadListByUserId = productProgramProxy.queryUserSolutionReadListByUserId(userId);
            for (int i = 0; i < recordList.size(); i++) {
                AppSolutionDesignResponseVo designResponseVo = recordList.get(i);
                PersonalDesignResponse designResponse = new PersonalDesignResponse();
                transformRecordVo(designResponse, designResponseVo, request.getOrderId(), request.getWidth(), request.getOsType());
                designResponse.setSolutionsHasRead(designResponse.getSolutionId() != null && CollectionUtils.isNotEmpty(solutionReadListByUserId) && solutionReadListByUserId.contains(designResponse.getSolutionId()));
                designResponseList.add(designResponse);
                if (CollectionUtils.isNotEmpty(styleCommitRecordList)) {
                    int count = 0;
                    for (StyleCommitRecordResponse styleCommitRecordResponse : styleCommitRecordList) {
                        if (styleCommitRecordResponse.getTaskId() != null && styleCommitRecordResponse.getTaskId().intValue() != 0
                                && designResponseVo != null && designResponseVo.getTaskId() != null && designResponseVo.getTaskId().intValue() != 0
                                && designResponseVo.getTaskId().equals(styleCommitRecordResponse.getTaskId())) {
                            setStyleCommitRecordAndRemark(designResponse, styleCommitRecordResponse);
                            count++;
                        }
                    }
                    if (count == 0 && i < styleCommitRecordList.size()) {
                        setStyleCommitRecordAndRemark(designResponse, styleCommitRecordList.get(i));
                    }
                }
                // remark
                checkAndSetResponseRemark(designResponse);
            }
            response.setStyleRecordList(designResponseList);
            response.setOrderSubStatus(orderDetailDto.getOrderSubStatus());
            if (((orderDetailDto.getOrderStatus().equals(13) || orderDetailDto.getOrderStatus().equals(14)) && orderDetailDto.getFundAmount().compareTo(BigDecimal.ZERO) > 0) || orderDetailDto.getOrderStatus().equals(15)) {
                response.setOrderStatus(15);
            } else if (orderDetailDto.getOrderStatus().equals(16)) {
                response.setOrderStatus(16);
            } else if (orderDetailDto.getOrderStatus().equals(12) || orderDetailDto.getOrderStatus().equals(17) || orderDetailDto.getOrderStatus().equals(2)) {
                response.setOrderStatus(17);
            } else {
                response.setOrderStatus(-1);
            }
            response.setIsLoan(solutionInfo != null && solutionInfo.getContainLoan().equals(1));
        }
        return response;
    }

    /**
     * 判断订单金额和已付金额是否为空
     *
     * @param familyOrderPayResponse
     * @return
     */
    private boolean isFinalAndPaidAmountEmpty(FamilyOrderPayResponse familyOrderPayResponse) {
        return familyOrderPayResponse != null && familyOrderPayResponse.getFinalOrderPrice() != null && familyOrderPayResponse.getFinalOrderPrice().getValue() != null
                && familyOrderPayResponse.getFinalOrderPrice().getValue().compareTo(BigDecimal.ZERO) != 0 && familyOrderPayResponse.getPaidAmount() != null;
    }

    /**
     * 收银台内容组装
     *
     * @param orderDetailDto
     * @return
     */
    private FamilyOrderPayResponse queryPayBaseInfo(OrderDetailDto orderDetailDto) {
        if (orderDetailDto != null) {
            FamilyOrderPayResponse response = new FamilyOrderPayResponse();
            response.setUpItemAmount(orderDetailDto.getUpItemAmount());//选配升级项金额
            response.setOrderStatus(FamilyOrderStatus.getOrderStatus(orderDetailDto.getOrderStatus()));
            response.setAdviserName(StringUtils.isBlank(orderDetailDto.getAdviserName()) ? null : orderDetailDto.getAdviserName());
            response.setAdviserMobile(StringUtils.isBlank(orderDetailDto.getAdviserMobile()) ? null : orderDetailDto.getAdviserMobile());
            response.setOrderSource(orderDetailDto.getSource());
            response.setGradeId(orderDetailDto.getGradeId());
            response.setGradeName(orderDetailDto.getGradeName());
            //其他优惠
            response.setOtherDisAmount(orderDetailDto.getNewOtherDisAmount() == null ? BigDecimal.ZERO : orderDetailDto.getNewOtherDisAmount());

            // 艾升级券面值
            response.setUpGradeCouponAmount(orderDetailDto.getUpGradeCouponAmount() == null ? BigDecimal.ZERO : orderDetailDto.getUpGradeCouponAmount());
            // 全品家立减金额
            response.setRightsDiscountAmount(orderDetailDto.getRightsDiscountAmount() == null ? BigDecimal.ZERO : orderDetailDto.getRightsDiscountAmount());

            //权益可抵扣金额，若已确认方案，则为已抵扣金额
            response.setRightAmount(orderDetailDto.getUpItemDeAmount() == null ? BigDecimal.ZERO : orderDetailDto.getUpItemDeAmount());

            //用户已付金额
            response.setPaidAmount(orderDetailDto.getFundAmount() == null ? BigDecimal.ZERO : orderDetailDto.getFundAmount());

            //实际合同额
            BigDecimal contractAmount = null;
            //已确认收款金额
            response.setConfirmReapAmount(orderDetailDto.getConfirmedAmount() == null ? BigDecimal.ZERO : orderDetailDto.getConfirmedAmount());

            //剩余应付
            BigDecimal restPay = BigDecimal.ZERO;

            //方案总价
            BigDecimal solutionPrice = BigDecimal.ZERO;

            if (IntegerUtil.isNullOrZero(orderDetailDto.getPreConfirmed())) {
                //方案未确认
                //app订单总价  = beta订单总价- 艾升级权益
                response.setFinalOrderPrice(CopyWriterAndValue.build(CopyWriterConstant.Order.ORDER_TOTAL_PRICE, orderDetailDto.getOrderTotalAmount() == null || orderDetailDto.getOrderTotalAmount().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : orderDetailDto.getOrderTotalAmount().subtract(orderDetailDto.getUpItemDeAmount() == null ? BigDecimal.ZERO : orderDetailDto.getUpItemDeAmount())));
                //由于老订单一些返现活动导致订单总价和合同价不一致，取合同价计算会出现剩余应付错误，故此处修改为使用订单总价来计算，废除合同额
                contractAmount = response.getFinalOrderPrice().getValue();
                if (orderDetailDto.getOriginalOrderAmount() == null) {
                    //订单未记录原始方案价格，则原始方案价格视为实际合同额
                    solutionPrice = contractAmount;
                } else {
                    solutionPrice = orderDetailDto.getOriginalOrderAmount();
                }

                //则前端显示的抵扣后合同额为：实际合同额-权益可抵扣金额
                BigDecimal restContract = contractAmount.subtract(response.getRightAmount());
                response.setContractAmount(restContract);

                //用户剩余应付，确认方案前为：合同额-已付金额-权益抵扣，若<=0 则为0
                restPay = contractAmount.subtract(response.getPaidAmount()).subtract(response.getRightAmount());
            } else if (orderDetailDto.getPreConfirmed().equals(1)) {
                //app订单总价  = beta订单总价
                response.setFinalOrderPrice(CopyWriterAndValue.build(CopyWriterConstant.Order.ORDER_TOTAL_PRICE, orderDetailDto.getOrderTotalAmount() == null || orderDetailDto.getOrderTotalAmount().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : orderDetailDto.getOrderTotalAmount()));
                //由于老订单一些返现活动导致订单总价和合同价不一致，取合同价计算会出现剩余应付错误，故此处修改为使用订单总价来计算，废除合同额
                contractAmount = response.getFinalOrderPrice().getValue();
                //方案已确认
                if (orderDetailDto.getOriginalOrderAmount() == null) {
                    //订单未记录原始方案价格，则原始方案价格视为实际合同额+权益已抵扣金额
                    solutionPrice = contractAmount.add(response.getRightAmount());

                } else {
                    solutionPrice = orderDetailDto.getOriginalOrderAmount();
                }

                //则前端展示的抵扣后合同额为：实际合同额
                response.setContractAmount(orderDetailDto.getContractAmount() == null ? BigDecimal.ZERO : orderDetailDto.getContractAmount());

                //用户剩余应付，确认方案后为：合同额-已支付金额
                restPay = contractAmount.subtract(response.getPaidAmount());

            }
            //3月款项优化后的剩余应付
            response.setNewRestAmount(response.getFinalOrderPrice().getValue().subtract(response.getPaidAmount()));
            if (response.getNewRestAmount().compareTo(BigDecimal.ZERO) < 0) {
                response.setNewRestAmount(BigDecimal.ZERO);
            }
            response.setContractAmount(response.getFinalOrderPrice().getValue());
            //原始方案价，若为空，则取合同金额
            response.setSolutionTotalPrice(solutionPrice);
            //方案总价
            response.setSolutionAmount(orderDetailDto.getNewSolutionAmount() == null ? BigDecimal.ZERO : orderDetailDto.getNewSolutionAmount());
            response.setRestAmount(restPay.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : restPay);

            response.setCanLoanAmount(response.getNewRestAmount() != null && response.getNewRestAmount().compareTo(BigDecimal.ZERO) > 0 ? response.getNewRestAmount() : UserConstants.CAN_LOAN_AMOUNT.subtract(response.getPaidAmount() == null ? BigDecimal.ZERO : response.getPaidAmount()));
            response.setCanLoanAmount(response.getCanLoanAmount().compareTo(BigDecimal.ZERO) >= 0 ? response.getCanLoanAmount() : BigDecimal.ZERO);
            return response;
        }
        return null;
    }


    /**
     * 查询设计需求，和收银台接口
     *
     * @param params
     * @return
     */
    private Map<String, Object> concurrentStyleRecordAndOrderInfo(Map<String, Object> params) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.queryOrderSummaryInfo((Integer) params.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return personalNeedProxy.queryDesignDemondHistory(params);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DESIGN_DEMOND_HISTORY_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return productProgramOrderProxy.querySolutionInfo((Integer) params.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }


    private void setStyleCommitRecordAndRemark(PersonalDesignResponse designResponse,
                                               StyleCommitRecordResponse styleCommitRecord) {
        if (null != styleCommitRecord && CollectionUtils.isNotEmpty(styleCommitRecord.getSelectedQuestionList())) {
            designResponse.setStyleCommitRecordId(styleCommitRecord.getTaskId());
        }
    }

    private void checkAndSetResponseRemark(PersonalDesignResponse designResponse) {
        if (null != designResponse.getRemark() && designResponse.getRemark().startsWith("[{") && designResponse.getRemark().endsWith("}]")) {
            try {
                List<StyleRemarkResultDto> remarks = JsonUtils.json2list(designResponse.getRemark(), StyleRemarkResultDto.class);
                if (CollectionUtils.isNotEmpty(remarks)) {
                    for (int j = remarks.size() - 1; j >= 0; j--) {
                        StyleRemarkResultDto rm = remarks.get(j);
                        if ("其他需求".equals(rm.getQuestion()) && CollectionUtils.isNotEmpty(rm.getAnswers())) {
                            designResponse.setRemark(rm.getAnswers().get(0));
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error("queryStyleRecord analysis o2o-exception , more info :{}", designResponse.getRemark(), e);
                designResponse.setRemark("");
            }
            if (designResponse.getRemark().startsWith("[{")) {
                designResponse.setRemark("");
            }
        }
    }

    /**
     * 将后台的vo转换为前台的response
     *
     * @param designResponse
     * @param designResponseVo
     * @param orderId
     */
    private void transformRecordVo(PersonalDesignResponse designResponse, AppSolutionDesignResponseVo designResponseVo, Integer orderId, Integer width, Integer osType) {

        if (designResponseVo != null) {
            designResponse.setOrderId(orderId);
            if (StringUtils.isNotBlank(designResponseVo.getBudget())) {
                designResponse.setBudget(designResponseVo.getBudget());
            }
            if (StringUtils.isNotBlank(designResponseVo.getRemark())) {
                designResponse.setRemark(designResponseVo.getRemark());
            }
            designResponse.setStyleCommitRecordId(designResponseVo.getTaskId());
            designResponse.setSolutionId(designResponseVo.getSolutionId());
            if (IntegerUtil.isNotEmpty(designResponseVo.getDnaId())) {
                designResponse.setDnaId(designResponseVo.getDnaId());
                designResponse.setDnaName(designResponseVo.getDnaName());
                if (StringUtils.isNotBlank(designResponseVo.getDnaHeadImg())) {
                    designResponse.setDnaHeadImage(AliImageUtil.imageCompress(designResponseVo.getDnaHeadImg(), osType, width, osType));
                }
                designResponse.setDnaStyle(designResponseVo.getDnaStyle());
                if (designResponseVo.getCreateTime() != null) {
                    designResponse.setCreateTime(designResponseVo.getCreateTime().split(" ")[0]);
                }
            }

            if (designResponseVo.getTaskStatus() != null) {

                designResponse.setTaskStatus(transferDesignStatus(designResponseVo.getTaskStatus()));
                designResponse.setTaskStatusStr(DesignDemondEnum.getEnumByCode(designResponseVo.getTaskStatus()).getStatusStr());
            }
        }
    }

    @Override
    public Integer transferDesignStatus(Integer status) {
        if (status != null) {
            // "无需分配",-1;"待分配", 0    =    "已提交",1
            if (status.equals(DesignTaskSystemEnum.UNNEED_DESIGN.getTaskStatus()) || status.equals(DesignTaskSystemEnum.WAIT_DESIGN.getTaskStatus())) {
                return DesignTaskAppEnum.COMMITTED.getTaskStatus();
                //"待确认",3;"设计中", 1;"审核中", 5;"审核不通过", 6    =    "设计中", 2
            } else if (status.equals(DesignTaskSystemEnum.WAIT_ACCEPT.getTaskStatus()) || status.equals(DesignTaskSystemEnum.DESIGNING.getTaskStatus()) ||
                    status.equals(DesignTaskSystemEnum.AUDITING.getTaskStatus()) || status.equals(DesignTaskSystemEnum.AUDIT_NOTPASS.getTaskStatus())) {
                return DesignTaskAppEnum.IN_DESIGN.getTaskStatus();
                //"设计完成", 2    =    "设计完成", 3
            } else if (status.equals(DesignTaskSystemEnum.DESIGNED.getTaskStatus())) {
                return DesignTaskAppEnum.DESIGN_FINISH.getTaskStatus();
                //"已失效",4||-3;"已作废",7    =    "已取消", -1
            } else if (status.equals(DesignTaskSystemEnum.INVALID2.getTaskStatus()) || status.equals(DesignTaskSystemEnum.INVALID.getTaskStatus()) || status.equals(DesignTaskSystemEnum.CANCELLATION.getTaskStatus())) {
                return DesignTaskAppEnum.INVALID.getTaskStatus();
                //(小艾提交设计需求状态)"待提交", 111；"待确认", 112  直接返回
            } else if (status.equals(DesignTaskSystemEnum.WAIT_SUBMIT.getTaskStatus()) || status.equals(DesignTaskSystemEnum.WAIT_AFFIRM.getTaskStatus())) {
                return status;
            } else if (status.equals(DesignTaskSystemEnum.WAIT_SUBMIT_BETA.getTaskStatus()) ){
                return DesignTaskSystemEnum.WAIT_AFFIRM.getTaskStatus();
            }
        }
        return 0;
    }

    @Override
    public QueryDesignStatusResponse queryDesignStatus(QueryDesignStatusRequest request) {
        List<AppSolutionDesignResponseVo> designResponseVoList =  personalNeedProxy.queryDesignDemondForOrderList(Arrays.asList(request.getOrderId()));

        if (CollectionUtils.isNotEmpty(designResponseVoList)) {
            AppSolutionDesignResponseVo designResponseVo = designResponseVoList.get(0);
            QueryDesignStatusResponse response = new QueryDesignStatusResponse();
            response.setOrderId(request.getOrderId())
                    .setTaskStatus(transferDesignStatus(designResponseVo.getTaskStatus()));
            return response;
        }
        return null;
    }

    @Override
    public List<FilterQueryDnaRoomResponse> filterDnaRoomByPurpose(FilterDnaRoomReq request) {
        if (CollectionUtils.isEmpty(request.getMarkAndPurposeList())) {
            LOG.error("filterDnaRoomByPurpose validate error params:{}", request);
            return Collections.emptyList();
        }
        List<FilterQueryDnaRoomResponse> result = new ArrayList<>();
        Map<Integer, Integer> dnaRoomPurposes = new HashMap<>();
        //根据DNA  ID查询DNA详情信息
        DNAInfoResponseVo dnaInfoResponse = homeCardBossProxy.getDnaDetailById(request.getDnaId());
        if (null != dnaInfoResponse && CollectionUtils.isNotEmpty(dnaInfoResponse.getDnaRoomList())) {
            //空间列表
            for (DNARoomVo roomVo : dnaInfoResponse.getDnaRoomList()) {
                dnaRoomPurposes.put(roomVo.getRoomUseId(), roomVo.getRoomId());
            }
        }

        Map<String, List<DnaRoomMarkAndPurposeVo>> roomMarkListMap = new HashMap<>();
        roomMarkListMap.put(RoomMarkConstants.ROOM.name(), ModelMapperUtil.strictMapList(request.getMarkAndPurposeList(), DnaRoomMarkAndPurposeVo.class));
        Integer houseId = request.getHouseId();
        if (null != houseId && houseId > 0) {// 用户有房产信息
            Map<String, Object> map = new HashMap<>();
            map.put("houseId", houseId);
            ApartmentInfoVo houseInfoByApartmentIdNew = homeCardBossProxy.getHouseInfoByApartmentIdNew(map);
            if (houseInfoByApartmentIdNew != null) {
                List<HouseRoomVo> apartmentRoomVoList = houseInfoByApartmentIdNew.getHouseRoomVos();
                if (CollectionUtils.isNotEmpty(apartmentRoomVoList)) {
                    assembleRoomMarkListMapWithHouse(apartmentRoomVoList, roomMarkListMap);
                }
            }
        } else {// 无房产
            assembleRoomMarkListMapWithNoHouse(roomMarkListMap);
        }

        List<FilterDnaRoomResponse> selfDataList = new ArrayList<>();
        List<FilterDnaRoomResponse> defaultDataList = new ArrayList<>();
        processHouseCompare(roomMarkListMap, dnaRoomPurposes, selfDataList, defaultDataList);

        // 根据用途id查询dna空间信息
        queryDnaRoomInfoByUse(selfDataList, request.getPageNo(), request.getPageSize());

        // 用户自选，非默认
        FilterQueryDnaRoomResponse selfData = new FilterQueryDnaRoomResponse();
        selfData.setIsDefault(0);
        List<DNARoomAndItemVo> dNARoomAndItemResponse = homeCardBossProxy.getSoftListByCondition(request.getDnaId());
        if (CollectionUtils.isNotEmpty(selfDataList)) {
            List<String> imageList = new ArrayList<>();
            Map<String, Picture> pictureMap = new HashMap<>();
            selfDataList.forEach(defaultData -> {
                if (CollectionUtils.isNotEmpty(defaultData.getRoomInfoList())) {
                    defaultData.getRoomInfoList().forEach(roomMarkListBean ->
                    {
                        if (CollectionUtils.isNotEmpty(roomMarkListBean.getDnaRoomPicUrlList())) {
                            imageList.addAll(roomMarkListBean.getDnaRoomPicUrlList());
                        }
                    });
                }
            });
            if (CollectionUtils.isNotEmpty(imageList)) {
                pictureMap = AppRedisUtil.getRedisImageSizeMap(imageList.stream().distinct().collect(Collectors.toList()), serviceCaller);
            }

            Map<String, Picture> finalPictureMap = pictureMap;
            selfDataList.forEach(defaultData -> {
                if (CollectionUtils.isNotEmpty(defaultData.getRoomInfoList())) {
                    defaultData.getRoomInfoList().forEach(roomMarkListBean ->
                    {
                        List<DnaRoomPicDto> dnaRoomPicUrlDtoList = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(roomMarkListBean.getDnaRoomPicUrlList())) {
                            for (String dnaRoomPicUrl : roomMarkListBean.getDnaRoomPicUrlList()) {
                                Picture picture = finalPictureMap.get(dnaRoomPicUrl);
                                dnaRoomPicUrlDtoList.add(new DnaRoomPicDto(picture.getWidth(), picture.getHeight(), AliImageUtil.imageCompress(roomMarkListBean.getDnaRoomPicUrl(), 1, 750, ImageConstant.SIZE_MIDDLE)));

                            }
                        }
                        boolean hasSoft = false;
                        if (CollectionUtils.isNotEmpty(dNARoomAndItemResponse)) {
                            for (DNARoomAndItemVo dnaRoomAndItemVo : dNARoomAndItemResponse) {
                                if (RoomUseEnum.getDescription(roomMarkListBean.getDnaRoomUsageId()).equals(dnaRoomAndItemVo.getRoomUseName())) {
                                    hasSoft = true;
                                }
                            }
                        }

                        roomMarkListBean.setDnaRoomPicUrlDtoList(dnaRoomPicUrlDtoList);
                        if (hasSoft) {
                            roomMarkListBean.setSkipUrl(apiConfig.getSoftSkipUrl() + request.getDnaId() + "&dnaRoomUsageId=" + roomMarkListBean.getDnaRoomUsageId());//软装清单跳转路径
                        }
                        roomMarkListBean.setDnaRoomPicUrl(AliImageUtil.imageCompress(roomMarkListBean.getDnaRoomPicUrl(), 1, 750, ImageConstant.SIZE_MIDDLE));
                    });
                }
            });
        }
        if (CollectionUtils.isNotEmpty(selfDataList)) {
            //目前前端识别同用途空间所选dna依赖与list中的顺序
            Collections.sort(selfDataList, Comparator.comparing(FilterDnaRoomResponse::getDnaPurposeId));
//            if(request.getDnaStyle()!=null){
//                selfDataList.forEach(filterDnaRoomResponse -> {
//                    if(CollectionUtils.isNotEmpty(filterDnaRoomResponse.getRoomInfoList())){
//                        filterDnaRoomResponse.getRoomInfoList().removeIf(dnaRoomInfoResponse -> dnaRoomInfoResponse.getDnaStyle()!=null && dnaRoomInfoResponse.getDnaStyle()!=request.getDnaStyle());
//                    }
//                });
//
//            }
        }
        selfData.setList(selfDataList);
        result.add(selfData);
        if (CollectionUtils.isNotEmpty(defaultDataList)) {
            defaultDataList.forEach(defaultData -> {
                if (CollectionUtils.isNotEmpty(defaultData.getRoomInfoList())) {
                    defaultData.getRoomInfoList().forEach(roomMarkListBean ->
                            roomMarkListBean.setDnaRoomPicUrl(AliImageUtil.imageCompress(roomMarkListBean.getDnaRoomPicUrl(), 1, 750, ImageConstant.SIZE_MIDDLE)));
                }
            });
        }
        // 默认
        FilterQueryDnaRoomResponse defaultData = new FilterQueryDnaRoomResponse();
        defaultData.setIsDefault(1);
        if (CollectionUtils.isNotEmpty(defaultDataList)) {
            //目前前端识别同用途空间所选dna依赖与list中的顺序
            Collections.sort(defaultDataList, Comparator.comparing(FilterDnaRoomResponse::getDnaPurposeId));
        }
        defaultData.setList(defaultDataList);
        result.add(defaultData);
        return result;
    }


    private void processHouseCompare(Map<String, List<DnaRoomMarkAndPurposeVo>> roomMarkListMap,
                                     Map<Integer, Integer> dnaRoomPurposes, List<FilterDnaRoomResponse> selfDataList,
                                     List<FilterDnaRoomResponse> defaultDataList) {
        // 比较室
        processCompareForRoom(roomMarkListMap.get(RoomMarkConstants.ROOM.name()), dnaRoomPurposes, selfDataList, defaultDataList);
        // 比较厅
        processCompareForLiving(roomMarkListMap.get(RoomMarkConstants.LIVING.name()), dnaRoomPurposes, selfDataList, defaultDataList);
        // 比较厨房
        processCompareForKitchen(roomMarkListMap.get(RoomMarkConstants.KITCHEN.name()), dnaRoomPurposes, selfDataList, defaultDataList);
        // 比较卫
        processCompareForBathroom(roomMarkListMap.get(RoomMarkConstants.BATHROOM.name()), dnaRoomPurposes, selfDataList, defaultDataList);
        // 比较阳台
        processCompareForBalcony(roomMarkListMap.get(RoomMarkConstants.BALCONY.name()), dnaRoomPurposes, selfDataList, defaultDataList);
        // 比较储藏室
        processCompareForStorage(roomMarkListMap.get(RoomMarkConstants.STORAGE.name()), defaultDataList);
        // 比较衣帽间
        processCompareForCloak(roomMarkListMap.get(RoomMarkConstants.CLOAK.name()), defaultDataList);
    }

    private void assembleRoomMarkListMapWithNoHouse(Map<String, List<DnaRoomMarkAndPurposeVo>> roomMarkListMap) {
        List<DnaRoomMarkAndPurposeVo> livingList = new ArrayList<>(2);
        DnaRoomMarkAndPurposeVo livingRoomMark1 = new DnaRoomMarkAndPurposeVo();
        livingRoomMark1.setDnaMarkId(CommonRoomMarkEnum.LIVING.getCode());
        livingList.add(livingRoomMark1);
        DnaRoomMarkAndPurposeVo livingRoomMark2 = new DnaRoomMarkAndPurposeVo();
        livingRoomMark2.setDnaMarkId(CommonRoomMarkEnum.RESTAURANT.getCode());
        livingList.add(livingRoomMark2);
        roomMarkListMap.put(RoomMarkConstants.LIVING.name(), livingList);

        List<DnaRoomMarkAndPurposeVo> kitchenList = new ArrayList<>(1);
        DnaRoomMarkAndPurposeVo kitchenRoomMark1 = new DnaRoomMarkAndPurposeVo();
        kitchenRoomMark1.setDnaMarkId(CommonRoomMarkEnum.KITCHEN.getCode());
        kitchenList.add(kitchenRoomMark1);
        roomMarkListMap.put(RoomMarkConstants.KITCHEN.name(), kitchenList);

        List<DnaRoomMarkAndPurposeVo> bathroomList = new ArrayList<>(2);
        DnaRoomMarkAndPurposeVo bathroomRoomMark1 = new DnaRoomMarkAndPurposeVo();
        bathroomRoomMark1.setDnaMarkId(CommonRoomMarkEnum.BATHROOM.getCode());
        bathroomList.add(bathroomRoomMark1);
        DnaRoomMarkAndPurposeVo bathroomRoomMark2 = new DnaRoomMarkAndPurposeVo();
        bathroomRoomMark2.setDnaMarkId(CommonRoomMarkEnum.SECOND_BATHROOM.getCode());
        bathroomList.add(bathroomRoomMark2);
        roomMarkListMap.put(RoomMarkConstants.BATHROOM.name(), bathroomList);

        List<DnaRoomMarkAndPurposeVo> balconyList = new ArrayList<>(2);
        DnaRoomMarkAndPurposeVo balconyRoomMark1 = new DnaRoomMarkAndPurposeVo();
        balconyRoomMark1.setDnaMarkId(CommonRoomMarkEnum.BALCONY.getCode());
        balconyList.add(balconyRoomMark1);
        DnaRoomMarkAndPurposeVo balconyRoomMark2 = new DnaRoomMarkAndPurposeVo();
        balconyRoomMark2.setDnaMarkId(CommonRoomMarkEnum.SECOND_BALCONY.getCode());
        balconyList.add(balconyRoomMark2);
        roomMarkListMap.put(RoomMarkConstants.BALCONY.name(), balconyList);
    }

    private void assembleRoomMarkListMapWithHouse(List<HouseRoomVo> apartmentRoomVoList,
                                                  Map<String, List<DnaRoomMarkAndPurposeVo>> roomMarkListMap) {
        List<DnaRoomMarkAndPurposeVo> livingList = new ArrayList<>();
        List<DnaRoomMarkAndPurposeVo> kitchenList = new ArrayList<>();
        List<DnaRoomMarkAndPurposeVo> bathroomList = new ArrayList<>();
        List<DnaRoomMarkAndPurposeVo> balconyList = new ArrayList<>();
        List<DnaRoomMarkAndPurposeVo> storageList = new ArrayList<>();
        List<DnaRoomMarkAndPurposeVo> cloakList = new ArrayList<>();

        for (HouseRoomVo vo : apartmentRoomVoList) {
            DnaRoomMarkAndPurposeVo roomMark = new DnaRoomMarkAndPurposeVo();
            roomMark.setDnaPurposeId(vo.getUsageId());
            String roomMarkType = CommonRoomUsageEnum.getRoomMarkType(vo.getUsageId());
            if (RoomMarkConstants.LIVING.name().equals(roomMarkType)) {
                livingList.add(roomMark);
            } else if (RoomMarkConstants.KITCHEN.name().equals(roomMarkType)) {
                kitchenList.add(roomMark);
            } else if (RoomMarkConstants.BATHROOM.name().equals(roomMarkType)) {
                bathroomList.add(roomMark);
            } else if (RoomMarkConstants.BALCONY.name().equals(roomMarkType)) {
                balconyList.add(roomMark);
            } else if (RoomMarkConstants.STORAGE.name().equals(roomMarkType)) {
                storageList.add(roomMark);
            } else if (RoomMarkConstants.CLOAK.name().equals(roomMarkType)) {
                cloakList.add(roomMark);
            }
        }

        if (CollectionUtils.isNotEmpty(livingList)) {
            roomMarkListMap.put(RoomMarkConstants.LIVING.name(), livingList);
        }
        if (CollectionUtils.isNotEmpty(kitchenList)) {
            roomMarkListMap.put(RoomMarkConstants.KITCHEN.name(), kitchenList);
        }
        if (CollectionUtils.isNotEmpty(bathroomList)) {
            roomMarkListMap.put(RoomMarkConstants.BATHROOM.name(), bathroomList);
        }
        if (CollectionUtils.isNotEmpty(balconyList)) {
            roomMarkListMap.put(RoomMarkConstants.BALCONY.name(), balconyList);
        }
        if (CollectionUtils.isNotEmpty(storageList)) {
            roomMarkListMap.put(RoomMarkConstants.STORAGE.name(), storageList);
        }
        if (CollectionUtils.isNotEmpty(cloakList)) {
            roomMarkListMap.put(RoomMarkConstants.CLOAK.name(), cloakList);
        }
    }

    //衣帽间
    private void processCompareForCloak(List<DnaRoomMarkAndPurposeVo> markAndPurposeVoList,
                                        List<FilterDnaRoomResponse> defaultDataList) {
        if (CollectionUtils.isEmpty(markAndPurposeVoList)) {
            return;
        }
        Integer firstDnaRoomId = null;
        for (int i = 1; i <= markAndPurposeVoList.size(); i++) {
            FilterDnaRoomResponse response = new FilterDnaRoomResponse();
            response.setSortNum(i);
            response.setRoomClassifyType(DnaRoomClassifyTypeEnum.CLOAK.getCode());
            response.setDnaPurposeId(RoomUseEnum.ROOM_MULTIPLE_FUNC.getCode());
            response.setDnaMarkId(markAndPurposeVoList.get(i - 1).getDnaMarkId());

            // TODO 临时修改 20190109
            response.setDnaRoomId(7397);
            defaultDataList.add(response);
        }
    }

    // 储藏间
    private void processCompareForStorage(List<DnaRoomMarkAndPurposeVo> markAndPurposeVoList, List<FilterDnaRoomResponse> defaultDataList) {
        if (CollectionUtils.isEmpty(markAndPurposeVoList)) {
            return;
        }
        Integer firstDnaRoomId = null;
        for (int i = 1; i <= markAndPurposeVoList.size(); i++) {
            FilterDnaRoomResponse response = new FilterDnaRoomResponse();
            response.setSortNum(i);
            response.setRoomClassifyType(DnaRoomClassifyTypeEnum.STORAGE.getCode());
            response.setDnaPurposeId(RoomUseEnum.ROOM_MULTIPLE_FUNC.getCode());
            response.setDnaMarkId(markAndPurposeVoList.get(i - 1).getDnaMarkId());
            // TODO 临时修改 20190109
            response.setDnaRoomId(7397);
            defaultDataList.add(response);
        }
    }

    // 阳台
    private void processCompareForBalcony(List<DnaRoomMarkAndPurposeVo> markAndPurposeVoList, Map<Integer, Integer> dnaRoomPurposes,
                                          List<FilterDnaRoomResponse> selfDataList, List<FilterDnaRoomResponse> defaultDataList) {
        if (CollectionUtils.isEmpty(markAndPurposeVoList)) {
            return;
        }
        Integer firstDnaRoomId = null;
        for (int i = 1; i <= markAndPurposeVoList.size(); i++) {
            FilterDnaRoomResponse response = new FilterDnaRoomResponse();
            response.setSortNum(i);
            response.setRoomClassifyType(DnaRoomClassifyTypeEnum.BALCONY.getCode());
            response.setDnaMarkId(markAndPurposeVoList.get(i - 1).getDnaMarkId());
            Integer dnaPurposeId = 20;
            if (i == 1) {
                dnaPurposeId = 10;
                if (!dnaRoomPurposes.containsKey(dnaPurposeId)) {
                    // 需要用户选择的
                    selfDataList.add(response);
                } else {
                    firstDnaRoomId = dnaRoomPurposes.get(dnaPurposeId);
                    response.setDnaRoomId(firstDnaRoomId);
                    defaultDataList.add(response);
                }
            } else if (i == 2) {
                if (!dnaRoomPurposes.containsKey(dnaPurposeId)) {
                    // 需要用户选择的
                    selfDataList.add(response);
                } else {
                    firstDnaRoomId = dnaRoomPurposes.get(dnaPurposeId);
                    response.setDnaRoomId(firstDnaRoomId);
                    defaultDataList.add(response);
                }
            } else {
                if (null != firstDnaRoomId) {
                    response.setDnaRoomId(firstDnaRoomId);
                }
                defaultDataList.add(response);
            }
            response.setDnaPurposeId(dnaPurposeId);
        }
    }

    // 卫生间
    private void processCompareForBathroom(List<DnaRoomMarkAndPurposeVo> markAndPurposeVoList, Map<Integer, Integer> dnaRoomPurposes,
                                           List<FilterDnaRoomResponse> selfDataList, List<FilterDnaRoomResponse> defaultDataList) {
        if (CollectionUtils.isEmpty(markAndPurposeVoList)) {
            return;
        }
        Integer firstDnaRoomId = null;
        for (int i = 1; i <= markAndPurposeVoList.size(); i++) {
            FilterDnaRoomResponse response = new FilterDnaRoomResponse();
            response.setSortNum(i);
            response.setRoomClassifyType(DnaRoomClassifyTypeEnum.BATHROOM.getCode());
            response.setDnaMarkId(markAndPurposeVoList.get(i - 1).getDnaMarkId());
            Integer dnaPurposeId = 19;
            if (i == 1) {
                dnaPurposeId = 18;
                if (!dnaRoomPurposes.containsKey(dnaPurposeId)) {
                    // 需要用户选择的
                    selfDataList.add(response);
                } else {
                    firstDnaRoomId = dnaRoomPurposes.get(dnaPurposeId);
                    response.setDnaRoomId(firstDnaRoomId);
                    defaultDataList.add(response);
                }
            } else if (i == 2) {
                if (!dnaRoomPurposes.containsKey(dnaPurposeId)) {
                    // 需要用户选择的
                    selfDataList.add(response);
                } else {
                    firstDnaRoomId = dnaRoomPurposes.get(dnaPurposeId);
                    response.setDnaRoomId(firstDnaRoomId);
                    defaultDataList.add(response);
                }
            } else {
                if (null != firstDnaRoomId) {
                    response.setDnaRoomId(firstDnaRoomId);
                }
                defaultDataList.add(response);
            }
            response.setDnaPurposeId(dnaPurposeId);
        }
    }

    // 厨房
    private void processCompareForKitchen(List<DnaRoomMarkAndPurposeVo> markAndPurposeVoList, Map<Integer, Integer> dnaRoomPurposes,
                                          List<FilterDnaRoomResponse> selfDataList, List<FilterDnaRoomResponse> defaultDataList) {
        if (CollectionUtils.isEmpty(markAndPurposeVoList)) {
            return;
        }
        Integer firstDnaRoomId = null;
        for (int i = 1; i <= markAndPurposeVoList.size(); i++) {
            FilterDnaRoomResponse response = new FilterDnaRoomResponse();
            response.setSortNum(i);
            response.setRoomClassifyType(DnaRoomClassifyTypeEnum.KITCHEN.getCode());
            response.setDnaPurposeId(8);
            response.setDnaMarkId(markAndPurposeVoList.get(i - 1).getDnaMarkId());
            if (i == 1) {
                if (!dnaRoomPurposes.containsKey(response.getDnaPurposeId())) {
                    // 需要用户选择的
                    selfDataList.add(response);
                } else {
                    firstDnaRoomId = dnaRoomPurposes.get(response.getDnaPurposeId());
                    response.setDnaRoomId(firstDnaRoomId);
                    defaultDataList.add(response);
                }
            } else {
                if (null != firstDnaRoomId) {
                    response.setDnaRoomId(firstDnaRoomId);
                }
                defaultDataList.add(response);
            }
        }
    }

    //厅
    private void processCompareForLiving(List<DnaRoomMarkAndPurposeVo> markAndPurposeVoList, Map<Integer, Integer> dnaRoomPurposes,
                                         List<FilterDnaRoomResponse> selfDataList, List<FilterDnaRoomResponse> defaultDataList) {
        if (CollectionUtils.isEmpty(markAndPurposeVoList)) {
            return;
        }
        Integer firstDnaRoomId = null;
        for (int i = 1; i <= markAndPurposeVoList.size(); i++) {
            FilterDnaRoomResponse response = new FilterDnaRoomResponse();
            response.setSortNum(i);
            response.setRoomClassifyType(DnaRoomClassifyTypeEnum.LIVING.getCode());
            response.setDnaMarkId(markAndPurposeVoList.get(i - 1).getDnaMarkId());
            Integer dnaPurposeId = 1;
            if (i == 1) {// 客厅
                if (!dnaRoomPurposes.containsKey(dnaPurposeId)) {
                    // 需要用户选择的
                    selfDataList.add(response);
                } else {
                    firstDnaRoomId = dnaRoomPurposes.get(dnaPurposeId);
                    response.setDnaRoomId(firstDnaRoomId);
                    defaultDataList.add(response);
                }
            } else if (i == 2) {// 餐厅
                dnaPurposeId = 6;
                if (!dnaRoomPurposes.containsKey(dnaPurposeId)) {
                    // 需要用户选择的
                    selfDataList.add(response);
                } else {
                    response.setDnaRoomId(dnaRoomPurposes.get(dnaPurposeId));
                    defaultDataList.add(response);
                }
            } else {// 客厅
                if (null != firstDnaRoomId) {
                    response.setDnaRoomId(firstDnaRoomId);
                }
                defaultDataList.add(response);
            }
            response.setDnaPurposeId(dnaPurposeId);
        }
    }

    private void processCompareForRoom(List<DnaRoomMarkAndPurposeVo> markAndPurposeVoList,
                                       Map<Integer, Integer> dnaRoomPurposes, List<FilterDnaRoomResponse> selfDataList,
                                       List<FilterDnaRoomResponse> defaultDataList) {
        if (CollectionUtils.isEmpty(markAndPurposeVoList)) {
            return;
        }
        int i = 1;
        Map<Integer, FilterDnaRoomResponse> defaultDataMap = new HashMap<Integer, FilterDnaRoomResponse>();
        for (DnaRoomMarkAndPurposeVo pp : markAndPurposeVoList) {
            if (null != pp && null != pp.getDnaPurposeId()) {
                FilterDnaRoomResponse response = new FilterDnaRoomResponse();
                response.setSortNum(i);
                response.setRoomClassifyType(DnaRoomClassifyTypeEnum.ROOM.getCode());
                response.setDnaPurposeId(pp.getDnaPurposeId());
                response.setDnaMarkId(pp.getDnaMarkId());
                if (!dnaRoomPurposes.containsKey(pp.getDnaPurposeId())) {
                    // 需要用户选择的
                    selfDataList.add(response);
                } else {
                    // 针对多个同样的用途，而dna中只有一个，则排在后面的需要用户选择
                    if (defaultDataMap.containsKey(pp.getDnaPurposeId())) {
                        selfDataList.add(response);
                    } else {
                        response.setDnaRoomId(dnaRoomPurposes.get(pp.getDnaPurposeId()));
                        defaultDataMap.put(pp.getDnaPurposeId(), response);
                    }
                }
            }
            i++;
        }
        if (!org.springframework.util.CollectionUtils.isEmpty(defaultDataMap)) {
            defaultDataList.addAll(new ArrayList<>(defaultDataMap.values()));
        }
    }

    private void queryDnaRoomInfoByUse(List<FilterDnaRoomResponse> responseList, int pageNo, int pageSize) {
        if (CollectionUtils.isEmpty(responseList)) {
            return;
        }
        if (pageNo == 0) {
            pageNo = 1;
        }
        if (pageSize == 0) {
            pageSize = 4;
        }

        Map<String, List<DnaRoomInfoResponse>> queryDnaRoomMap = new HashMap<>();
        // 分片处理
        int totalSize = responseList.size();
        int fragmentSize = 3;
        int fragmentFirst = 0;
        int fragmentEnd = 0;
        int fragment = totalSize % fragmentSize == 0 ? totalSize / fragmentSize : (totalSize / fragmentSize) + 1;
        for (int i = 0; i < fragment; i++) {
            fragmentFirst = i * fragmentSize;
            if (i == fragment - 1) {
                fragmentEnd = totalSize;
            } else {
                fragmentEnd = (i + 1) * fragmentSize;
            }
            queryDnaRoomMap.putAll(queryDnaRoomFragment(responseList.subList(fragmentFirst, fragmentEnd), pageNo, pageSize));
        }

        if (!org.springframework.util.CollectionUtils.isEmpty(queryDnaRoomMap)) {
            for (FilterDnaRoomResponse room : responseList) {
                room.setRoomInfoList(queryDnaRoomMap.get(ConcurrentTaskEnum.QUERY_DNA_ROOM_BYUSAGEID.name() + room.getDnaPurposeId()));
            }
        }
    }

    private Map<String, List<DnaRoomInfoResponse>> queryDnaRoomFragment(
            List<FilterDnaRoomResponse> responseList, int pageNo, int pageSize) {
        List<IdentityTaskAction<List<DnaRoomInfoResponse>>> queryTasks = new ArrayList<>(responseList.size());
        for (FilterDnaRoomResponse room : responseList) {
            if (null == room.getDnaPurposeId() || room.getDnaPurposeId() <= 0) {
                continue;
            }

            Map<String, Object> params = new HashMap<>();
            params.put("pageNo", pageNo);
            params.put("pageSize", pageSize);
            params.put("roomUsageId", room.getDnaPurposeId());
            queryTasks.add(new IdentityTaskAction<List<DnaRoomInfoResponse>>() {
                @Override
                public List<DnaRoomInfoResponse> doInAction() throws Exception {
                    PageModel pageModel = personalNeedProxy.queryDnaRoomByUsageId(params);
                    if (pageModel != null && null != pageModel.getList()) {
                        List<DnaRoomInfoResponse> list = pageModel.getList();
                        if (CollectionUtils.isNotEmpty(list)) {
                            // 切图
                            for (DnaRoomInfoResponse vo : list) {
                                if (!StringUtil.isNullOrEmpty(vo.getDnaRoomPicUrl())) {
                                    String dnaRoomPicUrl = AliImageUtil.imageCompress(vo.getDnaRoomPicUrl(), 1, 750, ImageConstant.SIZE_MIDDLE);
                                    vo.setDnaRoomPicUrl(dnaRoomPicUrl);
                                }
                                if (CollectionUtils.isNotEmpty(vo.getRoomItemBrandList())) {
                                    StringBuilder sb = new StringBuilder();
                                    vo.getRoomItemBrandList().forEach(x -> sb.append(x).append(" "));
                                    vo.setDnaRoomBrandPraise(sb.toString());
                                }
                            }
                        }
                        return list;
                    }
                    return Collections.emptyList();
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_DNA_ROOM_BYUSAGEID.name() + room.getDnaPurposeId();
                }
            });
        }

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DnaRoomListResponse queryDnaRoomByPurposeId(QueryDnaRoomRequest request) {
        int pageNo = request.getPageNo();
        int pageSize = request.getPageSize();
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        params.put("roomUsageId", request.getDnaPurposeId());

        PageModel<DnaRoomInfoResponse> pageModel = personalNeedProxy.queryDnaRoomByUsageId(params);
        DnaRoomListResponse response = new DnaRoomListResponse();
        if (pageModel != null && null != pageModel.getList()) {
            List<DnaRoomInfoResponse> list = pageModel.getList();
            response.setDnaRoomList(list);
            response.setTotalPage(pageModel.getTotalPages());
            return response;
        }

        return null;
    }

}
