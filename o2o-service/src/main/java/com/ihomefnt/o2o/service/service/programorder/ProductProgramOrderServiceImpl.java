/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年3月15日
 * Description:ProductProgramOrderServiceImpl.java
 */
package com.ihomefnt.o2o.service.service.programorder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Lists;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.common.concurrent.TaskProcessManager;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.message.RocketMQTemplate;
import com.ihomefnt.o2o.intf.domain.address.dto.AreaInfoDto;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.art.dto.KeywordVo;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.delivery.dto.ProductStatusListVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.HardDetailVo;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetBeginTimeByOrderIdResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.ProjectDetailProgressInfoVo;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.HouseLayoutVo;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.ProjectResponse;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.UserHousePropertiesResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.HouseInfo;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.QueryDraftRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.SolutionDraftResponse;
import com.ihomefnt.o2o.intf.domain.loan.vo.response.LoanListResponseVo;
import com.ihomefnt.o2o.intf.domain.main.dto.DeliverySimpleInfoDto;
import com.ihomefnt.o2o.intf.domain.main.vo.SolutionInfo;
import com.ihomefnt.o2o.intf.domain.order.dto.CashierRecordResultVo;
import com.ihomefnt.o2o.intf.domain.order.vo.CheckIfCanDeliveryConfirmVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.OrderSimpleInfoRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderSimpleInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.DraftSimpleResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.ProgramOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.product.dto.ProductCategoryVo;
import com.ihomefnt.o2o.intf.domain.program.dto.*;
import com.ihomefnt.o2o.intf.domain.program.enums.SolutionStatusEnum;
import com.ihomefnt.o2o.intf.domain.program.vo.request.AddBagCreateRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.request.AladdinUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.request.HardStandardRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SoftRoomSkuInfo.SimpleRoomSkuDto;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.*;
import com.ihomefnt.o2o.intf.domain.promotion.dto.JoinPromotionVo;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.PromotionPageResponse;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.PromotionResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.*;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaInfoDto;
import com.ihomefnt.o2o.intf.domain.vote.dto.SignedDnaDto;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import com.ihomefnt.o2o.intf.manager.constant.dms.DmsBomStatusEnum;
import com.ihomefnt.o2o.intf.manager.constant.dms.DmsProductStatusConstants;
import com.ihomefnt.o2o.intf.manager.constant.home.ElectronicContractTypeEnum;
import com.ihomefnt.o2o.intf.manager.constant.home.MasterOrderStatusEnum;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderErrorCodeEnum;
import com.ihomefnt.o2o.intf.manager.constant.program.*;
import com.ihomefnt.o2o.intf.manager.constant.programorder.*;
import com.ihomefnt.o2o.intf.manager.constant.user.UserConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.IntegerUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringTemplateUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.date.TimeUtils;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.manager.util.order.FamilyOrderStatus;
import com.ihomefnt.o2o.intf.manager.util.programorder.PromotionComparator;
import com.ihomefnt.o2o.intf.proxy.address.AddressProxy;
import com.ihomefnt.o2o.intf.proxy.delivery.DeliveryProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardBossProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.lechange.HbmsProxy;
import com.ihomefnt.o2o.intf.proxy.loan.LoanInfoProxy;
import com.ihomefnt.o2o.intf.proxy.main.DmsProxy;
import com.ihomefnt.o2o.intf.proxy.order.CashProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.product.ProductProxy;
import com.ihomefnt.o2o.intf.proxy.program.KeywordWcmProxy;
import com.ihomefnt.o2o.intf.proxy.promotion.PromotionProxy;
import com.ihomefnt.o2o.intf.proxy.user.PersonalCenterProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.proxy.vote.CharacterColorProxy;
import com.ihomefnt.o2o.intf.service.address.AreaService;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.service.common.AiDingTalk;
import com.ihomefnt.o2o.intf.service.home.HomeBuildingService;
import com.ihomefnt.o2o.intf.service.home.HomeV5PageService;
import com.ihomefnt.o2o.intf.service.loan.LoanService;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.o2o.intf.service.toc.TocService;
import com.ihomefnt.o2o.service.proxy.program.ProductProgramProxyImpl;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import com.ihomefnt.oms.trade.order.dto.solution.SolutionOrderDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise.ALADDIN_ORDER_STATUS_TOUCH;

/**
 * @author zhang
 */
@Service
@SuppressWarnings("all")
public class ProductProgramOrderServiceImpl implements ProductProgramOrderService {

    @Autowired
    private LoanService loanService;

    @Autowired
    private ProductProgramOrderProxyImpl proxy;

    @Autowired
    private ProductProgramProxyImpl programProxy;

    @Autowired
    PromotionProxy promotionProxy;

    @Autowired
    private AiDingTalk aiDingTalk;

    @Autowired
    HomeV5PageService homeV5PageService;

    @Autowired
    AddressProxy addressProxy;

    @Autowired
    UserProxy userProxy;

    @Autowired
    private TocService tocService;

    @Autowired
    private HomeCardWcmProxy homeCardWcmProxy;

    private static final Logger LOG = LoggerFactory.getLogger(ProductProgramOrderServiceImpl.class);

    @Autowired
    private ProductProgramService productProgramService;

    @Autowired
    private HomeBuildingService homeBuildingService;

    private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    private final SimpleDateFormat dayFormat_DAY = new SimpleDateFormat("yyyy年MM月dd日");

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @NacosValue(value = "${app.signed.dna.task.topic}", autoRefreshed = true)
    private String APP_SIGNED_DNA_TASK_TOPIC;

    @Autowired
    CashProxy cashProxy;

    @Autowired
    OrderProxy orderProxy;

    @Autowired
    private KeywordWcmProxy keywordWcmProxy;

    @Autowired
    LoanInfoProxy loanInfoProxy;

    @Autowired
    DicProxy dicProxy;

    @Autowired
    ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Autowired
    ProductProxy productProxy;
    @Autowired
    private HomeCardBossProxy homeCardBossProxy;
    @Autowired
    private PersonalCenterProxy personalCenterProxy;
    @Autowired
    private DeliveryProxy deliveryProxy;
    @Autowired
    private HbmsProxy hbmsProxy;
    @Autowired
    private AreaService areaService;
    @Autowired
    private DmsProxy dmsProxy;

    // 邀请用户定金 1000
    @NacosValue(value = "${invitation.deposit.money}", autoRefreshed = true)
    private BigDecimal invitationDepositMoney;
    // toC用户定金 99
    @NacosValue(value = "${to.c.deposit.money}", autoRefreshed = true)
    private BigDecimal toCDepositMoney;
    // 默认用户定金 10000
    @NacosValue(value = "${global.deposit.money.default}", autoRefreshed = true)
    private BigDecimal globalDepositMoney;


    @Override
    public AladdinOrderResponseVo createOrder(ProgramOrderCreateRequest request, Integer opType) {
        Integer orderType = request.getOrderType();
        if (orderType.intValue() == OrderConstant.ORDER_TYPE_SUIT) {
            Integer suitId = request.getSuitId();
            if (suitId == null) {
                throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
            }
            List<Integer> roomIdList = this.getRoomIdListBySuitId(suitId);
            if (CollectionUtils.isEmpty(roomIdList)) {
                throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
            }
            request.setRoomIdList(roomIdList);
        } else if (orderType.intValue() == OrderConstant.ORDER_TYPE_ROOM) {
            if (CollectionUtils.isEmpty(request.getRoomIdList())) {
                throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
            }
        } else {
            throw new BusinessException("订单类型错误");
        }
        // 判断是否登陆
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        // 判断是否特定用户
        Integer userId = userDto.getId();
        UserHousePropertiesResponseVo userInfo = productProgramService.getUserSpecificProgram(userId, request.getHouseId());
        if (userInfo == null || userInfo.getSpecific() == null
                || ProductProgramPraise.PROGRAM_LIMIT_SELECT.equals(userInfo.getSpecific())) {
            String msgResult = MessageConstant.USER_NOT_SPECIFIC;
            Long code = HttpResponseCode.USER_NOT_SPECIFIC;
            // 非特定用户、只能看方案、有退款申请
            if (userInfo != null && userInfo.getSpecific() != null
                    && ProductProgramPraise.PROGRAM_LIMIT_LOOK.equals(userInfo.getSpecific())) {
                // 只可以看
                if (userInfo.getCode() != null && ProductProgramPraise.ORDER_EXIST_REFUND_CODE.equals(userInfo.getCode())) {
                    // 存在申请退款
                    msgResult = MessageConstant.ALADDIN_ORDER_REFUND;
                    code = HttpResponseCode.USER_REFUND;
                }
            }
            throw new BusinessException(code, msgResult);
        }

        JSONObject param = new JSONObject();
        param.put("source", request.getOsType());
        List<Integer> roomIdList = request.getRoomIdList();
        param.put("roomIds", roomIdList);
        param.put("userId", userId);
        param.put("orderId", userInfo.getMasterOrderId());// 全品家大订单ID
        param.put("customerHouseId", request.getCustomerHouseId() == null ? request.getHouseId() : request.getCustomerHouseId());// 房产ID

        setRoomEffectImg(param, request);

        // 增配包数据
        if (CollectionUtils.isNotEmpty(request.getAddBagList())) {
            Map<Integer, Integer> skuCountMap = new HashMap<Integer, Integer>();
            List<Integer> skuIdList = new ArrayList<Integer>();
            for (AddBagCreateRequest bag : request.getAddBagList()) {
                skuIdList.add(bag.getSkuId());
                skuCountMap.put(bag.getSkuId(), bag.getSkuCount());
            }
            List<SolutionAddBagInfoVo> bagProducts = programProxy.queryExtraItemWithSkuIds(skuIdList);

            List<AddBagCreateRequest> softAddBagProducts = new ArrayList<AddBagCreateRequest>();
            List<AddBagCreateRequest> hardAddBagProducts = new ArrayList<AddBagCreateRequest>();

            for (SolutionAddBagInfoVo bag : bagProducts) {
                Integer type = bag.getType();// 增配包类型：0.软装 1.硬装
                if (type != null) {
                    AddBagCreateRequest info = new AddBagCreateRequest();
                    info.setSkuId(bag.getSkuId());
                    info.setSkuCount(skuCountMap.get(info.getSkuId()));
                    if (type == 0) {
                        softAddBagProducts.add(info);
                    } else if (type == 1) {
                        hardAddBagProducts.add(info);
                    }
                }
            }
            param.put("softAddBagProducts", softAddBagProducts);
            param.put("hardAddBagProducts", hardAddBagProducts);
        }
        // 升级包数据
        if (CollectionUtils.isNotEmpty(request.getStandardDtos())) {
            param.put("hardStandardDtos", request.getStandardDtos());
        }

        //空间软装商品调整对象
        if (CollectionUtils.isNotEmpty(request.getReplaceProductDtos())) {
            param.put("replaceProductDtos", request.getReplaceProductDtos());
        }
        //空间硬装商品调整对象
        if (CollectionUtils.isNotEmpty(request.getReplaceHardProductDtos())) {
            param.put("replaceHardProductDtoList", request.getReplaceHardProductDtos());
        }

        if (CollectionUtils.isNotEmpty(request.getAddHardProductDtoList())) {
            param.put("addHardProductDtoList", request.getAddHardProductDtoList());
        }
        param.put("opType", opType);//1下单  2查询价格

        AladdinCreateOrderResponseVo responseVo = proxy.createOrder(param);
        if (responseVo == null || responseVo.getOrderNum() == null) {
            throw new BusinessException(MessageConstant.FAILED);
        }

        AladdinOrderResponseVo response = new AladdinOrderResponseVo();
        response.setOrderId(responseVo.getOrderNum());
        response.setHouseId(responseVo.getCustomerHouseId());
        response.setContractAmount(responseVo.getContractAmount());
        return response;
    }

    /**
     * 校验并替换硬装分类id为实时查询
     * 2018年1月19日临时添加（xiamingyu）
     *
     * @param roomHardReplaceList
     * @return
     */
    @Override
    public List<RoomReplaceHardProductDto> checkAndReplaceHardRoomClassId(List<RoomReplaceHardProductDto> roomHardReplaceList) {

        if (null == roomHardReplaceList || roomHardReplaceList.size() == 0) {
            return roomHardReplaceList;
        }

        List<Integer> skuIdList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roomHardReplaceList)) {
            for (RoomReplaceHardProductDto roomReplaceHardProductDto : roomHardReplaceList) {
                if (CollectionUtils.isNotEmpty(roomReplaceHardProductDto.getReplaceHardProductDtoList())) {
                    for (HardReplace hardReplace : roomReplaceHardProductDto.getReplaceHardProductDtoList()) {
                        skuIdList.add(hardReplace.getNewSkuId());
                    }
                }
                if (CollectionUtils.isNotEmpty(roomReplaceHardProductDto.getAddHardProductDtoList())) {
                    for (HardReplace hardReplace : roomReplaceHardProductDto.getAddHardProductDtoList()) {
                        skuIdList.add(hardReplace.getNewSkuId());
                    }
                }
                if (CollectionUtils.isNotEmpty(roomReplaceHardProductDto.getDelHardProductDtoList())) {
                    for (HardReplace hardReplace : roomReplaceHardProductDto.getDelHardProductDtoList()) {
                        skuIdList.add(hardReplace.getSkuId());
                    }
                }
            }
        }

        if (CollectionUtils.isEmpty(skuIdList)) {
            return roomHardReplaceList;
        }

        //商品中心实时查询硬装sku分类Id
        List<RoomClassIdDto> roomClassIdDtoList = productProxy.batchQueryClassIdBySkuId(skuIdList);

        //塞回原数据结构
        if (CollectionUtils.isNotEmpty(roomClassIdDtoList)) {
            for (RoomClassIdDto roomClassIdDto : roomClassIdDtoList) {
                if (CollectionUtils.isNotEmpty(roomHardReplaceList)) {
                    for (RoomReplaceHardProductDto roomReplaceHardProductDto : roomHardReplaceList) {
                        if (CollectionUtils.isNotEmpty(roomReplaceHardProductDto.getReplaceHardProductDtoList())) {
                            for (HardReplace hardReplace : roomReplaceHardProductDto.getReplaceHardProductDtoList()) {
                                if (roomClassIdDto.getSkuId().equals(hardReplace.getNewSkuId())) {
                                    hardReplace.setRoomClassId(roomClassIdDto.getRoomClassId());
                                }
                            }
                        }
                        if (CollectionUtils.isNotEmpty(roomReplaceHardProductDto.getAddHardProductDtoList())) {
                            for (HardReplace hardReplace : roomReplaceHardProductDto.getAddHardProductDtoList()) {
                                if (roomClassIdDto.getSkuId().equals(hardReplace.getNewSkuId())) {
                                    hardReplace.setRoomClassId(roomClassIdDto.getRoomClassId());
                                }
                            }
                        }
                        if (CollectionUtils.isNotEmpty(roomReplaceHardProductDto.getDelHardProductDtoList())) {
                            for (HardReplace hardReplace : roomReplaceHardProductDto.getDelHardProductDtoList()) {
                                if (roomClassIdDto.getSkuId().equals(hardReplace.getSkuId())) {
                                    hardReplace.setRoomClassId(roomClassIdDto.getRoomClassId());
                                }
                            }
                        }
                    }
                }
            }
        }

        return roomHardReplaceList;
    }

    /**
     * 查询是否可进行艾佳贷
     *
     * @param request
     * @return
     */
    @Override
    public LoanInfoResponse queryLoanPermissionInfo(ProgramOrderDetailRequest request) {
        LoanInfoResponse loanInfoResponse = new LoanInfoResponse();
        loanInfoResponse.setLoanAble(0);
        loanInfoResponse.setLoanCount(0);
        OrderDetailDto orderDetailDto = orderProxy.queryOrderSummaryInfo(request.getOrderId());
        //查询爱家贷列表
        LoanListResponseVo loanListResponseVo = loanService.queryLoanInfoList(request.getOrderId().longValue());
        Integer orderStatus = FamilyOrderStatus.getOrderStatus(orderDetailDto.getOrderStatus());
        if (loanListResponseVo != null && CollectionUtils.isNotEmpty(loanListResponseVo.getLoanList())) {
            loanInfoResponse.setLoanCount(loanListResponseVo.getLoanList().size());
        }
        if(orderStatus != null){//意向阶段到交清全款前可申请艾佳贷
            if((orderStatus>=2 && orderStatus<4)
                    || (orderStatus==4 && orderDetailDto.getOrderSubStatus()!=null && (orderDetailDto.getOrderSubStatus() == 161||orderDetailDto.getOrderSubStatus() == 162||orderDetailDto.getOrderSubStatus() == 0))){//艾家贷签约子状态为0
                loanInfoResponse.setLoanAble(1);
            }
        }
        return loanInfoResponse;
    }

    @Override
    public AladdinCreateOrderResponseVo createFamilyOrder(CreateFamilyOrderRequest request) {
        if (request == null || StringUtils.isBlank(request.getMobileNum()) || request.getUserId() == null
                || CollectionUtils.isEmpty(request.getRoomIds()) || request.getOrderId() == null) {
            throw new BusinessException(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        //剔除为null的roomId
        while (request.getRoomIds().remove(null)) {
            //do nothing
        }

        if (CollectionUtils.isNotEmpty(request.getSolutionIds())) {
            asynSendAiMessage(new SignedDnaDto(request.getUserId(), request.getOrderId()), request.getSolutionIds().get(0));
        }

        if (CollectionUtils.isNotEmpty(request.getReplaceHardProductDtos())) {
            List<RoomReplaceHardProductDto> replaceHardProductDtoList = checkAndReplaceHardRoomClassId(request.getReplaceHardProductDtos());
            request.setReplaceHardProductDtos(replaceHardProductDtoList);
        }
        if (request.getCustomerHouseId() == null) {
            request.setCustomerHouseId(request.getHouseId());
        }
        return proxy.createFamilyOrder(request);
    }

    @Override
    public AladdinCreateOrderResponseVo updateFamilyOrder(CreateFamilyOrderRequest request) {
        if (request == null || StringUtils.isBlank(request.getMobileNum()) || request.getUserId() == null
                || CollectionUtils.isEmpty(request.getRoomIds()) || request.getOrderId() == null) {
            throw new BusinessException(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        //剔除为null的roomId
        while (request.getRoomIds().remove(null)) {
            //do nothing
        }

        if (CollectionUtils.isNotEmpty(request.getSolutionIds())) {
            asynSendAiMessage(new SignedDnaDto(request.getUserId(), request.getOrderId()), request.getSolutionIds().get(0));
        }

        if (CollectionUtils.isNotEmpty(request.getReplaceHardProductDtos())) {
            List<RoomReplaceHardProductDto> replaceHardProductDtoList = checkAndReplaceHardRoomClassId(request.getReplaceHardProductDtos());
            request.setReplaceHardProductDtos(replaceHardProductDtoList);
        }
        if (request.getCustomerHouseId() == null) {
            request.setCustomerHouseId(request.getHouseId());
        }
        return proxy.updateFamilyOrder(request);
    }

    @Autowired
    private CharacterColorProxy characterColorProxy;

    /**
     * 给AI发消息
     *
     * @param signedDnaDto
     */
    private void asynSendAiMessage(SignedDnaDto signedDnaDto, Long solutionId) {
        List<TaskAction<?>> taskActions = new ArrayList<>();
        try {
            taskActions.add(() -> {
                List<DnaInfoDto> dnaInfoDtos = characterColorProxy.queryDnaBysolutionId(solutionId);
                if (CollectionUtils.isNotEmpty(dnaInfoDtos)) {
                    signedDnaDto.setDnaIdList(dnaInfoDtos.stream().map(dnaInfoDto -> dnaInfoDto.getDnaId()).collect(Collectors.toList()));
                    LOG.info("rocket message :{} to {} start", JSON.toJSONString(signedDnaDto), APP_SIGNED_DNA_TASK_TOPIC);
                    SendResult sendResult = rocketMQTemplate.syncSend(APP_SIGNED_DNA_TASK_TOPIC, signedDnaDto);
                    if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                        LOG.info("signed.dna task topic send success content {}, sendStatus:{}", JSON.toJSONString(signedDnaDto), sendResult.getSendStatus().toString());
                    } else {
                        LOG.error("signed.dna task topic send error content {} , sendStatus:{}", JSON.toJSONString(signedDnaDto), sendResult.getSendStatus().toString());
                    }
                }
                return 1;

            });
            // 执行任务
            Executor.getInvokeOuterServiceFactory().asyncExecuteTask(taskActions);
        } catch (Exception e) {
            LOG.error("signed.dna task topic send error message :", e);
        }
    }

    @Override
    public AppOrderBaseInfoResponseVo queryAppOrderBaseInfo(Integer orderId) {
        return productProgramOrderProxy.queryAppOrderBaseInfo(orderId);
    }

    @Override
    public AladdinOrderResponseVo createFamilyProgramOrder(CreateFamilyOrderRequest request) {
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        // 判断是否特定用户
        Integer userId = userDto.getId();
        UserHousePropertiesResponseVo userInfo = productProgramService.getUserSpecificProgram(userId, request.getHouseId());
        if (userInfo == null || userInfo.getSpecific() == null
                || ProductProgramPraise.PROGRAM_LIMIT_SELECT.equals(userInfo.getSpecific())) {
            String msgResult = MessageConstant.USER_NOT_SPECIFIC;
            Long code = HttpResponseCode.USER_NOT_SPECIFIC;
            // 非特定用户、只能看方案、有退款申请
            if (userInfo != null && userInfo.getSpecific() != null
                    && ProductProgramPraise.PROGRAM_LIMIT_LOOK.equals(userInfo.getSpecific())) {
                // 只可以看
                if (userInfo.getCode() != null && ProductProgramPraise.ORDER_EXIST_REFUND_CODE.equals(userInfo.getCode())) {
                    // 存在申请退款
                    msgResult = MessageConstant.ORDER_DRAWBACK;
                    code = HttpResponseCode.USER_REFUND;
                }
            }
            throw new BusinessException(code, msgResult);
        }
        // 特定用户，可以下单
        request.setUserId(userDto.getId());
        request.setMobileNum(userDto.getMobile());
        request.setOrderId(userInfo.getMasterOrderId());
        LOG.info("ProductProgramOrderController.createOrderOrQueryPrice params:" + JsonUtils.obj2json(request));
        AladdinCreateOrderResponseVo result = this.createFamilyOrder(request);
        //下单价格异步校验
        if (result != null && result.getContractAmount() != null) { // 非线上  不发告警
            this.CheckPrice(result, request);
        }
        if (result != null && result.getOrderNum() != null) {
            AladdinOrderResponseVo response = new AladdinOrderResponseVo();
            response.setOrderId(result.getOrderNum());
            response.setHouseId(result.getCustomerHouseId());
            response.setContractAmount(result.getContractAmount());
            return response;
        } else if (result != null) {
            String msg = OrderErrorCodeEnum.getShowMsgByCode(null == result.getCode() ? 2 : result.getCode());
            throw new BusinessException(StringUtils.isEmpty(msg) ? OrderErrorCodeEnum.getShowMsgByCode(2) : msg);
        } else {
            throw new BusinessException(OrderErrorCodeEnum.getShowMsgByCode(2));
        }
    }

    /**
     * 根据草稿查询订单价格
     *
     * @param request
     * @return
     */
    @Override
    public QueryPreSignResponse queryPreSignInfo(QueryPreSignRequest request) {
        // 组装草稿
        DraftInfoResponse solutionDraftResponse = new DraftInfoResponse();
        CreateFamilyOrderRequest orderRequest = new CreateFamilyOrderRequest();
        if (request.getOpType() == 2) {// 草稿ID
            Map<String, Object> params = new HashMap<>();
            params.put("draftId", request.getDraftId());
            solutionDraftResponse = homeCardWcmProxy.queryDraftInfo(params);
        } else if (request.getOpType() == 1) { // 保存草稿并下单
            solutionDraftResponse = JSON.parseObject(JSON.toJSONString(request), DraftInfoResponse.class);
            solutionDraftResponse.setOrderNum(request.getOrderId());
        }
        // 组装下单参数
        homeV5PageService.setOrderRequest(orderRequest, solutionDraftResponse);
        Map<String, Object> stringObjectMap = concurrentQueryPreSignInfo(solutionDraftResponse.getOrderNum(), orderRequest);
        // 预算下单价格
        AladdinOrderResponseVo result = (AladdinOrderResponseVo) stringObjectMap.get(ConcurrentTaskEnum.QUERY_FAMILY_ORDER_PRICE.name());
        QueryPreSignResponse queryPreSignResponse = new QueryPreSignResponse();
        queryPreSignResponse.setContractAmount(BigDecimal.ZERO);
        if (result != null && result.getContractAmount() != null) {
            queryPreSignResponse.setContractAmount(result.getContractAmount());
        }

        // 查询合同模板URL
        DicDto dicVo = (DicDto) stringObjectMap.get(ConcurrentTaskEnum.QUERY_SERVICE_CONTRACT_INFO.name());
        if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
            queryPreSignResponse.setServiceContractUrl(dicVo.getValueDesc());
            queryPreSignResponse.setServiceContractName("《" + ElectronicContractTypeEnum.SERVICE_CONTRACT.getDescription() + "》");
        }

        List<String> refundDescList = new ArrayList();
        // 提交签约说明
        KeywordListResponseVo listResponseVo = (KeywordListResponseVo) stringObjectMap.get(ConcurrentTaskEnum.QUERY_PRESIGN_INFO.name());
        if (listResponseVo != null && CollectionUtils.isNotEmpty(listResponseVo.getKeywordList())) {
            List<KeywordVo> keywordList = listResponseVo.getKeywordList();
            for (KeywordVo keywordVo : keywordList) {
                if (keywordVo != null) {
                    List<String> words = keywordVo.getWords();
                    for (String str : words) {
                        refundDescList.add(str);
                    }
                }
            }
        }
        OrderDetailDto orderDetailDto = (OrderDetailDto) stringObjectMap.get(ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name());
        FamilyOrderPayResponse familyOrderPayResponse = queryPayBaseInfo(orderDetailDto);
        if (isFinalAndPaidAmountEmpty(familyOrderPayResponse)) {
            if (familyOrderPayResponse.getFinalOrderPrice().getValue().compareTo(familyOrderPayResponse.getPaidAmount()) == 0) {
                queryPreSignResponse.setAllMoney(1);
            } else if (familyOrderPayResponse.getFinalOrderPrice().getValue().compareTo(familyOrderPayResponse.getPaidAmount()) < 0) {
                queryPreSignResponse.setAllMoney(2);
            }
        }
        queryPreSignResponse.setPreSignInfoList(refundDescList);
        return queryPreSignResponse;
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
     * 预签约说明
     *
     * @param orderId
     * @return
     */
    private Map<String, Object> concurrentQueryPreSignInfo(Integer orderId, CreateFamilyOrderRequest orderRequest) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        //查询合同模板URL
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return dicProxy.queryDicByKey(ElectronicContractTypeEnum.SERVICE_CONTRACT.getKey());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SERVICE_CONTRACT_INFO.name();
            }
        });
        //提交签约说明
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                List<String> refundList = new ArrayList();
                refundList.add(ProductProgramPraise.KEYWORD_PRESIGN_DESC);
                return keywordWcmProxy.getKeywordList(refundList);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_PRESIGN_INFO.name();
            }
        });
        //收银台接口
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.queryOrderSummaryInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name();
            }
        });
        // 预算下单价格
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return queryFamilyOrderPrice(orderRequest);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_FAMILY_ORDER_PRICE.name();
            }
        });
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 收银台简单内容组装
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
            response.setPreferentialAmount(orderDetailDto.getPreferentialAmount());
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
            BigDecimal contractAmount = orderDetailDto.getContractAmount() == null ? BigDecimal.ZERO : orderDetailDto.getContractAmount();

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

    @Override
    public AladdinOrderResponseVo queryFamilyOrderPrice(QueryFamilyOrderPriceRequest request) {

        if (request == null || CollectionUtils.isEmpty(request.getRoomIds()) || request.getOrderId() == null) {
            return null;
        }
        //剔除为null的roomId
        while (request.getRoomIds().remove(null)) {
            //do nothing
        }
        if (request.getCustomerHouseId() == null) {
            request.setCustomerHouseId(request.getHouseId());
        }

        if (CollectionUtils.isNotEmpty(request.getReplaceHardProductDtos())) {
            List<RoomReplaceHardProductDto> replaceHardProductDtoList = checkAndReplaceHardRoomClassId(request.getReplaceHardProductDtos());
            request.setReplaceHardProductDtos(replaceHardProductDtoList);
        }

        AladdinQueryFamilyOrderPriceResponseVo queryFamilyOrderPriceResult = proxy.queryFamilyOrderPrice(request);

        if (queryFamilyOrderPriceResult != null && queryFamilyOrderPriceResult.getOrderNum() != null) {
            AladdinOrderResponseVo response = new AladdinOrderResponseVo();
            response.setOrderId(queryFamilyOrderPriceResult.getOrderNum());
            response.setHouseId(queryFamilyOrderPriceResult.getHouseId());
            response.setContractAmount(queryFamilyOrderPriceResult.getContractAmount());
            return response;
        } else if (queryFamilyOrderPriceResult != null) {
            String msg = OrderErrorCodeEnum.getShowMsgByCode(
                    null == queryFamilyOrderPriceResult.getCode() ? 2 : queryFamilyOrderPriceResult.getCode());
            throw new BusinessException(StringUtils.isEmpty(msg) ? OrderErrorCodeEnum.getShowMsgByCode(2) : msg);
        }
        throw new BusinessException(OrderErrorCodeEnum.getShowMsgByCode(2));
    }


    @Override
    public SolutionOrderInfo querySolutionOrderInfo(Integer orderId, Integer widthBag) {
        SolutionOrderInfo solutionOrderInfo = new SolutionOrderInfo();
        SolutionOrderInfoResponse aladdinOrderResponseVo = querySolutionOrderInfo(orderId);

        // 已选方案信息
        AladdinProgramInfoVo solutionSelectedInfo = aladdinOrderResponseVo.getSolutionSelectedInfo();

        Integer width = widthBag * ImageSize.WIDTH_PER_SIZE_26 / ImageSize.WIDTH_PER_SIZE_100;

        if (solutionSelectedInfo != null) {
            List<SolutionRoomDetailVo> solutionRoomDetailVoList = aladdinOrderResponseVo.getSolutionRoomDetailVoList();

            // 空间图片
            List<String> roomAllUrls = new ArrayList<String>();
            if (CollectionUtils.isNotEmpty(solutionRoomDetailVoList)) {
                for (SolutionRoomDetailVo detail : solutionRoomDetailVoList) {
                    List<SolutionRoomPicVo> solutionRoomPicVoList = detail.getSolutionRoomPicVoList();

                    if (CollectionUtils.isNotEmpty(solutionRoomPicVoList)) {
                        for (SolutionRoomPicVo pic : solutionRoomPicVoList) {
                            String url = pic.getSolutionRoomPicURL();
                            if (StringUtils.isNotBlank(url)) {
                                roomAllUrls.add(AliImageUtil.imageCompress(url, 2, width, ImageConstant.SIZE_MIDDLE));
                            }
                        }
                    }
                }
            }

            // 所有空间的图
            List<Map> roomAllUrlsAndNames = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(solutionRoomDetailVoList)) {
                for (SolutionRoomDetailVo detail : solutionRoomDetailVoList) {
                    List<SolutionRoomPicVo> solutionRoomPicVoList = detail.getSolutionRoomPicVoList();

                    if (CollectionUtils.isNotEmpty(solutionRoomPicVoList)) {
                        for (SolutionRoomPicVo pic : solutionRoomPicVoList) {
                            String url = pic.getSolutionRoomPicURL();
                            if (StringUtils.isNotBlank(url)) {
                                Map map = new HashMap();
                                map.put("roomUrl", AliImageUtil.imageCompress(url, 2, width, ImageConstant.SIZE_MIDDLE));
                                map.put("roomName", detail.getRoomUsageName());
                                sort(map, detail.getRoomUsageName());
                                roomAllUrlsAndNames.add(map);
                            }
                        }
                    }
                }
            }

            Collections.sort(roomAllUrlsAndNames, new Comparator<Map>() {
                @Override
                public int compare(Map map1, Map map2) {
                    int sort1 = (int) map1.get("sort");
                    int sort2 = (int) map2.get("sort");

                    return sort1 - sort2;
                }
            });

            solutionOrderInfo.setRoomAllUrlsAndNames(roomAllUrlsAndNames);

            solutionOrderInfo.setRoomAllUrls(roomAllUrls);
        }

        return solutionOrderInfo;
    }

    private SolutionOrderInfoResponse querySolutionOrderInfo(Integer orderId) {
        SolutionOrderInfoResponse masterOrderInfoVo = new SolutionOrderInfoResponse();
        // 查询出大订单详情
        AppMasterOrderDetailDto masterOrderResultDto = personalCenterProxy.queryMasterOrderDetail(orderId);

        AladdinProgramInfoVo solutionSelectedInfo = new AladdinProgramInfoVo();
        // 获取空间商品图片
        List<SolutionRoomDetailVo> solutionRoomDetailVoList = new ArrayList<>();
        SolutionRoomDetailVo solutionRoomDetailVo = null;

        // 方案下单时候查询方案信息,代客下单不查
        if (!OrderConstant.VALETORDER_SOURCE.equals(masterOrderResultDto.getMasterOrderInfo().getSource())) {
            List<SnapshotOrderRoomInfoDto> snapshotOrderRoomInfoDtoList = proxy.batchQueryRoomSkuInfo(orderId);

            if (null == snapshotOrderRoomInfoDtoList) {
                snapshotOrderRoomInfoDtoList = new ArrayList<>();
            }

            // 所有空间风格
            List<AladdinStyleInfoVo> styleList = new ArrayList<AladdinStyleInfoVo>();

            for (SnapshotOrderRoomInfoDto item : snapshotOrderRoomInfoDtoList) {

                if (null == item.getOrderNum() || !orderId.equals(item.getOrderNum())) {
                    continue;
                }

                if (null == solutionSelectedInfo.getId()) {
                    solutionSelectedInfo.setId(item.getSolutionId());
                    solutionSelectedInfo.setName(item.getSolutionName());
                    // 方案类型
                    solutionSelectedInfo.setType(item.getDecorationType());
                }

                AladdinStyleInfoVo styleInfoVo = new AladdinStyleInfoVo();
                styleInfoVo.setName(item.getSolutionStyleName());
                styleList.add(styleInfoVo);

                solutionRoomDetailVo = new SolutionRoomDetailVo();
                // 组装快照空间到方案空间vo
                convertSolutionRoomDetail(solutionRoomDetailVo, item);
                solutionRoomDetailVoList.add(solutionRoomDetailVo);
            }

            // 获取客厅信息
            LivingRoomDto livingRoomDto = getLivingRoomInfo(snapshotOrderRoomInfoDtoList);
            if (null != livingRoomDto) {
                solutionSelectedInfo.setPic(livingRoomDto.getSolutionHeadImgURL());
                solutionSelectedInfo.setSeriesStr(livingRoomDto.getSeriesStr());
            }

            // 没有客厅取第一个
            if (!CollectionUtils.isEmpty(snapshotOrderRoomInfoDtoList) && null == livingRoomDto) {
                solutionSelectedInfo.setPic(snapshotOrderRoomInfoDtoList.get(0).getRoomImage());
                solutionSelectedInfo.setSeriesStr(snapshotOrderRoomInfoDtoList.get(0).getSuitName());
            }

            // 获取家具总数
            solutionSelectedInfo.setFurnitureCount(getRoomSkuCount(snapshotOrderRoomInfoDtoList));

            solutionSelectedInfo.setStyleList(styleList);
        }

        masterOrderInfoVo.setSolutionSelectedInfo(solutionSelectedInfo);
        masterOrderInfoVo.setSolutionRoomDetailVoList(solutionRoomDetailVoList);
        return masterOrderInfoVo;
    }

    /**
     * 获取总家具数
     *
     * @param snapshotOrderRoomInfoDtoList
     * @return
     */
    private Integer getRoomSkuCount(List<SnapshotOrderRoomInfoDto> snapshotOrderRoomInfoDtoList) {
        if (null == snapshotOrderRoomInfoDtoList || snapshotOrderRoomInfoDtoList.isEmpty()) {
            return Integer.valueOf(0);
        }

        // 总家具数
        Integer allRoomSkuCount = Integer.valueOf(0);
        for (SnapshotOrderRoomInfoDto item : snapshotOrderRoomInfoDtoList) {
            List<SnapshotOrderRoomSkuInfo> snapshotOrderRoomSkuInfoList = item.getSnapshotOrderRoomSkuInfos();
            if (null == snapshotOrderRoomSkuInfoList) {
                continue;
            }
            // 单个空间sku总数
            Integer allSkuCount = Integer.valueOf(0);
            for (SnapshotOrderRoomSkuInfo itemInner : snapshotOrderRoomSkuInfoList) {
                if (null != itemInner.getProductCount()) {
                    // 家具类型为赠品不统计在其中 (2018.11.19修改为统计所有类型的家具数)
//					Integer furnitureType = itemInner.getFurnitureType();
//					if (furnitureType != null && furnitureType == 2) {
//						continue;
//					}
                    allSkuCount = allSkuCount + itemInner.getProductCount();
                }
            }

            allRoomSkuCount = allRoomSkuCount + allSkuCount;
        }
        return allRoomSkuCount;
    }

    /**
     * 获取客厅信息
     *
     * @return
     */
    private LivingRoomDto getLivingRoomInfo(List<SnapshotOrderRoomInfoDto> snapshotOrderRoomInfoDtoList) {
        if (null == snapshotOrderRoomInfoDtoList || snapshotOrderRoomInfoDtoList.isEmpty()) {
            return null;
        }

        for (SnapshotOrderRoomInfoDto item : snapshotOrderRoomInfoDtoList) {
            // 判断该空间是否是客厅
            if (RoomUseEnum.LIVING_ROOM.getDescription().equals(item.getRoomName())) {
                List<SnapshotPictureInfoDto> snapshotPictureInfoDtoList = item.getPictureVoInfos();
                if (null == snapshotPictureInfoDtoList) {
                    snapshotPictureInfoDtoList = new ArrayList<>();
                }
                LivingRoomDto livingRoomDto = new LivingRoomDto();
                livingRoomDto.setSeriesStr(item.getSuitName());
                livingRoomDto.setSolutionHeadImgURL(item.getRoomImage());
                return livingRoomDto;
            }
        }
        return null;
    }

    /**
     * 封装快照空间到空间vo
     *
     * @param
     * @param item
     */
    private void convertSolutionRoomDetail(SolutionRoomDetailVo solutionRoomDetailVo, SnapshotOrderRoomInfoDto item) {
        solutionRoomDetailVo.setRoomDesc(item.getHardListDesc());
        solutionRoomDetailVo.setRoomId(item.getRoomId());
        solutionRoomDetailVo.setRoomUsageName(item.getRoomName());

        // 空间价格没有
        solutionRoomDetailVo.setRoomHardDecorationSalePrice(item.getRoomHardSalePrice());
        solutionRoomDetailVo.setRoomSalePrice(item.getRoomSalePrice());
        solutionRoomDetailVo.setRoomSoftDecorationSalePrice(item.getRoomSoftSalePrice());

        // solutionRoomDetailVo.setStyleId(styleId);
        // solutionRoomDetailVo.setSeriesId(seriesId);
        solutionRoomDetailVo.setStyleName(item.getSolutionStyleName());
        solutionRoomDetailVo.setSeriesName(item.getSuitName());

        List<SnapshotOrderRoomSkuInfo> snapshotOrderRoomSkuInfoList = item.getSnapshotOrderRoomSkuInfos();
        if (null == snapshotOrderRoomSkuInfoList) {
            snapshotOrderRoomSkuInfoList = new ArrayList<>();
        }

        // 单个空间sku总数
        Integer allSkuCount = Integer.valueOf(0);

        // 封装空间家具信息
        List<SolutionRoomItemVo> solutionRoomItemVoList = new ArrayList<>();

        for (SnapshotOrderRoomSkuInfo itemInner : snapshotOrderRoomSkuInfoList) {
            SolutionRoomItemVo solutionRoomItemVo = new SolutionRoomItemVo();

            // 快照空间转化为空间vo
            convertSolutionRoomItem(solutionRoomItemVo, itemInner);

            solutionRoomItemVoList.add(solutionRoomItemVo);

            if (null != itemInner.getProductCount()) {
                allSkuCount = allSkuCount + itemInner.getProductCount();
            }
        }

        solutionRoomDetailVo.setSolutionRoomItemVoList(solutionRoomItemVoList);
        solutionRoomDetailVo.setRoomItemCount(allSkuCount);

        // 封装空间图片
        List<SolutionRoomPicVo> solutionRoomPicVoList = new ArrayList<>();
        // 封装效果图片
        List<SolutionRoomPicVo> solutionRoomViewPicVoList = new ArrayList<>();

        List<SnapshotPictureInfoDto> snapshotPictureInfoDtoList = item.getPictureVoInfos();
        if (null == snapshotPictureInfoDtoList) {
            snapshotPictureInfoDtoList = new ArrayList<>();
        }
        for (SnapshotPictureInfoDto picItem : snapshotPictureInfoDtoList) {

            SolutionRoomPicVo solutionRoomPicVo = new SolutionRoomPicVo();
            solutionRoomPicVo.setIsFirst(picItem.getIsFirst());
            solutionRoomPicVo.setSolutionRoomPicURL(picItem.getSolutionRoomPicURL());

            solutionRoomPicVoList.add(solutionRoomPicVo);
            if (picItem.getType() == 1) {
                solutionRoomViewPicVoList.add(solutionRoomPicVo);
            }
        }

        solutionRoomDetailVo.setSolutionRoomPicVoList(solutionRoomPicVoList);
        solutionRoomDetailVo.setSolutionRoomViewPicVoList(solutionRoomViewPicVoList);
    }

    /**
     * 快照空间转化为方案空间sku vo
     *
     * @param solutionRoomItemVo
     * @param snapshotOrderRoomSkuInfo
     */
    private void convertSolutionRoomItem(SolutionRoomItemVo solutionRoomItemVo,
                                         SnapshotOrderRoomSkuInfo snapshotOrderRoomSkuInfo) {
        if (null != snapshotOrderRoomSkuInfo.getFurnitureType()) {
            solutionRoomItemVo.setFurnitureType(snapshotOrderRoomSkuInfo.getFurnitureType());
        }
        solutionRoomItemVo.setItemBrand(snapshotOrderRoomSkuInfo.getBrand());
        // 颜色
        solutionRoomItemVo.setItemColor(snapshotOrderRoomSkuInfo.getProductColor());
        solutionRoomItemVo.setItemCount(snapshotOrderRoomSkuInfo.getProductCount());
        solutionRoomItemVo.setItemMaterial(snapshotOrderRoomSkuInfo.getMaterial());
        solutionRoomItemVo.setItemName(snapshotOrderRoomSkuInfo.getProductName());
        solutionRoomItemVo.setProductImage(snapshotOrderRoomSkuInfo.getProductImage());
        solutionRoomItemVo.setSkuPrice(snapshotOrderRoomSkuInfo.getProductPrice());

        String size = StringTemplateUtil.format("{}*{}*{}", null == snapshotOrderRoomSkuInfo.getLength() ? ""
                : snapshotOrderRoomSkuInfo.getLength(), null == snapshotOrderRoomSkuInfo.getWidth() ? ""
                : snapshotOrderRoomSkuInfo.getWidth(), null == snapshotOrderRoomSkuInfo.getHeight() ? ""
                : snapshotOrderRoomSkuInfo.getHeight());

        solutionRoomItemVo.setItemSize(size);
        // 商品排序序号
        solutionRoomItemVo.setSequenceNum(snapshotOrderRoomSkuInfo.getSequenceNum());
        if (null != snapshotOrderRoomSkuInfo.getSkuId()) {
            solutionRoomItemVo.setSkuId(snapshotOrderRoomSkuInfo.getSkuId());
        }
    }

    public void setRoomEffectImg(JSONObject jo, ProgramOrderCreateRequest request) {
        if (request != null && !CollectionUtils.isEmpty(request.getReplaceProductDtos())) {

            JSONArray ja = new JSONArray();

            List<ReplaceProductCreateRequest> replaceProductCreateRequests = request.getReplaceProductDtos();

            replaceProductCreateRequests.forEach(p -> {

                JSONObject ro = new JSONObject();

                Integer roomId = p.getRoomId();

                List<ReplaceProductRequest> productDtos = p.getProductDtos();

                if (roomId == null || CollectionUtils.isEmpty(productDtos)) {
                    return;
                }

                List<String> imgs = p.getVisualImgs();

                ro.put("roomId", roomId);
                ro.put("pictureUrls", imgs);
                ja.add(ro);
            });

            jo.put("roomEffectImageDtos", ja);
        }
    }


    @Override
    public OrderResponse orderDetail(ProgramOrderDetailRequest request) {
        Integer orderId = request.getOrderId();
        OrderResponse order = getSolutionOrderById(orderId);
        if (order == null) {
            return null;
        }
        Date createTime = order.getCreateTime();
        if (null != createTime) {
            String createDateStr = dayFormat.format(createTime);
            order.setCreateDateStr(createDateStr);
        }
        Integer orderType = order.getOrderType();
        if (OrderConstant.ORDER_TYPE_SUIT.equals(orderType)) {
            // 套装订单
            SolutionDetailResponseVo solutionDetail = order.getSolutionDetail();
            if (solutionDetail != null) {
                String headImg = solutionDetail.getSolutionHeadImgURL();
                if (StringUtils.isNotBlank(headImg)) {
                    Integer width = request.getWidth();
                    if (width != null && width > 0) {
                        width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
                        headImg = QiniuImageUtils.compressImageAndDiffPic(headImg, width, -1);
                    }
                    solutionDetail.setSolutionHeadImgURL(headImg);
                }
                Number solutionDiscount = solutionDetail.getSolutionDiscount();
                if (solutionDiscount != null && solutionDiscount.intValue() > 0) {
                    solutionDetail.setSolutionDiscountStr(solutionDiscount.intValue() + "%");
                }
            }
        } else if (OrderConstant.ORDER_TYPE_ROOM.equals(orderType)) {
            // 空间组合订单
            SolutionDetailResponseVo solutionDetail = order.getSolutionDetail();
            if (solutionDetail == null) {
                solutionDetail = new SolutionDetailResponseVo();
            }
            solutionDetail.setSolutionTotalSalePrice(order.getTotalPrice());
            solutionDetail.setSolutionTotalItemCount(order.getProductCount());
            List<SolutionRoomDetailVo> solutionRoomDetailVoList = solutionDetail.getSolutionRoomDetailVoList();
            if (CollectionUtils.isNotEmpty(solutionRoomDetailVoList)) {
                for (SolutionRoomDetailVo roomDetail : solutionRoomDetailVoList) {
                    List<SolutionRoomPicVo> solutionRoomPicVoList = roomDetail.getSolutionRoomPicVoList();
                    if (CollectionUtils.isNotEmpty(solutionRoomPicVoList)) {
                        for (SolutionRoomPicVo pic : solutionRoomPicVoList) {
                            Integer isFirst = pic.getIsFirst();// 是否首图标志位 0不是 1是
                            if (isFirst != null && isFirst == 1) {
                                String headImg = pic.getSolutionRoomPicURL();
                                if (StringUtils.isNotBlank(headImg)) {
                                    Integer width = request.getWidth();
                                    if (width != null && width > 0) {
                                        width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
                                        headImg = QiniuImageUtils.compressImageAndDiffPic(headImg, width, -1);
                                    }
                                    roomDetail.setRoomHeadImgURL(headImg);
                                    break;
                                }
                            }
                        }
                    }
                    List<SolutionRoomItemVo> solutionRoomItemVoList = roomDetail.getSolutionRoomItemVoList();
                    int roomItemCount = 0;
                    if (CollectionUtils.isNotEmpty(solutionRoomItemVoList)) {
                        for (SolutionRoomItemVo item : solutionRoomItemVoList) {
                            Integer itemCount = item.getItemCount();
                            if (itemCount == null) {
                                itemCount = 0;
                            }
                            roomItemCount += itemCount;
                        }
                    }
                    roomDetail.setRoomItemCount(roomItemCount);
                    roomDetail.setSolutionRoomItemVoList(null);
                    roomDetail.setSolutionRoomPicVoList(null);
                }
            }
        }
        SolutionDetailResponseVo solutionDetail = order.getSolutionDetail();
        Integer userId = order.getUserId();
        if (userId != null && userId > 0) {
            UserInfoResponse userInfo = getUserInfoResponseByUserId(userId);
            List<CashierRecordResultVo> list = null;
            try {
                list = cashProxy.queryCashierRecordsByOrderId(orderId);
            } catch (Exception e) {
                LOG.info("getUserInfoResponseByUserId ERROR:" + JsonUtils.obj2json(e));
            }
            if (CollectionUtils.isNotEmpty(list)) {
                BigDecimal totalPrice = new BigDecimal(0);
                Date cashRecordcreateTime = null;
                int i = 0;
                for (CashierRecordResultVo dto : list) {
                    if (i == 0) {
                        cashRecordcreateTime = dto.getCreateTime();
                    }
                    i++;
                    BigDecimal money = dto.getMoney();
                    if (money != null) {
                        totalPrice = totalPrice.add(money);
                    }
                }
                userInfo.setAmount(Integer.toString(totalPrice.intValue()));
                if (cashRecordcreateTime != null) {
                    userInfo.setReceiptTime(dayFormat_DAY.format(cashRecordcreateTime));
                }

            }
            solutionDetail.setUserInfo(userInfo);
            order.setSolutionDetail(solutionDetail);
        }

        // 产品方案订单支付信息
        PaymentInfo paymentInfo = order.getSolutionPaymentInfo();
        OrderPayStage orderPayStage = order.getNextOrderPayStage();
        if (paymentInfo != null && order.getActualPayMent() != null) {
            BigDecimal totalPrice = order.getActualPayMent(); // 订单总价
            BigDecimal stageTotalAmount = new BigDecimal(0);// 阶段应付总额
            BigDecimal maxStagePayRate = new BigDecimal(0);// 阶段最大支付比例
            BigDecimal minStagePayRate = new BigDecimal(0);// 阶段最小支付比例
            // 若是阶段临界值时，阶段支付比例取下一阶段值
            if (orderPayStage != null && orderPayStage.getPayStage() != null && orderPayStage.getPayStage() > 0) {
                if (orderPayStage.getMaxPayStageRatio() != null) {
                    maxStagePayRate = orderPayStage.getMaxPayStageRatio();
                }
                if (orderPayStage.getMinPayStageRatio() != null) {
                    minStagePayRate = orderPayStage.getMinPayStageRatio();
                }
                // 支付阶段
                if (ProductProgramPraise.BOSS_STAGE_ADVANCE_CODE.equals(orderPayStage.getPayStage())) {
                    maxStagePayRate = ProductProgramPraise.PAY_STAGE_INITIAL_RATE;
                    paymentInfo.setMaxStagePayRate(maxStagePayRate);
                    paymentInfo.setStagePayment(ProductProgramPraise.PAY_STAGE_INITIAL_CODE);
                    paymentInfo.setStagePaymentDesc(ProductProgramPraise.PAY_STAGE_INITIAL_DESC);
                } else if (ProductProgramPraise.BOSS_STAGE_INITIAL_CODE.equals(orderPayStage.getPayStage())) {
                    paymentInfo.setStagePayment(ProductProgramPraise.PAY_STAGE_INITIAL_CODE);
                    paymentInfo.setStagePaymentDesc(ProductProgramPraise.PAY_STAGE_INITIAL_DESC);
                } else if (ProductProgramPraise.BOSS_STAGE_INTERIM_CODE.equals(orderPayStage.getPayStage())) {
                    paymentInfo.setStagePayment(ProductProgramPraise.PAY_STAGE_INTERIM_CODE);
                    paymentInfo.setStagePaymentDesc(ProductProgramPraise.PAY_STAGE_INTERIM_DESC);
                } else if (ProductProgramPraise.BOSS_STAGE_FINAL_CODE.equals(orderPayStage.getPayStage())) {
                    paymentInfo.setStagePayment(ProductProgramPraise.PAY_STAGE_FINAL_CODE);
                    paymentInfo.setStagePaymentDesc(ProductProgramPraise.PAY_STAGE_FINAL_DESC);
                } else {
                    paymentInfo.setStagePaymentDesc("");
                }
            } else {
                if (paymentInfo.getMaxStagePayRate() != null) {
                    maxStagePayRate = paymentInfo.getMaxStagePayRate();
                }
                if (paymentInfo.getMinStagePayRate() != null) {
                    minStagePayRate = paymentInfo.getMinStagePayRate();
                }
                // 支付阶段
                if (ProductProgramPraise.BOSS_STAGE_ADVANCE_CODE.equals(paymentInfo.getStagePayment())) {
                    maxStagePayRate = ProductProgramPraise.PAY_STAGE_INITIAL_RATE;
                    paymentInfo.setMaxStagePayRate(maxStagePayRate);
                    paymentInfo.setStagePayment(ProductProgramPraise.PAY_STAGE_INITIAL_CODE);
                    paymentInfo.setStagePaymentDesc(ProductProgramPraise.PAY_STAGE_INITIAL_DESC);
                } else if (ProductProgramPraise.BOSS_STAGE_INITIAL_CODE.equals(paymentInfo.getStagePayment())) {
                    paymentInfo.setStagePayment(ProductProgramPraise.PAY_STAGE_INITIAL_CODE);
                    paymentInfo.setStagePaymentDesc(ProductProgramPraise.PAY_STAGE_INITIAL_DESC);
                } else if (ProductProgramPraise.BOSS_STAGE_INTERIM_CODE.equals(paymentInfo.getStagePayment())) {
                    paymentInfo.setStagePayment(ProductProgramPraise.PAY_STAGE_INTERIM_CODE);
                    paymentInfo.setStagePaymentDesc(ProductProgramPraise.PAY_STAGE_INTERIM_DESC);
                } else if (ProductProgramPraise.BOSS_STAGE_FINAL_CODE.equals(paymentInfo.getStagePayment())) {
                    paymentInfo.setStagePayment(ProductProgramPraise.PAY_STAGE_FINAL_CODE);
                    paymentInfo.setStagePaymentDesc(ProductProgramPraise.PAY_STAGE_FINAL_DESC);
                } else {
                    paymentInfo.setStagePaymentDesc("");
                }
            }

            // 支付阶段比例描述
            if (maxStagePayRate.compareTo(BigDecimal.ZERO) > 0) {
                paymentInfo.setMaxStagePayRateDesc(String.valueOf(maxStagePayRate.multiply(new BigDecimal(100))
                        .intValue()) + ProductProgramPraise.DISCOUNT);
            } else {
                paymentInfo.setMaxStagePayRateDesc("");
            }
            if (minStagePayRate.compareTo(BigDecimal.ZERO) > 0) {
                paymentInfo.setMinStagePayRateDesc(String.valueOf(minStagePayRate.multiply(new BigDecimal(100))
                        .intValue()) + ProductProgramPraise.DISCOUNT);
            } else {
                paymentInfo.setMinStagePayRateDesc("");
            }
            stageTotalAmount = totalPrice.multiply(maxStagePayRate);
            paymentInfo.setStageTotalAmount(stageTotalAmount);
            BigDecimal stageFillAmount = new BigDecimal(0);// 阶段已支付金额
            BigDecimal stageRemainAmount = new BigDecimal(0);// 阶段剩余应支付金额
            if (paymentInfo.getStageFillAmount() != null) {
                stageFillAmount = paymentInfo.getStageFillAmount();
                stageRemainAmount = stageTotalAmount.subtract(stageFillAmount);
            }
            paymentInfo.setStageRemainAmount(stageRemainAmount);

            order.setSolutionPaymentInfo(paymentInfo);
        }

        return order;
    }

    public UserInfoResponse getUserInfoResponseByUserId(Integer userId) {
        UserInfoResponse userInfo = null;
        if (userId != null) {
            try {
                UserDto user = userProxy.getUserById(userId);
                if (user != null) {
                    AladdinUserInfoRequest request = new AladdinUserInfoRequest();
                    request.setMobileNum(user.getMobile());
                    userInfo = productProgramService.getUserInfo(request);

                }
            } catch (Exception e) {
                LOG.info("getUserInfoResponseByUserId ERROR:" + JsonUtils.obj2json(e));
            }
        }
        if (userInfo == null) {
            userInfo = new UserInfoResponse();
        }
        return userInfo;
    }

    @Override
    public OrderResponse orderDetailById(Integer orderId, Integer width) {
        OrderResponse order = getSolutionOrderById(orderId);
        if (order == null) {
            return null;
        }
        Date createTime = order.getCreateTime();
        if (null != createTime) {
            String createDateStr = dayFormat.format(createTime);
            order.setCreateDateStr(createDateStr);
        }
        Integer orderType = order.getOrderType();
        SolutionDetailResponseVo solutionDetail = order.getSolutionDetail();
        if (OrderConstant.ORDER_TYPE_SUIT.equals(orderType)) {
            // 套装订单
            if (solutionDetail != null) {
                String headImg = solutionDetail.getSolutionHeadImgURL();
                if (StringUtils.isNotBlank(headImg)) {
                    if (width != null && width > 0) {
                        width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
                        headImg = QiniuImageUtils.compressImageAndDiffPic(headImg, width, -1);
                    }
                    solutionDetail.setSolutionHeadImgURL(headImg);
                }
                Number solutionDiscount = solutionDetail.getSolutionDiscount();
                if (solutionDiscount != null && solutionDiscount.intValue() > 0) {
                    solutionDetail.setSolutionDiscountStr(solutionDiscount.intValue() + "%");
                }
            }
        } else if (OrderConstant.ORDER_TYPE_ROOM.equals(orderType)) {
            if (solutionDetail == null) {
                solutionDetail = new SolutionDetailResponseVo();
            }
            solutionDetail.setSolutionTotalSalePrice(order.getTotalPrice());
            solutionDetail.setSolutionTotalItemCount(order.getProductCount());
            // 空间组合订单
            List<SolutionRoomDetailVo> solutionRoomDetailVoList = solutionDetail.getSolutionRoomDetailVoList();
            if (CollectionUtils.isNotEmpty(solutionRoomDetailVoList)) {
                // 如果是组合,那就获取客厅;
                for (SolutionRoomDetailVo roomDetail : solutionRoomDetailVoList) {
                    Integer roomUsageId = roomDetail.getRoomUsageId();
                    if (roomUsageId != null && roomUsageId == 1) { // roomTypeId:1
                        // 客厅
                        solutionDetail.setSolutionName(roomDetail.getSolutionName());
                        solutionDetail.setSolutionStyleName(roomDetail.getSolutionStyleName());
                        solutionDetail.setSolutionSeriesName(roomDetail.getSolutionSeriesName());
                        List<SolutionRoomPicVo> solutionRoomPicVoList = roomDetail.getSolutionRoomPicVoList();
                        if (CollectionUtils.isNotEmpty(solutionRoomPicVoList)) {
                            for (SolutionRoomPicVo pic : solutionRoomPicVoList) {
                                Integer isFirst = pic.getIsFirst();// 是否首图标志位 //
                                // 0不是 1是
                                if (isFirst != null && isFirst == 1) {
                                    String headImg = pic.getSolutionRoomPicURL();
                                    if (StringUtils.isNotBlank(headImg)) {
                                        if (width != null && width > 0) {
                                            width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
                                            headImg = QiniuImageUtils.compressImageAndDiffPic(headImg, width, -1);
                                        }
                                        solutionDetail.setSolutionHeadImgURL(headImg);
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                // 没有客厅就随机取一张
                if (StringUtils.isBlank(solutionDetail.getSolutionName())) {
                    SolutionRoomDetailVo roomDetail = solutionRoomDetailVoList.get(0);
                    solutionDetail.setSolutionName(roomDetail.getSolutionName());
                    solutionDetail.setSolutionStyleName(roomDetail.getSolutionStyleName());
                    solutionDetail.setSolutionSeriesName(roomDetail.getSolutionSeriesName());
                    List<SolutionRoomPicVo> solutionRoomPicVoList = roomDetail.getSolutionRoomPicVoList();
                    if (CollectionUtils.isNotEmpty(solutionRoomPicVoList)) {
                        for (SolutionRoomPicVo pic : solutionRoomPicVoList) {
                            Integer isFirst = pic.getIsFirst();// 是否首图标志位
                            // 0不是 1是
                            if (isFirst != null && isFirst == 1) {
                                String headImg = pic.getSolutionRoomPicURL();
                                if (StringUtils.isNotBlank(headImg)) {

                                    if (width != null && width > 0) {
                                        width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
                                        headImg = QiniuImageUtils.compressImageAndDiffPic(headImg, width, -1);
                                    }
                                    solutionDetail.setSolutionHeadImgURL(headImg);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (solutionDetail != null) {
            solutionDetail.setSolutionRoomDetailVoList(null);
        }
        return order;
    }

    private OrderResponse getSolutionOrderById(Integer orderId) {
        SolutionOrderDto solutionOrderDto = orderProxy.querySolutionOrderById(orderId);
        if (null != solutionOrderDto) {
            OrderResponse solutionOrderVo = ModelMapperUtil.strictMap(solutionOrderDto, OrderResponse.class);
            Integer payStage = solutionOrderDto.getPayStage();
            String minPayStageRatio = solutionOrderDto.getMinPayStageRatio();
            String maxPayStageRatio = solutionOrderDto.getMaxPayStageRatio();
            BigDecimal paidMoney = solutionOrderDto.getPayedMoney();
            PaymentInfo familyOrderPaymentInfo = new PaymentInfo();
            familyOrderPaymentInfo.setStagePayment(payStage);
            if (!StringUtils.isEmpty(minPayStageRatio)) {
                familyOrderPaymentInfo.setMinStagePayRate(new BigDecimal(minPayStageRatio));
            }
            if (!StringUtils.isEmpty(maxPayStageRatio)) {
                familyOrderPaymentInfo.setMaxStagePayRate(new BigDecimal(maxPayStageRatio));
            }
            familyOrderPaymentInfo.setStageFillAmount(paidMoney);
            solutionOrderVo.setSolutionPaymentInfo(familyOrderPaymentInfo);
            if (solutionOrderDto.getNextOrderPayStage() != null) {
                com.ihomefnt.oms.trade.order.dto.OrderPayStage orderPayStage = solutionOrderDto.getNextOrderPayStage();
                OrderPayStage orderNextPayStage = new OrderPayStage();
                orderNextPayStage.setPayStage(orderPayStage.getPayStage());
                String minNextPayStageRatio = orderPayStage.getMinPayStageRatio();
                String maxNextPayStageRatio = orderPayStage.getMaxPayStageRatio();
                if (!StringUtils.isEmpty(minNextPayStageRatio)) {
                    orderNextPayStage.setMinPayStageRatio(new BigDecimal(minNextPayStageRatio));
                }
                if (!StringUtils.isEmpty(maxNextPayStageRatio)) {
                    orderNextPayStage.setMaxPayStageRatio(new BigDecimal(maxNextPayStageRatio));
                }
                solutionOrderVo.setNextOrderPayStage(orderNextPayStage);
            }
            SolutionDetailResponseVo solutionDetail = solutionOrderVo.getSolutionDetail();
            if (null == solutionDetail) {
                solutionDetail = new SolutionDetailResponseVo();
                solutionOrderVo.setSolutionDetail(solutionDetail);
            }
            solutionOrderVo.getSolutionDetail()
                    .setSolutionRoomDetailVoList(ModelMapperUtil.strictMapList(
                            solutionOrderDto.getSolutionDetail().getSolutionRoomDetailDtoList(),
                            SolutionRoomDetailVo.class));

            return solutionOrderVo;
        }

        return null;
    }

    @Override
    public List<Integer> getRoomIdListBySuitId(Integer solutionId) {
        SolutionDetailResponseVo solution = programProxy.getProgramDetailById(solutionId);
        if (solution != null && !SolutionStatusEnum.ONLINE.getStatus().equals(solution.getSolutionStatus())) {
            solution = null;
        }
        if (solution == null) {
            return null;
        }
        List<SolutionRoomDetailVo> solutionRoomDetailVoList = solution.getSolutionRoomDetailVoList();
        if (CollectionUtils.isEmpty(solutionRoomDetailVoList)) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        for (SolutionRoomDetailVo room : solutionRoomDetailVoList) {
            if (room != null && room.getRoomId() != null) {
                list.add(room.getRoomId());
            }
        }
        return list;
    }

    @Override
    public List<PaymentRecordListResponse> queryPaymentRecordListByOrderId(ProgramOrderRecordRequest request) {
        List<PaymentRecordListResponse> result = Lists.newArrayList();
        if (StringUtils.isBlank(request.getOrderNum())) {
            return result;
        }
        PageModel<TransactionListVo> model = proxy.queryPayDetailInfoListWithParam(
                request.getOrderNum(), request.getType());
        if (model != null && CollectionUtils.isNotEmpty(model.getList())) {
            Map<String, List<TransactionListVo>> collect = model.getList().stream().collect(Collectors.groupingBy(TransactionListVo::getGroupTyme));
            collect.forEach((k, v) -> {
                PaymentRecordListResponse paymentRecordListResponse = new PaymentRecordListResponse();
                paymentRecordListResponse.setPayTimeGroup(k);
                paymentRecordListResponse.setRecordList(v);
                result.add(paymentRecordListResponse);
            });
            result.sort((o1, o2) -> {
                try {
                    return DateUtils.parseDate(o1.getPayTimeGroup(), "yyyy-MM-dd").before(DateUtils.parseDate(o2.getPayTimeGroup(), "yyyy-MM-dd")) ? 1 : -1;
                } catch (ParseException e) {
                    LOG.error("date parse o2o-exception , more info :", e);
                }
                return 0;
            });
            return result;
        }
        return null;
    }


    // 日期：周几
    private static String getWeek(Date date) {
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    // 日期：时分
    private static String getTimeHM(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
        int minute = cal.get(Calendar.MINUTE);// 分
        String timeHM = hour + ":" + minute + "分";
        return timeHM;
    }

    @Override
    public PaymentResultResponse queryPaymentResultByOrderId(ProgramOrderDetailRequest request) {
        PaymentResultResponse response = new PaymentResultResponse();

        String depositMoneyDefalut = "5000";
        DicDto dicVo = dicProxy.queryDicByKey(ProductProgramPraise.GLOBAL_DEPOSIT_MONEY_DEFAULT);
        // 数据库覆盖掉
        if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
            depositMoneyDefalut = dicVo.getValueDesc();
        }
        // 全局订金默认值
        response.setDepositMoneyDefalut(new BigDecimal(depositMoneyDefalut));

        // 查询全品家大订单详情
        AladdinOrderResultDto aladdinOrderResponseVo = this.queryAllProductOrderDetailById(request.getOrderId(), false);
        if (aladdinOrderResponseVo != null) {
            BigDecimal stageFillAmount = new BigDecimal(0);// 已支付金额
            BigDecimal stageRemainAmount = new BigDecimal(0);// 剩余应支付金额
            BigDecimal stageTotalAmount = new BigDecimal(0);// 应付总额
            BigDecimal orderTotalPrice = new BigDecimal(0);//订单总价
            // 订单信息
            AladdinOrderBaseInfoVo orderInfo = aladdinOrderResponseVo.getOrderInfo();
            if (orderInfo != null) {
                response.setOrderId(orderInfo.getId());
                response.setOrderNum(orderInfo.getId().toString());
                if (orderInfo.getContractAmount() != null) {
                    stageTotalAmount = orderInfo.getContractAmount();
                }
                if (orderInfo.getOrderTotalAmount() != null) {
                    orderTotalPrice = orderInfo.getOrderTotalAmount();
                }
                response.setOrderStatus(orderInfo.getOrderStatus());
            }
            // 交易信息
            AladdinDealInfoVo transactionInfo = aladdinOrderResponseVo.getTransactionInfo();
            if (transactionInfo != null) {
                if (transactionInfo.getPayedAmount() != null) {
                    stageFillAmount = transactionInfo.getPayedAmount();
                }
                if (transactionInfo.getRemainAmount() != null) {
                    stageRemainAmount = transactionInfo.getRemainAmount();
                }
            }
            if (stageFillAmount.compareTo(new BigDecimal(depositMoneyDefalut)) < 0) {
                BigDecimal depositMoneyLeft = new BigDecimal(depositMoneyDefalut).subtract(stageFillAmount);
                response.setDepositMoneyLeft(depositMoneyLeft.setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                response.setDepositMoneyLeft(BigDecimal.ZERO);
            }
            response.setStageFillAmount(stageFillAmount);
            response.setStageRemainAmount(stageRemainAmount);
            response.setStageTotalAmount(stageTotalAmount);
            //设置订单总价
            response.setFinalOrderPrice(CopyWriterAndValue.build(CopyWriterConstant.Order.ORDER_TOTAL_PRICE, orderTotalPrice));
            return response;
        } else {
            return null;
        }
    }

    @Override
    public boolean cancelPaymentRecord(CancelPaymentRecordRequest request) {
        if (request == null || request.getPayType() == null || StringUtils.isBlank(request.getResultCode())
                || request.getOrderId() == null) {
            return false;
        } else {
            // 查询是否支付失败
            String resultDesc = PaymentStatus.getResultDesc(request.getPayType(), request.getResultCode());
            if (StringUtils.isNotBlank(resultDesc)) {
                String cancelMsg = "";
                if (StringUtils.isNotBlank(request.getResultCode())) {
                    cancelMsg = request.getResultCode() + resultDesc;
                }
                LOG.info("ProductProgramOrderServiceImpl.cancelPaymentRecord payType:{},orderId:{},cancelMsg:{}",
                        request.getPayType(), request.getOrderId().toString(), cancelMsg);
                return true;
//              return proxy.cancelPaymentRecord(request.getPayType(), request.getOrderId().toString(), "", "",
//                        cancelMsg);//此接口废弃
            } else {
                return false;
            }
        }
    }

    //设置 订单基本信息中的 期望交付时间
    public String setExceptTime(AladdinOrderBaseInfoVo orderInfo) {
        try {
            if (orderInfo != null) {

                Date exceptTime = orderInfo.getExpectTime();

                if (null != exceptTime) {
                    return DateUtil.formatDate(exceptTime, "yyyy年MM月dd日");
                }
            }
        } catch (Exception e) {
            LOG.error("setExceptTime Exception ", e);
        }
        return "";
    }

    public static String formate(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        try {
            return DateUtil.formatDate(DateUtil.parseDate(str, Arrays.asList("yyyy-MM-dd HH:mm:ss")), "yyyy-MM-dd");
        } catch (Exception e) {
            LOG.error("formate Exception ", e);
        }
        return "";
    }

    @Override
    public AllProductOrderResponse queryAllProductOrderDetailById(Integer orderId, Integer width, String source) {
        Integer orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_HARDSOFT;// 默认11（套装、硬装+软装）
        String category = ProductProgramPraise.HARD_SOFT;// 默认（硬装+软装）
        Integer orderProgramTypeCode = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT;// 默认（1整套方案）
        Integer widthBag = 0;
        if (width != null && width > 0) {
            if (!source.equals("homecard")) {
                width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
            }
            widthBag = widthBag * ImageSize.WIDTH_PER_SIZE_26 / ImageSize.WIDTH_PER_SIZE_100;
        } else {
            width = 0;
        }
        AllProductOrderResponse response = new AllProductOrderResponse();
        String depositMoneyDefalut = "5000";
        DicDto dicVo = dicProxy.queryDicByKey(ProductProgramPraise.GLOBAL_DEPOSIT_MONEY_DEFAULT);
        // 数据库覆盖掉
        if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
            depositMoneyDefalut = dicVo.getValueDesc();
        }
        // 全局订金默认值
        response.setDepositMoneyDefalut(new BigDecimal(depositMoneyDefalut));
        if (orderId != null && orderId > 0) {
            AladdinOrderResultDto aladdinOrderResponseVo = this.queryAllProductOrderDetailById(orderId, true);

            if (aladdinOrderResponseVo != null && aladdinOrderResponseVo.getOrderInfo() != null) {
                if (aladdinOrderResponseVo.getOldOrder() != null) {
                    response.setOldFlag(aladdinOrderResponseVo.getOldOrder() == 0);
                }

                if (aladdinOrderResponseVo.getOrderInfo().getGradeId() != null && !aladdinOrderResponseVo.getOrderInfo().getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_CONTACT_STAGE.getStatus())) {
                    //过滤接触阶段
                    response.setGradeId(aladdinOrderResponseVo.getOrderInfo().getGradeId());
                    response.setGradeName(aladdinOrderResponseVo.getOrderInfo().getGradeName());
                }
                // 订单信息
                AladdinOrderBaseInfoVo orderInfo = aladdinOrderResponseVo.getOrderInfo();

                // ------蒋军 2018/04/12
                response.setSource(orderInfo.getSource());
                response.setCompleteTime(formate(orderInfo.getCompleteTime()));
                response.setExceptTime(setExceptTime(orderInfo));

                response.setOrderId(orderInfo.getId());
                // orderSaleType售卖类型：0：软装+硬装，1：软装 isAutoMatch 是否自由搭配
                if (orderInfo.getOrderSaleType() != null) {
                    if (ProductProgramPraise.HARD_STANDARD_ALL.equals(orderInfo.getOrderSaleType())) {
                        category = ProductProgramPraise.HARD_SOFT;
                        // 硬装+软装
                        if (orderInfo.getIsAutoMatch() == null) {

                        } else if (orderInfo.getIsAutoMatch()) {
                            // 自由搭配
                            orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_HARDSOFT;
                            orderProgramTypeCode = ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM;
                        } else {
                            // 套装
                            orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_HARDSOFT;
                            orderProgramTypeCode = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT;
                        }
                    } else if (ProductProgramPraise.HARD_STANDARD_SOFT.equals(orderInfo.getOrderSaleType())) {
                        category = ProductProgramPraise.HARD_STANDARD_SOFT_DESC;
                        // 纯软装
                        if (orderInfo.getIsAutoMatch() == null) {

                        } else if (orderInfo.getIsAutoMatch()) {
                            // 自由搭配
                            orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_SOFT;
                            orderProgramTypeCode = ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM;
                        } else {
                            // 套装
                            orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_SOFT;
                            orderProgramTypeCode = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT;
                        }
                    }
                }
                response.setOrderType(orderType); // 订单类型
                response.setOrderNum(orderInfo.getId().toString());// 大订单没有订单编号概念
                // 订单状态扭转
                if (orderInfo.getOrderStatus() != null) {
                    response.setState(homeBuildingService.getOrderStatus(orderInfo.getOrderStatus()));
                }
                response.setStateDesc(orderInfo.getOrderStatusStr());
                if (orderInfo.getCreateTime() != null) {
                    response.setCreateTime(dayFormat_DAY.format(orderInfo.getCreateTime()));
                }
                if (orderInfo.getContractAmount() != null) {
                    response.setTotalPrice(orderInfo.getContractAmount());// 合同金额
                }
                FamilyOrderPayResponse familyOrderPayResponse = queryPayBaseInfo(orderId);
                //其他优惠
                response.setOtherDisAmount(familyOrderPayResponse.getOtherDisAmount());
                //订单总价(款项优化后)
                response.setFinalOrderPrice(familyOrderPayResponse.getFinalOrderPrice());
                //剩余应付(款项优化后)
                response.setNewRestAmount(familyOrderPayResponse.getNewRestAmount());
                // 方案原价
                if (orderInfo.getPreferAmount() != null) {
                    response.setOriginalPrice(orderInfo.getPreferAmount());
                } else {
                    response.setOriginalPrice(new BigDecimal(0));
                }

                //订单优惠金额
                if (orderInfo.getDiscountAmount() != null) {
                    response.setDiscountPrice(orderInfo.getDiscountAmount());
                } else {
                    response.setDiscountPrice(new BigDecimal(0));
                }

                // 交易信息
                AladdinDealInfoVo transactionInfo = aladdinOrderResponseVo.getTransactionInfo();
                if (transactionInfo != null) {
                    if (transactionInfo.getTranstionAmount() != null) {
//                        response.setActualPayMent(familyOrderPayResponse.getContractAmount());// 应收金额
                        //应收金额改为订单总价
                        response.setActualPayMent(familyOrderPayResponse.getFinalOrderPrice().getValue());// 应收金额
                    }
                    if (transactionInfo.getRemainAmount() != null) {
                        response.setUnpaidMoney(familyOrderPayResponse.getRestAmount());// 剩余金额
                    }
                    if (transactionInfo.getPayedAmount() != null) {
                        response.setPaidMoney(transactionInfo.getPayedAmount());// 已交金额
                    }
                    BigDecimal originalAmount = transactionInfo.getOriginalAmount();// 优惠前原价
                    if (originalAmount != null) {
                        response.setOriginalAmount(originalAmount);
                    }
                    if (transactionInfo.getDstTime() != null) {
                        response.setPaymentTime(dayFormat_DAY.format(transactionInfo.getDstTime()));// 交款日期
                    }
                }

                // 用户信息
                UserInfoResponse userInfoResponse = new UserInfoResponse();
                AladdinUserInfoVo userInfo = aladdinOrderResponseVo.getUserInfo();
                if (userInfo != null) {
                    userInfoResponse.setUserId(userInfo.getId());
                    userInfoResponse.setUserName(userInfo.getUserName());
                    if (StringUtils.isNotBlank(userInfo.getMobile())) {
                        userInfoResponse.setMobileNum(userInfo.getMobile());
                        userInfoResponse.setHideMobileNum(userInfo.getMobile().replaceAll(
                                ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
                    }
                }

                // 房产信息
                HouseInfoResponseVo houseInfo = aladdinOrderResponseVo.getHouseInfo();
                if (houseInfo != null) {

                    response.setDeliverTime(houseInfo.getDeliverTime());

                    setHouseInfo(houseInfo, userInfoResponse);
                }

                // 置家顾问信息
                AladdinAdviserInfoVo adviserInfo = aladdinOrderResponseVo.getAdviserInfo();
                if (adviserInfo != null && StringUtils.isNotBlank(adviserInfo.getMobile())) {
                    userInfoResponse.setAdviserMobileNum(adviserInfo.getMobile());
                } else {
                    userInfoResponse.setAdviserMobileNum(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);
                }
                response.setUserInfo(userInfoResponse);
                response.setServiceMobile(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);// 客服

                // 可选方案信息
                AladdinBuildingProjectInfoVo buildingInfo = aladdinOrderResponseVo.getBuildingInfo();
                if (buildingInfo != null) {
                    if (CollectionUtils.isNotEmpty(buildingInfo.getBuildingHouseList())) {
                        buildingInfo.getBuildingHouseList().removeIf(aladdinHouseTypeInfoVo -> aladdinHouseTypeInfoVo.getBuildingHouseId() == null || houseInfo.getHouseTypeId() == null || !aladdinHouseTypeInfoVo.getBuildingHouseId().equals(houseInfo.getHouseTypeId()));
                        buildingInfo.setBuildingHouseCount(buildingInfo.getBuildingHouseList().size());
                    }
                    response.setSelectSolutionInfo(setSelectSolutionInfo(buildingInfo, width));
                }

                // 已选方案信息
                AladdinProgramInfoVo solutionSelectedInfo = aladdinOrderResponseVo.getSolutionSelectedInfo();
                if (solutionSelectedInfo != null) {
                    List<SolutionRoomDetailVo> solutionRoomDetailVoList = aladdinOrderResponseVo.getSolutionRoomDetailVoList();
                    response.setSolutionOrderInfo(setSolutionOrderInfo(solutionSelectedInfo, orderProgramTypeCode, solutionRoomDetailVoList, widthBag, category));
                }

                // 硬装订单信息
                AladdinHardOrderInfoVo hardOrderInfo = aladdinOrderResponseVo.getHardOrderInfo();
                if (hardOrderInfo != null) {
                    HardConstructInfo hardConstructInfo = new HardConstructInfo();
                    hardConstructInfo.setHardOrderId(hardOrderInfo.getId());
                    hardConstructInfo.setHardOrderStatus(hardOrderInfo.getHardOrderStatus());
                    hardConstructInfo.setHardOrderStatusStr(hardOrderInfo.getHardOrderStatusStr());
                    if (hardOrderInfo.getCommenceTime() != null) {
                        hardConstructInfo.setConstructTime(dayFormat_DAY.format(hardOrderInfo.getCommenceTime()));
                    }
                    response.setHardConstructInfo(hardConstructInfo);
                }

                // 软装订单信息
                AladdinSoftOrderInfoVo softOrderInfo = aladdinOrderResponseVo.getSoftOrderInfo();
                if (softOrderInfo != null) {
                    SoftDeliveryInfo softDeliveryInfo = new SoftDeliveryInfo();
                    softDeliveryInfo.setSoftOrderId(softOrderInfo.getId());
                    softDeliveryInfo.setSoftOrderStatus(softOrderInfo.getSoftOrderStatus());
                    softDeliveryInfo.setSoftOrderStatusStr(softOrderInfo.getSoftOrderStatusStr());
                    response.setSoftDeliveryInfo(softDeliveryInfo);
                }

                // 订单退款信息
                AladdinRefundInfoVo aladdinRefundInfoVo = aladdinOrderResponseVo.getRefundInfo();
                if (aladdinRefundInfoVo != null) {
                    response.setOrderRefundInfo(setRefundInfo(aladdinRefundInfoVo));
                }

                // 增配包信息
                List<AladdinAddBagInfoVo> addBagInfoVo = aladdinOrderResponseVo.getAddBags();
                if (addBagInfoVo != null && CollectionUtils.isNotEmpty(addBagInfoVo)) {
                    response.setAddBagInfo(setAddBagsInfo(addBagInfoVo, widthBag));
                }

                //标准升级包信息
//				List<StandardUpgradeInfoVo> upgradeInfoVos = aladdinOrderResponseVo.getUpgradeInfos();
//
//				if(CollectionUtils.isNotEmpty(upgradeInfoVos)){
//					List<StandardUpgradeInfo> upgradeInfos = ModelMapperUtil.strictMapList(upgradeInfoVos,StandardUpgradeInfo.class);
//					response.setUpgradeInfos(upgradeInfos);
//				}

                //非标准升级包信息
                List<NoStandardUpgradeInfoVo> noUpgradeInfoVos = aladdinOrderResponseVo.getNoUpgradeInfos();

                if (CollectionUtils.isNotEmpty(noUpgradeInfoVos)) {
                    List<NoStandardUpgradeInfo> noUpgradeInfos = new ArrayList<NoStandardUpgradeInfo>();
                    for (NoStandardUpgradeInfoVo noStandardUpgradeInfoVo : noUpgradeInfoVos) {
                        NoStandardUpgradeInfo noUpgradeInfo = ModelMapperUtil.strictMap(noStandardUpgradeInfoVo, NoStandardUpgradeInfo.class);
                        //非标准的非强制
                        Integer required = noUpgradeInfo.getRequired();
                        if (required == 0) {
                            noUpgradeInfos.add(noUpgradeInfo);
                        }
                    }
                    response.setNoUpgradeInfos(noUpgradeInfos);
                }

                // 增减项信息
                AladdinIncrementItemVo incrementResultDto = aladdinOrderResponseVo.getIncrementResultDto();
                if (incrementResultDto != null) {
                    IncrementItemInfo incrementItemInfo = setOrderIncrementInfo(incrementResultDto, width);
                    response.setIncrementItemInfo(incrementItemInfo);
                }

                // 促销活动
                ActPageResponseVo actPageResponseVo = aladdinOrderResponseVo.getQueryPromotionResultDto();
                PromotionPageResponse promotionPage = this.getPromotion(transactionInfo, actPageResponseVo);
                response.setPromotionPage(promotionPage);

                //1219置家狂欢节
                HomeCarnivalOrderInfo carnivalOrderInfo = setHomeCarnivalInfo(aladdinOrderResponseVo.getJoinActFlag(), aladdinOrderResponseVo.getJoinTimeStr());
                response.setHomeCarnivalFlag(carnivalOrderInfo.isHomeCarnivalFlag());
                response.setHomeCarnivalTime(carnivalOrderInfo.getHomeCarnivalTime());

                //软硬装清单是否已确认
                if (aladdinOrderResponseVo.getCheckResult() != null) {
                    response.setConfirmationFlag(aladdinOrderResponseVo.getCheckResult());
                }
            }

            //电子合同数量
            List<ContractInfoResponse> contractList = queryContractListByOrderId(orderId);
            if (CollectionUtils.isNotEmpty(contractList)) {
                response.setContractNum(contractList.size());
            }

        }

        return response;
    }

    /**
     * 设置房产信息
     *
     * @param houseInfo
     * @param userInfoResponse
     */
    private void setHouseInfo(HouseInfoResponseVo houseInfo, UserInfoResponse userInfoResponse) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);

        HouseInfo houseInfoResponse = homeBuildingService.setHouseInfoStandard(houseInfo);

        // 项目名称+几+栋+几+室
        StringBuffer maintainAddress = new StringBuffer("");
        if (StringUtils.isNotBlank(houseInfo.getHouseProjectName())) {
            userInfoResponse.setBuildingName(houseInfoResponse.getBuildingName());
            maintainAddress.append(houseInfoResponse.getBuildingName());
        }
        if (StringUtils.isNotBlank(houseInfo.getHousingNum())) {
            maintainAddress.append(houseInfo.getHousingNum()).append("栋");
        }
        if (StringUtils.isNotBlank(houseInfo.getUnitNum())) {
            maintainAddress.append(houseInfo.getUnitNum()).append("单元");
        }
        if (StringUtils.isNotBlank(houseInfo.getRoomNum())) {
            maintainAddress.append(houseInfo.getRoomNum()).append("室");
        }
        userInfoResponse.setMaintainAddress(maintainAddress.toString());
        userInfoResponse.setBuildingAddress(houseInfoResponse.getBuildingAddress());
        userInfoResponse.setHouseArea(houseInfoResponse.getSize());
        userInfoResponse.setHouseName(houseInfoResponse.getHouseTypeName());
        userInfoResponse.setHousePattern(houseInfoResponse.getHousePattern());
        userInfoResponse.setHouseFullName(houseInfoResponse.getUnitRoomNo());
        userInfoResponse.setBuildingId(houseInfo.getHouseProjectId());
        userInfoResponse.setHouseId(houseInfo.getId());
        userInfoResponse.setCustomerHouseId(houseInfo.getId());
        userInfoResponse.setHouseTypeId(houseInfo.getHouseTypeId());
        userInfoResponse.setApartmentVersion(houseInfo.getApartmentVersion());
        userInfoResponse.setReformFlag(houseInfo.getReformFlag());
        userInfoResponse.setLayoutId(houseInfo.getLayoutId() == null ? 0 : houseInfo.getLayoutId().longValue());
    }

    /**
     * 设置增配包信息
     *
     * @param addBagInfoVo
     * @param widthBag
     * @return
     */
    private List<AddBagDetail> setAddBagsInfo(List<AladdinAddBagInfoVo> addBagInfoVo, Integer widthBag) {
        List<AddBagDetail> addBagInfo = new ArrayList<AddBagDetail>();
        for (AladdinAddBagInfoVo aladdinAddBagInfoVo : addBagInfoVo) {
            if (aladdinAddBagInfoVo.getSkuId() != null && aladdinAddBagInfoVo.getSkuId() > 0) {
                AddBagDetail addBagDetail = new AddBagDetail();
                int count = 0;
                BigDecimal price = new BigDecimal(0);
                addBagDetail.setSkuId(aladdinAddBagInfoVo.getSkuId());
                if (StringUtils.isNotBlank(aladdinAddBagInfoVo.getProductName())) {
                    addBagDetail.setSkuName(aladdinAddBagInfoVo.getProductName());
                }
                if (StringUtils.isNotBlank(aladdinAddBagInfoVo.getProductUrl())) {
                    addBagDetail.setSkuImgUrl(QiniuImageUtils.compressImageAndDiffPic(
                            aladdinAddBagInfoVo.getProductUrl(), widthBag, -1));
                }
                if (aladdinAddBagInfoVo.getProductCount() != null
                        && aladdinAddBagInfoVo.getProductCount() > 0) {
                    addBagDetail.setSkuCount(aladdinAddBagInfoVo.getProductCount());
                    count = aladdinAddBagInfoVo.getProductCount();
                }
                if (aladdinAddBagInfoVo.getProductPrice() != null) {
                    addBagDetail.setSkuUnitPrice(aladdinAddBagInfoVo.getProductPrice());
                    price = aladdinAddBagInfoVo.getProductPrice();
                }
                addBagDetail.setCategoryName(aladdinAddBagInfoVo.getCategoryName());
                addBagDetail.setSkuTotalPrice(price.multiply(new BigDecimal(count)).setScale(2,
                        BigDecimal.ROUND_HALF_UP));
                addBagInfo.add(addBagDetail);
            }
        }

        return addBagInfo;
    }

    /**
     * 设置订单退款信息
     *
     * @param aladdinRefundInfoVo
     * @return
     */
    private OrderRefundInfo setRefundInfo(AladdinRefundInfoVo aladdinRefundInfoVo) {
        BigDecimal refundAmount = new BigDecimal(0);
        OrderRefundInfo orderRefundInfo = new OrderRefundInfo();
        if (aladdinRefundInfoVo.getRefundTime() != null) {
            orderRefundInfo.setOrderCancelTime(DateFormatUtils.format(aladdinRefundInfoVo.getRefundTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        if (aladdinRefundInfoVo.getCancelTime() != null) {
            orderRefundInfo.setCancelTime(aladdinRefundInfoVo.getCancelTime());
        }

        if (aladdinRefundInfoVo.getRefundAount() != null) {
            orderRefundInfo.setRefundAmount(aladdinRefundInfoVo.getRefundAount());
            refundAmount = aladdinRefundInfoVo.getRefundAount();
        }
        // 退款状态扭转
        if (aladdinRefundInfoVo.getRefundStatus() != null) {
            if (ProductProgramPraise.ALADDIN_ORDER_REFUND_STATUS_WAIT_REFUND.equals(aladdinRefundInfoVo.getRefundStatus())) {
                orderRefundInfo
                        .setRefundStatus(ProductProgramPraise.ALADDIN_ORDER_REFUND_STATUS_REFUND_WAIT);// 已审核待付款
                orderRefundInfo.setRefundStatusDesc("已申请退款");
                orderRefundInfo.setSubStatusPraise("应退金额：" + refundAmount + "元，艾佳将在7个工作日内打款");
            } else if (ProductProgramPraise.ALADDIN_ORDER_REFUND_STATUS_REFUNDED.equals(aladdinRefundInfoVo.getRefundStatus())) {
                orderRefundInfo
                        .setRefundStatus(ProductProgramPraise.ALADDIN_ORDER_REFUND_STATUS_REFUND_FINISH);// 已付款
                orderRefundInfo.setRefundStatusDesc("已退金额：" + refundAmount + "元");
                orderRefundInfo.setSubStatusPraise("退款已退至银行账户，请留意银行提醒");
            }
        }
        List<String> refundDescList = new ArrayList<String>();
        // 退款说明
        List<String> refundList = new ArrayList<String>();
        refundList.add(ProductProgramPraise.KEYWORD_REFUND_DESC);
        KeywordListResponseVo listResponseVo = keywordWcmProxy.getKeywordList(refundList);
        if (listResponseVo != null && CollectionUtils.isNotEmpty(listResponseVo.getKeywordList())) {
            List<KeywordVo> keywordList = listResponseVo.getKeywordList();
            for (KeywordVo keywordVo : keywordList) {
                if (keywordVo != null) {
                    List<String> words = keywordVo.getWords();
                    for (String str : words) {
                        refundDescList.add(str);
                    }
                }
            }
        }
        orderRefundInfo.setRefundDescList(refundDescList);
        return orderRefundInfo;
    }

    /**
     * 设置已选方案信息
     *
     * @param solutionSelectedInfo
     * @param orderProgramTypeCode
     * @param solutionRoomDetailVoList
     * @param width
     * @param category
     * @return
     */
    private SolutionOrderInfo setSolutionOrderInfo(AladdinProgramInfoVo solutionSelectedInfo, Integer
            orderProgramTypeCode, List<SolutionRoomDetailVo> solutionRoomDetailVoList, Integer width, String category) {
        SolutionOrderInfo solutionOrderInfo = new SolutionOrderInfo();
        solutionOrderInfo.setOrderProgramTypeCode(orderProgramTypeCode);// 订单方案类型：1整套方案
        // 2自由搭配
        List<String> styleNameList = new ArrayList<String>();
        List<AladdinStyleInfoVo> styleList = solutionSelectedInfo.getStyleList();
        if (CollectionUtils.isNotEmpty(styleList)) {
            for (AladdinStyleInfoVo aladdinStyleInfoVo : styleList) {
                styleNameList.add(aladdinStyleInfoVo.getName());
            }
        }
        solutionOrderInfo.setStyleNameList(styleNameList);
        if (StringUtils.isNotBlank(solutionSelectedInfo.getSeriesStr())) {
            solutionOrderInfo.setSeriesName(solutionSelectedInfo.getSeriesStr());
        }
        solutionOrderInfo.setSolutionId(solutionSelectedInfo.getId());
        if (StringUtils.isNotBlank(solutionSelectedInfo.getName())) {
            solutionOrderInfo.setSolutionName(solutionSelectedInfo.getName());
        }
        if (solutionSelectedInfo.getFurnitureCount() != null) {
            solutionOrderInfo.setFurnitureTotalNum(solutionSelectedInfo.getFurnitureCount());
        }
        if (StringUtils.isNotBlank(solutionSelectedInfo.getPic())) {
            solutionOrderInfo.setHeadImgUrl(AliImageUtil.imageCompress(solutionSelectedInfo.getPic(), 2, width, ImageConstant.SIZE_MIDDLE));
        }
        solutionOrderInfo.setCategory(category);

        //空间图片
        List<String> roomAllUrls = new ArrayList<String>(); //所有空间的图
        if (CollectionUtils.isNotEmpty(solutionRoomDetailVoList)) {
            for (SolutionRoomDetailVo detail : solutionRoomDetailVoList) {
                List<SolutionRoomPicVo> solutionRoomPicVoList = detail.getSolutionRoomPicVoList();

                if (CollectionUtils.isNotEmpty(solutionRoomPicVoList)) {
                    for (SolutionRoomPicVo pic : solutionRoomPicVoList) {
                        String url = pic.getSolutionRoomPicURL();
                        if (StringUtils.isNotBlank(url)) {
                            roomAllUrls.add(AliImageUtil.imageCompress(url, 2, width, ImageConstant.SIZE_MIDDLE));
                        }
                    }
                }
            }
        }

        List<Map> roomAllUrlsAndNames = new ArrayList<>(); //所有空间的图
        if (CollectionUtils.isNotEmpty(solutionRoomDetailVoList)) {
            for (SolutionRoomDetailVo detail : solutionRoomDetailVoList) {
                List<SolutionRoomPicVo> solutionRoomPicVoList = detail.getSolutionRoomPicVoList();

                if (CollectionUtils.isNotEmpty(solutionRoomPicVoList)) {
                    for (SolutionRoomPicVo pic : solutionRoomPicVoList) {
                        String url = pic.getSolutionRoomPicURL();
                        if (StringUtils.isNotBlank(url)) {
                            Map map = new HashMap();
                            map.put("roomUrl", AliImageUtil.imageCompress(url, 2, width, ImageConstant.SIZE_MIDDLE));
                            map.put("roomName", detail.getRoomUsageName());
                            sort(map, detail.getRoomUsageName());
                            roomAllUrlsAndNames.add(map);
                        }
                    }
                }
            }
        }

        Collections.sort(roomAllUrlsAndNames, new Comparator<Map>() {
            @Override
            public int compare(Map map1, Map map2) {
                int sort1 = (int) map1.get("sort");
                int sort2 = (int) map2.get("sort");

                return sort1 - sort2;
            }
        });

        solutionOrderInfo.setRoomAllUrlsAndNames(roomAllUrlsAndNames);

        solutionOrderInfo.setRoomAllUrls(roomAllUrls);
        return solutionOrderInfo;
    }

    /**
     * 应产品需求 需要排序
     * 明宇确认 我们这边按汉字排
     * 排序逻辑 : 客厅”、“餐厅”、“主卧”、“次卧”、“儿童房”、“书房”、“老人房”  
     * 、榻榻米房、健身房、衣帽间、玄关、走廊、厨房 、卫生间 、阳台
     *
     * @param map
     * @param roomName
     */
    public void sort(Map map, String roomName) {

        if (roomName == null) {
            roomName = "";
        }
        int count;

        switch (roomName) {
            case "客厅":
                count = 1;
                break;
            case "餐厅":
                count = 2;
                break;
            case "主卧":
                count = 3;
                break;
            case "次卧":
                count = 4;
                break;
            case "儿童房":
                count = 5;
                break;
            case "书房":
                count = 6;
                break;
            case "老人房":
                count = 7;
                break;
            case "榻榻米房":
                count = 8;
                break;
            case "健身房":
                count = 9;
                break;
            case "衣帽间":
                count = 10;
                break;
            case "玄关":
                count = 11;
                break;
            case "走廊":
                count = 12;
                break;
            case "厨房":
                count = 13;
                break;
            case "卫生间":
                count = 14;
                break;
            case "阳台":
                count = 15;
                break;
            default:
                count = 100;
                break;
        }

        map.put("sort", count);
    }

    /**
     * 设置可选方案信息
     *
     * @param buildingInfo
     * @param width
     * @return
     */
    private SelectSolutionInfo setSelectSolutionInfo(AladdinBuildingProjectInfoVo buildingInfo, Integer width) {
        SelectSolutionInfo selectSolutionInfo = new SelectSolutionInfo();
        Integer selectSolutionNum = 0;// 可选方案数量
        selectSolutionInfo.setHouseTypeNum(buildingInfo.getBuildingHouseCount());
        List<AladdinHouseTypeInfoVo> buildingHouseList = buildingInfo.getBuildingHouseList();
        if (CollectionUtils.isNotEmpty(buildingHouseList)) {
            List<ProgramResponse> solutionList = new ArrayList<ProgramResponse>();
            for (AladdinHouseTypeInfoVo aladdinHouseTypeInfoVo : buildingHouseList) {
                List<AladdinProgramInfoVo> solutionAvailableList = aladdinHouseTypeInfoVo
                        .getSolutionAvailableList();// 该户型下可用的方案列表

                //去掉6套展示限制 ------ 蒋军 2018.04.23
                Integer selectMaxCount = solutionAvailableList.size();
//				if (solutionAvailableList.size() > 6) {
//					selectMaxCount = 6;
//				} else {
//					selectMaxCount = solutionAvailableList.size();
//				}
                for (int i = 0; i < selectMaxCount; i++) {
                    AladdinProgramInfoVo aladdinProgramInfoVo = solutionAvailableList.get(i);
                    ProgramResponse programResponse = new ProgramResponse();
                    programResponse.setProgramId(aladdinProgramInfoVo.getId());
                    programResponse.setName(aladdinProgramInfoVo.getName());
                    if (StringUtils.isNotBlank(aladdinProgramInfoVo.getPic())) {
                        programResponse.setHeadImgUrl(AliImageUtil.imageCompress(
                                aladdinProgramInfoVo.getPic(), 2, width, ImageConstant.SIZE_MIDDLE));
                    }
                    solutionList.add(programResponse);
                }
                selectSolutionNum = selectSolutionNum + aladdinHouseTypeInfoVo.getSolutionAvailableCount();
            }
            selectSolutionInfo.setSolutionList(solutionList);
        }
        selectSolutionInfo.setSelectSolutionNum(selectSolutionNum);
        return selectSolutionInfo;
    }

    /**
     * 获取促销活动 <br/>
     * 已付金额 ＜ 订单总价 && 订单下没有“参加成功”的活动<br/>
     * 1: 未选择活动:待开始活动数==0 && 正在进行活动数==0 <br/>
     * 2: 未选择活动:正在进行活动数==0 && 待开始活动数>=1 <br/>
     * 3: 未选择活动:正在进行活动数>=1 <br/>
     * 4: 已选择活动 <br/>
     * 已付金额 ≥ 订单总价 && 订单下没有“参加成功”的活动<br/>
     * 5:没有参加过活动<br/>
     * 订单下有“参加成功”的活动 <br/>
     * 6:参加过活动<br/>
     */
    private PromotionPageResponse getPromotion(AladdinDealInfoVo transactionInfo, ActPageResponseVo
            actPageResponseVo) {
        if (transactionInfo == null || actPageResponseVo == null) {
            return null;
        }
        List<ActResponseVo> joinedActs = actPageResponseVo.getJoinedActs();// 已参加活动列表:正在参加,已经参加成功
        List<ActResponseVo> successJoined = new ArrayList<ActResponseVo>();// 已经参加成功
        List<ActResponseVo> joining = new ArrayList<ActResponseVo>();// 正在参加
        if (CollectionUtils.isNotEmpty(joinedActs)) {
            for (ActResponseVo vo : joinedActs) {
                Integer isSuccessJoin = vo.getIsSuccessJoin();// 是否成功参加:0:否，1:是
                if (isSuccessJoin == null) {
                    continue;
                } else if (isSuccessJoin == 0) {
                    joining.add(vo);
                } else if (isSuccessJoin == 1) {
                    successJoined.add(vo);
                } else {
                    continue;
                }
            }
        }
        BigDecimal originalAmount = transactionInfo.getOriginalAmount();// 优惠前原价
        if (originalAmount == null) {
            originalAmount = new BigDecimal(0);
        }
        BigDecimal transtionAmount = transactionInfo.getTranstionAmount();// 应收金额:也就是订单总价
        if (transtionAmount == null) {
            transtionAmount = new BigDecimal(0);
        }
        BigDecimal payedAmount = transactionInfo.getPayedAmount();// 已交金额
        if (payedAmount == null) {
            payedAmount = new BigDecimal(0);
        }
        BigDecimal totalPromotionMoney = originalAmount.subtract(transtionAmount);// 活动优惠总金额
        if (totalPromotionMoney.intValue() < 0) {
            totalPromotionMoney = new BigDecimal(0);
        }
        // 计算场景6: 有“参加成功”的活动
        if (CollectionUtils.isNotEmpty(successJoined)) {
            return getSuccessJoined(successJoined, totalPromotionMoney);
        }

        // 计算场景5:没有“参加成功”的活动 &&已付金额 ≥ 订单总价
        if (payedAmount.compareTo(transtionAmount) >= 0) {
            return getNoJoind();
        }

        List<ActResponseVo> canJoinActs = actPageResponseVo.getCanJoinActs();// 可参加活动列表
        // 计算场景4: 没有“参加成功”的活动 &&已付金额 < 订单总价&& 已选择活动
        if (CollectionUtils.isNotEmpty(joining)) {
            return getSelected(joining, canJoinActs, totalPromotionMoney);
        }

        // 计算场景1: 没有“参加成功”的活动 &&已付金额 < 订单总价&& 没有选择活动&&没有可参加
        if (CollectionUtils.isEmpty(canJoinActs)) {
            return getNoExist();
        }

        // 计算场景2,3: 没有“参加成功”的活动 &&已付金额 < 订单总价&& 没有选择活动&&有可参加
        return getWaitingAndDoing(canJoinActs);
    }

    /**
     * 计算场景6: 有“参加成功”的活动
     *
     * @param successJoined
     * @param totalPromotionMoney
     * @return
     */
    private PromotionPageResponse getSuccessJoined(List<ActResponseVo> successJoined, BigDecimal
            totalPromotionMoney) {
        PromotionPageResponse page = new PromotionPageResponse();
        page.setPromotionCode(PromotionCodeEnum.SUCCESS_JOINED.getCode());
        page.setTotalPromotionMoney(totalPromotionMoney);
        List<PromotionResponse> promotionList = new ArrayList<PromotionResponse>();
        page.setPromotionList(promotionList);
        for (ActResponseVo vo : successJoined) {
            if (vo.getStartTime() == null || vo.getEndTime() == null) {
                continue;
            }
            PromotionResponse response = JsonUtils.json2obj(JsonUtils.obj2json(vo), PromotionResponse.class);
            response.setStartTimeDesc((new SimpleDateFormat("yyyy.MM.dd")).format(vo.getStartTime()));
            response.setEndTimeDesc((new SimpleDateFormat("yyyy.MM.dd")).format(vo.getEndTime()));
            response.setStatus(PromotionStatusEnum.SUCCESS_DONE.getCode());
            promotionList.add(response);
        }
        return page;
    }

    /**
     * 计算场景5:没有“参加成功”的活动 &&已付金额 ≥ 订单总价
     *
     * @return
     */
    private PromotionPageResponse getNoJoind() {
        PromotionPageResponse page = new PromotionPageResponse();
        page.setPromotionCode(PromotionCodeEnum.NO_JOINED.getCode());
        return page;
    }

    /**
     * 计算场景4: 没有“参加成功”的活动 &&已付金额 < 订单总价&& 已选择活动
     *
     * @param joining
     * @param canJoinActs
     * @param totalPromotionMoney
     * @return
     */
    private PromotionPageResponse getSelected
    (List<ActResponseVo> joining, List<ActResponseVo> canJoinActs, BigDecimal totalPromotionMoney) {
        PromotionPageResponse page = new PromotionPageResponse();
        page.setPromotionCode(PromotionCodeEnum.SELECTED.getCode());
        page.setTotalPromotionMoney(totalPromotionMoney);
        Long minTime = null;// 最小时间
        Date minEndTime = null;// 最小时间
        List<PromotionResponse> promotionList = new ArrayList<PromotionResponse>();
        page.setPromotionList(promotionList);
        for (ActResponseVo vo : joining) {
            if (vo.getStartTime() == null || vo.getEndTime() == null) {
                continue;
            }
            PromotionResponse response = JsonUtils.json2obj(JsonUtils.obj2json(vo), PromotionResponse.class);
            response.setIsSelect(PromotionSelectEnum.YES.getCode());// 是否选中:0:否，1:是
            // 计算活动状态
            Date endTime = vo.getEndTime();// 活动结束时间
            Long time = endTime.getTime() - System.currentTimeMillis();
            if (time < 24 * 60 * 60 * 1000L) {
                response.setStatus(PromotionStatusEnum.FINISHING.getCode()); // 活动状态
                // :1
                // 即将结束(离活动结束还剩24小时)
            } else {
                response.setStatus(PromotionStatusEnum.DOING.getCode()); // 活动状态
                // :2.正在进行(离活动结束超过24小时)
            }
            // 判断最小时间
            if (minTime == null) {
                minTime = time;
                minEndTime = endTime;
            } else {
                if (time < minTime) {
                    minTime = time;
                    minEndTime = endTime;
                }
            }
            response.setStartTimeDesc((new SimpleDateFormat("yyyy.MM.dd")).format(vo.getStartTime()));
            response.setEndTimeDesc((new SimpleDateFormat("yyyy.MM.dd")).format(vo.getEndTime()));
            promotionList.add(response);
        }
        // 剩余时间倒计时
        if (minTime != null && minTime > 0) {
            String timeLeft = TimeUtils.getLeftTime(minTime);
            page.setMinTime(minEndTime.getTime());
            page.setTimeLeft(timeLeft);
        } else {
            page.setTimeLeft("");
            page.setMinTime(0L);
        }
        // 有可参加活动列表
        if (CollectionUtils.isNotEmpty(canJoinActs)) {
            for (ActResponseVo vo : canJoinActs) {
                if (vo.getStartTime() == null || vo.getEndTime() == null) {
                    continue;
                }
                PromotionResponse response = JsonUtils.json2obj(JsonUtils.obj2json(vo),
                        PromotionResponse.class);
                response.setIsSelect(PromotionSelectEnum.NO.getCode());//是否选中:0:否，1:是
                Date startTime = vo.getStartTime();
                // 计算活动状态
                if (startTime.after(new Date())) {
                    response.setStatus(PromotionStatusEnum.WAITING.getCode());// 活动状态 3.即将开始
                } else {
                    Date endTime = vo.getEndTime();
                    Long time = endTime.getTime() - System.currentTimeMillis();
                    if (time < 24 * 60 * 60 * 1000L) {
                        // 活动状态 :1 即将结束(离活动结束还剩24小时)
                        response.setStatus(PromotionStatusEnum.FINISHING.getCode());
                    } else {
                        // 活动状态 :2.正在进行(离活动结束超过24小时)
                        response.setStatus(PromotionStatusEnum.DOING.getCode());
                    }
                }
                response.setStartTimeDesc((new SimpleDateFormat("yyyy.MM.dd")).format(vo.getStartTime()));
                response.setEndTimeDesc((new SimpleDateFormat("yyyy.MM.dd")).format(vo.getEndTime()));
                promotionList.add(response);
            }
        }
        this.sort(promotionList);
        return page;
    }

    /**
     * 计算场景1: 没有“参加成功”的活动 &&已付金额 < 订单总价&& 没有选择活动&&没有可参加
     *
     * @return
     */
    private PromotionPageResponse getNoExist() {
        PromotionPageResponse page = new PromotionPageResponse();
        page.setPromotionCode(PromotionCodeEnum.NOT_EXIST.getCode());
        return page;
    }

    /**
     * 计算场景2,3: 没有“参加成功”的活动 &&已付金额 < 订单总价&& 没有选择活动&&有可参加<br/>
     * 场景2,场景3区别活动 有没有正在开始<br/>
     * 只要有一个正在开始就是场景3<br/>
     *
     * @return
     */
    private PromotionPageResponse getWaitingAndDoing(List<ActResponseVo> canJoinActs) {
        PromotionPageResponse page = new PromotionPageResponse();
        Integer promotionCode = PromotionCodeEnum.WAITING.getCode();
        BigDecimal minPromotionMoney = null;// 立减活动最小金额
        Date minDate = null;
        List<PromotionResponse> promotionList = new ArrayList<PromotionResponse>();
        page.setPromotionList(promotionList);
        for (ActResponseVo vo : canJoinActs) {
            if (vo.getStartTime() == null || vo.getEndTime() == null) {
                continue;
            }
            PromotionResponse response = JsonUtils.json2obj(JsonUtils.obj2json(vo),
                    PromotionResponse.class);
            response.setIsSelect(PromotionSelectEnum.NO.getCode());//是否选中:0:否，1:是
            Date startTime = vo.getStartTime();
            // 计算活动状态
            if (startTime.after(new Date())) {
                response.setStatus(PromotionStatusEnum.WAITING.getCode());// 活动状态 3.即将开始
                if (minDate == null) {
                    minDate = startTime;
                } else {
                    if (minDate.compareTo(startTime) > 0) {
                        minDate = startTime;
                    }
                }
            } else {
                // 只要出现一次,就是场景3
                promotionCode = PromotionCodeEnum.DOING.getCode();
                ;
                Date endTime = vo.getEndTime();
                Long time = endTime.getTime() - System.currentTimeMillis();
                if (time < 24 * 60 * 60 * 1000L) {
                    // 活动状态 :1 即将结束(离活动结束还剩24小时)
                    response.setStatus(PromotionStatusEnum.FINISHING.getCode());
                } else {
                    // 活动状态 :2.正在进行(离活动结束超过24小时)
                    response.setStatus(PromotionStatusEnum.DOING.getCode());
                }
                BigDecimal promotionAmount = vo.getPromotionAmount();
                if (minPromotionMoney == null) {
                    minPromotionMoney = promotionAmount;
                } else {
                    // 增加保护
                    if (promotionAmount != null && minPromotionMoney.compareTo(promotionAmount) > 0) {
                        minPromotionMoney = promotionAmount;
                    }
                }
            }
            response.setStartTimeDesc((new SimpleDateFormat("yyyy.MM.dd")).format(vo.getStartTime()));
            response.setEndTimeDesc((new SimpleDateFormat("yyyy.MM.dd")).format(vo.getEndTime()));
            promotionList.add(response);
        }
        page.setPromotionCode(promotionCode);
        if (PromotionCodeEnum.WAITING.getCode().equals(promotionCode)) {
            if (minDate != null) {
                page.setNextTime((new SimpleDateFormat("yyyy-MM-dd日HH点")).format(minDate));// 下次活动进行时间
            }
        } else if (PromotionCodeEnum.DOING.getCode().equals(promotionCode)) {
            page.setMinPromotionMoney(minPromotionMoney);
        }
        this.sort(promotionList);
        return page;


    }

    /**
     * 排序算法
     *
     * @param list
     */
    private void sort(List<PromotionResponse> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        long t1 = System.currentTimeMillis();
        Collections.sort(list, new PromotionComparator());
        long t2 = System.currentTimeMillis();
        LOG.info("sort time:{} ms", t2 - t1);
    }

    @Override
    public boolean queryLoanQualification(Integer orderId) {
        boolean result = false;
        if (orderId != null && orderId > 0) {
            List<AladdinLoanInfoResponseVo> infoResponseVos = loanInfoProxy.queryLoanInfo(orderId);
            if (CollectionUtils.isEmpty(infoResponseVos)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public Long createLoanInfo(Integer userId, Integer orderId, BigDecimal amount) {
        if (orderId == null) {
            return null;
        }
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("orderNum", orderId);
        paramMap.put("amount", amount);
        return loanInfoProxy.createLoanInfo(paramMap);
    }

    /**
     * 代客下单详情
     *
     * @param orderId
     * @param width
     * @return
     */
    @Override
    public AllProductOrderResponse queryValetOrderDetailById(Integer orderId, Integer width) {
        Integer orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_HARDSOFT;// 默认11（套装、硬装+软装）
        String orderSaleTypeStr = "";
        String softorderStatusPraise = "";
        if (width != null && width > 0) {
            width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
        } else {
            width = 0;
        }

        AllProductOrderResponse response = new AllProductOrderResponse();

        if (orderId != null && orderId > 0) {
            AladdinOrderResultDto aladdinOrderResponseVo = this.queryAllProductOrderDetailById(orderId, true);
            if (aladdinOrderResponseVo != null && aladdinOrderResponseVo.getOrderInfo() != null) {
                // 订单信息
                AladdinOrderBaseInfoVo orderInfo = aladdinOrderResponseVo.getOrderInfo();

                //蒋军 ----- 2018/04/13
                if (orderInfo != null) {
                    response.setCompleteTime(formate(orderInfo.getCompleteTime()));
                    if (orderInfo.getGradeId() != null && !orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_CONTACT_STAGE.getStatus())) {
                        response.setGradeId(orderInfo.getGradeId());
                        response.setGradeName(orderInfo.getGradeName());
                    }
                }

                response.setOrderId(orderInfo.getId());
                // orderSaleType售卖类型：0：软装+硬装，1：软装
                if (orderInfo.getOrderSaleType() != null) {
                    if (ProductProgramPraise.HARD_STANDARD_ALL.equals(orderInfo.getOrderSaleType())) {
                        orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_HARDSOFT;
                        orderSaleTypeStr = "硬装";
                    } else if (ProductProgramPraise.HARD_STANDARD_SOFT.equals(orderInfo.getOrderSaleType())) {
                        orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_SOFT;
                    }
                }
                response.setOrderType(orderType); // 订单类型
                response.setOrderNum(orderInfo.getId().toString());// 大订单没有订单编号概念
                // 订单状态扭转
                if (orderInfo.getOrderStatus() != null) {
                    if (OrderConstant.ORDER_OMSSTATUS_TOUCH.equals(orderInfo.getOrderStatus())) {
                        response.setState(ALADDIN_ORDER_STATUS_TOUCH);// 接触状态
                    } else if (OrderConstant.ORDER_OMSSTATUS_PURPOSE.equals(orderInfo.getOrderStatus())) {
                        response.setState(ProductProgramPraise.ALADDIN_ORDER_STATUS_PURPOSE);// 意向状态
                    } else if (OrderConstant.ORDER_OMSSTATUS_HANDSEL.equals(orderInfo.getOrderStatus())) {
                        response.setState(ProductProgramPraise.ALADDIN_ORDER_STATUS_HANDSEL);// 定金状态
                    } else if (OrderConstant.ORDER_OMSSTATUS_SIGN.equals(orderInfo.getOrderStatus())) {
                        response.setState(ProductProgramPraise.ALADDIN_ORDER_STATUS_SIGN);// 签约状态
                        if (StringUtils.isNotBlank(orderSaleTypeStr)) {
                            softorderStatusPraise = "本方案包含" + orderSaleTypeStr;
                        }
                    } else if (OrderConstant.ORDER_OMSSTATUS_DELIVERY.equals(orderInfo.getOrderStatus())) {
                        response.setState(ProductProgramPraise.ALADDIN_ORDER_STATUS_DELIVERY);// 交付中状态
                        softorderStatusPraise = "软装家具饰品会在硬装结束后进场";
                    } else if (OrderConstant.ORDER_OMSSTATUS_FINISH.equals(orderInfo.getOrderStatus())) {
                        response.setState(ProductProgramPraise.ALADDIN_ORDER_STATUS_FINISH);// 已完成状态
                        softorderStatusPraise = "软装家具已打扮在您的新家中";
                    } else if (OrderConstant.ORDER_OMSSTATUS_CANCEL.equals(orderInfo.getOrderStatus())) {
                        response.setState(ProductProgramPraise.ALADDIN_ORDER_STATUS_CANCEL);// 已取消状态
                    } else {
                        response.setState(-1);// 订单状态
                    }
                }
                if (StringUtils.isNotBlank(orderInfo.getOrderStatusStr())) {
                    response.setStateDesc(orderInfo.getOrderStatusStr());
                }
                if (orderInfo.getCreateTime() != null) {
                    response.setCreateTime(dayFormat_DAY.format(orderInfo.getCreateTime()));
                }
                if (orderInfo.getContractAmount() != null) {
                    response.setTotalPrice(orderInfo.getContractAmount());// 合同额
                }
                //订单总价
                response.setFinalOrderPrice(CopyWriterAndValue.build(CopyWriterConstant.Order.ORDER_TOTAL_PRICE, orderInfo.getOrderTotalAmount()));

                // 交易信息
                AladdinDealInfoVo transactionInfo = aladdinOrderResponseVo.getTransactionInfo();
                if (transactionInfo != null) {
                    if (transactionInfo.getTranstionAmount() != null) {
                        response.setActualPayMent(transactionInfo.getTranstionAmount());// 应收金额
                    }
                    if (transactionInfo.getRemainAmount() != null) {
                        response.setUnpaidMoney(transactionInfo.getRemainAmount());// 剩余金额
                    }
                    if (transactionInfo.getPayedAmount() != null) {
                        response.setPaidMoney(transactionInfo.getPayedAmount());// 已交金额
                    }
                    if (transactionInfo.getDstTime() != null) {
                        response.setPaymentTime(dayFormat_DAY.format(transactionInfo.getDstTime()));// 交款日期
                    }
                }

                // 用户信息
                UserInfoResponse userInfoResponse = new UserInfoResponse();
                AladdinUserInfoVo userInfo = aladdinOrderResponseVo.getUserInfo();
                if (userInfo != null) {
                    userInfoResponse.setUserId(12434);
                    userInfoResponse.setUserName(userInfo.getUserName());
                    if (StringUtils.isNotBlank(userInfo.getMobile())) {
                        userInfoResponse.setMobileNum(userInfo.getMobile());
                        userInfoResponse.setHideMobileNum(userInfo.getMobile().replaceAll(
                                ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
                    }
                }

                // 房产信息
                HouseInfoResponseVo houseInfo = aladdinOrderResponseVo.getHouseInfo();
                if (houseInfo != null) {
                    setHouseInfo(houseInfo, userInfoResponse);
                }

                // 置家顾问信息
                AladdinAdviserInfoVo adviserInfo = aladdinOrderResponseVo.getAdviserInfo();
                if (adviserInfo != null && StringUtils.isNotBlank(adviserInfo.getMobile())) {
                    userInfoResponse.setAdviserMobileNum(adviserInfo.getMobile());
                } else {
                    userInfoResponse.setAdviserMobileNum(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);
                }
                response.setUserInfo(userInfoResponse);
                response.setServiceMobile(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);// 客服

                // 硬装订单信息
                AladdinHardOrderInfoVo hardOrderInfo = aladdinOrderResponseVo.getHardOrderInfo();
                if (hardOrderInfo != null) {
                    HardConstructInfo hardConstructInfo = new HardConstructInfo();
                    hardConstructInfo.setHardOrderId(hardOrderInfo.getId());
                    hardConstructInfo.setHardOrderStatus(hardOrderInfo.getHardOrderStatus());
                    hardConstructInfo.setHardOrderStatusStr(hardOrderInfo.getHardOrderStatusStr());
                    if (hardOrderInfo.getCommenceTime() != null) {
                        hardConstructInfo.setConstructTime(dayFormat_DAY.format(hardOrderInfo.getCommenceTime()));
                    }
                    response.setHardConstructInfo(hardConstructInfo);
                }

                // 软装订单信息
                AladdinSoftOrderInfoVo softOrderInfo = aladdinOrderResponseVo.getSoftOrderInfo();
                if (softOrderInfo != null) {
                    List<AppValetOrderInfoSoftDetailVo> valetSoftOrderInfo = aladdinOrderResponseVo
                            .getValetSoftOrderInfo();// 代客下单软装子订单商品列表
                    response.setSoftDeliveryInfo(setSoftDeliveryInfo(softOrderInfo, softorderStatusPraise, valetSoftOrderInfo, width));
                }

                // 增减项信息
                AladdinIncrementItemVo incrementResultDto = aladdinOrderResponseVo.getIncrementResultDto();
                if (incrementResultDto != null) {
                    IncrementItemInfo incrementItemInfo = setOrderIncrementInfo(incrementResultDto, width);
                    response.setIncrementItemInfo(incrementItemInfo);
                }

                //1219置家狂欢节
                HomeCarnivalOrderInfo carnivalOrderInfo = setHomeCarnivalInfo(aladdinOrderResponseVo.getJoinActFlag(), aladdinOrderResponseVo.getJoinTimeStr());
                response.setHomeCarnivalFlag(carnivalOrderInfo.isHomeCarnivalFlag());
                response.setHomeCarnivalTime(carnivalOrderInfo.getHomeCarnivalTime());

                //软硬装清单是否已确认
                if (aladdinOrderResponseVo.getCheckResult() != null) {
                    response.setConfirmationFlag(aladdinOrderResponseVo.getCheckResult());
                }
            }

            //电子合同数量
            List<ContractInfoResponse> contractList = queryContractListByOrderId(orderId);
            if (CollectionUtils.isNotEmpty(contractList)) {
                response.setContractNum(contractList.size());
            }
        }

        return response;
    }

    /**
     * 设置软装订单信息
     *
     * @param softOrderInfo
     * @return
     */
    private SoftDeliveryInfo setSoftDeliveryInfo(AladdinSoftOrderInfoVo softOrderInfo, String
            softorderStatusPraise, List<AppValetOrderInfoSoftDetailVo> valetSoftOrderInfo, Integer width) {
        DecimalFormat format = new DecimalFormat("###################.###########");
        Integer productTotalCount = 0;// 商品总数（不含已取消状态的商品）
        SoftDeliveryInfo softDeliveryInfo = new SoftDeliveryInfo();
        softDeliveryInfo.setSoftOrderId(softOrderInfo.getId());
        softDeliveryInfo.setSoftOrderStatus(softOrderInfo.getSoftOrderStatus());
        softDeliveryInfo.setSoftOrderStatusStr(softOrderInfo.getSoftOrderStatusStr());
        softDeliveryInfo.setSoftorderStatusPraise(softorderStatusPraise);
        if (CollectionUtils.isNotEmpty(valetSoftOrderInfo)) {
            List<ValetSoftProductInfo> valetSoftProductInfoList = new ArrayList<ValetSoftProductInfo>();
            for (AppValetOrderInfoSoftDetailVo appValetOrderInfoSoftDetailVo : valetSoftOrderInfo) {
                ValetSoftProductInfo productInfo = new ValetSoftProductInfo();
                productInfo.setProductId(appValetOrderInfoSoftDetailVo.getSkuId());
                productInfo.setProductName(appValetOrderInfoSoftDetailVo.getProductName());
                if (StringUtils.isNotBlank(appValetOrderInfoSoftDetailVo.getProductImage())) {
                    productInfo.setHeadImgUrl(QiniuImageUtils.compressImageAndDiffPic(
                            appValetOrderInfoSoftDetailVo.getProductImage(), width, -1));
                }
                productInfo.setDeliveryStatus(appValetOrderInfoSoftDetailVo.getProductStatus());
                // 待付款状态不展示
                if (appValetOrderInfoSoftDetailVo.getProductStatus() != null
                        && !ProductProgramPraise.SKU_STATUS_WAITPAY.equals(appValetOrderInfoSoftDetailVo.getProductStatus())) {
                    productInfo.setDeliveryStatusStr(appValetOrderInfoSoftDetailVo.getProductStatusStr());
                }
                if (appValetOrderInfoSoftDetailVo.getProductCount() != null) {
                    productInfo.setProductCount(appValetOrderInfoSoftDetailVo.getProductCount());
                    // 已取消状态不算在总数内
                    if (appValetOrderInfoSoftDetailVo.getProductStatus() != null
                            && !ProductProgramPraise.SKU_STATUS_CANCEL.equals(appValetOrderInfoSoftDetailVo.getProductStatus())) {
                        productTotalCount = productTotalCount
                                + appValetOrderInfoSoftDetailVo.getProductCount();
                    }
                }
                if (StringUtils.isNotBlank(appValetOrderInfoSoftDetailVo.getSpecifications())) {
                    if (appValetOrderInfoSoftDetailVo.getSpecifications().endsWith(";")) {
                        productInfo.setProductColor(appValetOrderInfoSoftDetailVo.getSpecifications()
                                .substring(0,
                                        appValetOrderInfoSoftDetailVo.getSpecifications().length() - 1));
                    } else {
                        productInfo.setProductColor(appValetOrderInfoSoftDetailVo.getSpecifications());
                    }
                }
                if (StringUtils.isNotBlank(appValetOrderInfoSoftDetailVo.getMaterial())) {
                    productInfo.setProductMaterial(appValetOrderInfoSoftDetailVo.getMaterial());
                }
                if (appValetOrderInfoSoftDetailVo.getLength() != null
                        && appValetOrderInfoSoftDetailVo.getWidth() != null
                        && appValetOrderInfoSoftDetailVo.getHeight() != null) {
                    productInfo.setProductSize(format.format(appValetOrderInfoSoftDetailVo.getLength())
                            + "*" + format.format(appValetOrderInfoSoftDetailVo.getWidth()) + "*"
                            + format.format(appValetOrderInfoSoftDetailVo.getHeight()) + "mm");
                }
                valetSoftProductInfoList.add(productInfo);
            }
            softDeliveryInfo.setValetSoftProductInfoList(valetSoftProductInfoList);
        }
        softDeliveryInfo.setProductTotalCount(productTotalCount);
        return softDeliveryInfo;
    }

    /**
     * 全品家订单增减项信息
     */
    private IncrementItemInfo setOrderIncrementInfo(AladdinIncrementItemVo incrementResultDto, Integer width) {
        IncrementItemInfo incrementItemInfo = new IncrementItemInfo();
        if (incrementResultDto != null) {
            Integer incrementItemTotalCount = 0;
            if (incrementResultDto.getDealAmount() != null) {
                incrementItemInfo.setTotalAmount(incrementResultDto.getDealAmount());
            }
            List<AppValetOrderInfoSoftDetailVo> softFitmentInfo = incrementResultDto.getSoftFitmentInfo();// 软装增减项信息
            if (CollectionUtils.isNotEmpty(softFitmentInfo)) {
                DecimalFormat format = new DecimalFormat("###################.###########");
                List<ValetSoftProductInfo> softProductInfoList = new ArrayList<ValetSoftProductInfo>();
                for (AppValetOrderInfoSoftDetailVo appValetOrderInfoSoftDetailVo : softFitmentInfo) {
                    ValetSoftProductInfo productInfo = new ValetSoftProductInfo();
                    productInfo.setProductId(appValetOrderInfoSoftDetailVo.getSkuId());
                    productInfo.setProductName(appValetOrderInfoSoftDetailVo.getProductName());
                    if (StringUtils.isNotBlank(appValetOrderInfoSoftDetailVo.getProductImage())) {
                        productInfo.setHeadImgUrl(QiniuImageUtils.compressImageAndDiffPic(
                                appValetOrderInfoSoftDetailVo.getProductImage(), width, -1));
                    }
                    productInfo.setDeliveryStatus(appValetOrderInfoSoftDetailVo.getProductStatus());
                    // 待付款状态不展示
                    if (appValetOrderInfoSoftDetailVo.getProductStatus() != null
                            && ProductProgramPraise.SKU_STATUS_WAITPAY.equals(appValetOrderInfoSoftDetailVo.getProductStatus())) {
                        productInfo.setDeliveryStatusStr(appValetOrderInfoSoftDetailVo.getProductStatusStr());
                    }
                    if (appValetOrderInfoSoftDetailVo.getProductCount() != null) {
                        productInfo.setProductCount(appValetOrderInfoSoftDetailVo.getProductCount());
                        incrementItemTotalCount = incrementItemTotalCount
                                + appValetOrderInfoSoftDetailVo.getProductCount();
                    }
                    if (StringUtils.isNotBlank(appValetOrderInfoSoftDetailVo.getSpecifications())) {
                        if (appValetOrderInfoSoftDetailVo.getSpecifications().endsWith(";")) {
                            productInfo.setProductColor(appValetOrderInfoSoftDetailVo.getSpecifications().substring(0,
                                    appValetOrderInfoSoftDetailVo.getSpecifications().length() - 1));
                        } else {
                            productInfo.setProductColor(appValetOrderInfoSoftDetailVo.getSpecifications());
                        }
                    }
                    if (StringUtils.isNotBlank(appValetOrderInfoSoftDetailVo.getMaterial())) {
                        productInfo.setProductMaterial(appValetOrderInfoSoftDetailVo.getMaterial());
                    }
                    if (appValetOrderInfoSoftDetailVo.getLength() != null
                            && appValetOrderInfoSoftDetailVo.getWidth() != null
                            && appValetOrderInfoSoftDetailVo.getHeight() != null) {
                        productInfo.setProductSize(format.format(appValetOrderInfoSoftDetailVo.getLength()) + "*"
                                + format.format(appValetOrderInfoSoftDetailVo.getWidth()) + "*"
                                + format.format(appValetOrderInfoSoftDetailVo.getHeight()) + "mm");
                    }
                    softProductInfoList.add(productInfo);
                }
                incrementItemInfo.setSoftProductInfoList(softProductInfoList);
            }

            List<AladdinStrongFitmentDetailVo> strongIncreaseInfo = incrementResultDto.getStrongIncreaseInfo();// 硬装增项信息
            if (CollectionUtils.isNotEmpty(strongIncreaseInfo)) {
                List<ValetHardItemInfo> hardChargeInfoList = new ArrayList<ValetHardItemInfo>();
                for (AladdinStrongFitmentDetailVo aladdinStrongFitmentDetailVo : strongIncreaseInfo) {
                    ValetHardItemInfo hardItemInfo = new ValetHardItemInfo();
                    if (aladdinStrongFitmentDetailVo.getItemCount() != null) {
                        hardItemInfo.setHardCount(aladdinStrongFitmentDetailVo.getItemCount());
                        incrementItemTotalCount = incrementItemTotalCount + aladdinStrongFitmentDetailVo.getItemCount();
                    }
                    if (StringUtils.isNotBlank(aladdinStrongFitmentDetailVo.getItemDesciption())) {
                        hardItemInfo.setHardItemName(aladdinStrongFitmentDetailVo.getItemDesciption());
                    }
                    if (StringUtils.isNotBlank(aladdinStrongFitmentDetailVo.getUnit())) {
                        hardItemInfo.setMeasure(aladdinStrongFitmentDetailVo.getUnit());
                    }
                    hardChargeInfoList.add(hardItemInfo);
                }
                incrementItemInfo.setHardChargeInfoList(hardChargeInfoList);
            }

            List<AladdinStrongFitmentDetailVo> strongDecreaseInfo = incrementResultDto.getStrongDecreaseInfo();// 硬装减项信息
            if (CollectionUtils.isNotEmpty(strongDecreaseInfo)) {
                List<ValetHardItemInfo> hardReductionInfoList = new ArrayList<ValetHardItemInfo>();
                for (AladdinStrongFitmentDetailVo aladdinStrongFitmentDetailVo : strongDecreaseInfo) {
                    ValetHardItemInfo hardItemInfo = new ValetHardItemInfo();
                    if (aladdinStrongFitmentDetailVo.getItemCount() != null) {
                        hardItemInfo.setHardCount(aladdinStrongFitmentDetailVo.getItemCount());
                        incrementItemTotalCount = incrementItemTotalCount + aladdinStrongFitmentDetailVo.getItemCount();
                    }
                    if (StringUtils.isNotBlank(aladdinStrongFitmentDetailVo.getItemDesciption())) {
                        hardItemInfo.setHardItemName(aladdinStrongFitmentDetailVo.getItemDesciption());
                    }
                    if (StringUtils.isNotBlank(aladdinStrongFitmentDetailVo.getUnit())) {
                        hardItemInfo.setMeasure(aladdinStrongFitmentDetailVo.getUnit());
                    }
                    hardReductionInfoList.add(hardItemInfo);
                }
                incrementItemInfo.setHardReductionInfoList(hardReductionInfoList);
            }

            incrementItemInfo.setIncrementItemTotalCount(incrementItemTotalCount);
        }

        return incrementItemInfo;
    }

    @Override
    public Integer joinPromotion(Integer orderId, List<Integer> actCodes) {
        if (actCodes == null) {
            actCodes = new ArrayList<Integer>();
        }
        AladdinOrderResultDto aladdinOrderResponseVo = this.queryAllProductOrderDetailById(orderId, false);
        if (aladdinOrderResponseVo == null) {
            return PromotionErrorEnum.ORDER_FAIL.getCode();
        }
        AladdinOrderBaseInfoVo orderInfo = aladdinOrderResponseVo.getOrderInfo();// 订单基本信息

        AladdinUserInfoVo userInfo = aladdinOrderResponseVo.getUserInfo();// 用户信息

        if (orderInfo == null) {
            return PromotionErrorEnum.ORDER_FAIL.getCode();
        }
        BigDecimal contractAmount = orderInfo.getContractAmount();// 合同金额
        if (contractAmount == null) {
            return PromotionErrorEnum.ACT_AVAIL_FAIL.getCode();
        }
        Integer companyId = orderInfo.getCompanyId();// 所属公司id
        if (companyId == null) {
            return PromotionErrorEnum.ACT_AVAIL_FAIL.getCode();
        }
        Integer buildingId = orderInfo.getBuildingId();// 所属项目id
        if (buildingId == null) {
            return PromotionErrorEnum.ACT_AVAIL_FAIL.getCode();
        }

        JoinPromotionVo param = new JoinPromotionVo();
        param.setActCodes(actCodes);
        param.setContractAmount(contractAmount);
        if (userInfo != null) {
            param.setCustomerName(userInfo.getUserName());
        }
        param.setOrderNum(orderId);
        param.setBuildingId(buildingId);
        param.setCompanyId(companyId);
        param.setOrderSource(ProductProgramPraise.ORDER_SOURCE_APP);
        return promotionProxy.confirmJoinAct(param);
    }


    /**
     * 1219置家狂欢节
     */
    public HomeCarnivalOrderInfo setHomeCarnivalInfo(Integer joinActFlag, String joinTimeStr) {
        HomeCarnivalOrderInfo carnivalOrderInfo = new HomeCarnivalOrderInfo();
        if (joinActFlag != null && joinActFlag.equals(ProductProgramPraise.ACTIVITY_FLAG_JOIN)) {
            carnivalOrderInfo.setHomeCarnivalFlag(true);
            if (StringUtils.isNotBlank(joinTimeStr)) {
                carnivalOrderInfo.setHomeCarnivalTime(joinTimeStr);
            }
        }
        return carnivalOrderInfo;
    }

    @Override
    public SoftAndHardListResponse querySoftAndHardListById(Integer orderId, Integer width) {
        SoftAndHardListResponse response = new SoftAndHardListResponse();

        if (width == null) {
            width = 0;
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderId);
        AladdinOrderDetailResponseVo aladdinOrderDetailResponseVo = proxy.queryOrderListByParam(paramMap);
        if (aladdinOrderDetailResponseVo != null) {
            OrderSoftItem orderSoftItem = new OrderSoftItem();//软装清单

            //软装基础商品
            List<SpaceEntity> spaceList = new ArrayList<SpaceEntity>();// 空间集合    软装清单
            List<AladdinSoftSpaceVo> softBaseList = aladdinOrderDetailResponseVo.getSoftBaseList();
            if (CollectionUtils.isNotEmpty(softBaseList)) {
                for (AladdinSoftSpaceVo aladdinSoftSpaceVo : softBaseList) {
                    SpaceEntity spaceEntity = new SpaceEntity();
                    spaceEntity.setRoomId(aladdinSoftSpaceVo.getRoomId());
                    if (StringUtils.isNotBlank(aladdinSoftSpaceVo.getRoomName())) {
                        spaceEntity.setSpaceName(aladdinSoftSpaceVo.getRoomName());
                    }
                    List<AladdinSoftItemVo> softItemList = aladdinSoftSpaceVo.getSoftItemList();
                    List<FurnitureEntity> furnitureList = setSoftItemInfo(softItemList, width);
                    spaceEntity.setFurnitureList(furnitureList);
                    spaceList.add(spaceEntity);
                }
                orderSoftItem.setSpaceList(spaceList);
            }

            //软装增配包
            List<AladdinSoftItemVo> aladdiSoftAddBagList = aladdinOrderDetailResponseVo.getSoftAddBagList();
            if (CollectionUtils.isNotEmpty(aladdiSoftAddBagList)) {
                List<FurnitureEntity> softAddBagList = setSoftItemInfo(aladdiSoftAddBagList, width);
                orderSoftItem.setSoftAddBagList(softAddBagList);
            }

            //增减项
            List<AladdinSoftItemVo> softFitemList = aladdinOrderDetailResponseVo.getSoftFitemList();//增减项
            if (CollectionUtils.isNotEmpty(softFitemList)) {
                List<FurnitureEntity> softIncrementList = setSoftItemInfo(softFitemList, width);
                orderSoftItem.setSoftIncrementList(softIncrementList);
            }

            //硬装清单
            OrderHardItem orderHardItem = new OrderHardItem();

            List<HardStandardSpaceResponse> hardSpaceList = new ArrayList<HardStandardSpaceResponse>();//硬装基础清单
            HardStandardRequest request = new HardStandardRequest();
            request.setOrderId(orderId);
            HardStandardDetailListResponseVo hardStandardDetail = productProgramService.queryHardStandardDetail(request);
            if (hardStandardDetail != null) {
                hardSpaceList = hardStandardDetail.getSpaceList();
            }
            orderHardItem.setHardSpaceList(hardSpaceList);

            //硬装升级项  类别、商品名称
            UpgradeInfo upgradeInfo = new UpgradeInfo();
            //标准升级项
            List<AladdinHardItemVo> upgradeInfoList = aladdinOrderDetailResponseVo.getUpgradeInfoList();
            if (CollectionUtils.isNotEmpty(upgradeInfoList)) {
                List<ItemInfo> standardUpgradeList = setHardItemInfo(upgradeInfoList, width);
                upgradeInfo.setStandardUpgradeList(standardUpgradeList);
            }

            //非标准升级项
            List<AladdinHardItemVo> noUpgradeInfoList = aladdinOrderDetailResponseVo.getNoUpgradeInfoList();
            if (CollectionUtils.isNotEmpty(noUpgradeInfoList)) {
                List<ItemInfo> nonstandardUpgradeList = setHardItemInfo(noUpgradeInfoList, width);
                upgradeInfo.setNonstandardUpgradeList(nonstandardUpgradeList);
            }
            orderHardItem.setUpgradeInfo(upgradeInfo);

            //硬装增配包  图片、名称
            List<AladdinHardItemVo> aladdinHardAddBagList = aladdinOrderDetailResponseVo.getHardAddBagList();
            if (CollectionUtils.isNotEmpty(aladdinHardAddBagList)) {
                List<ItemInfo> hardAddBagList = setHardItemInfo(aladdinHardAddBagList, width);
                orderHardItem.setHardAddBagList(hardAddBagList);
            }

            //硬装增减项  不展示减项   类目说明、数量、计量单位
            List<AladdinHardItemVo> hardInFitmentList = aladdinOrderDetailResponseVo.getHardInFitmentList();
            if (CollectionUtils.isNotEmpty(hardInFitmentList)) {
                List<ItemInfo> hardIncrementList = setHardItemInfo(hardInFitmentList, width);
                orderHardItem.setHardIncrementList(hardIncrementList);
            }

            response.setOrderSoftItem(orderSoftItem);
            response.setOrderHardItem(orderHardItem);

            //置家顾问电话
            if (StringUtils.isNotBlank(aladdinOrderDetailResponseVo.getAdviseMoblie())) {
                response.setHomeAdviserMobile(aladdinOrderDetailResponseVo.getAdviseMoblie());
            }

            //软装赠品
            List<AladdinSoftItemVo> softPresentList = aladdinOrderDetailResponseVo.getSoftPresentList();
            if (CollectionUtils.isNotEmpty(softPresentList)) {
                String activeGift = "";
                for (AladdinSoftItemVo aladdinSoftItemVo : softPresentList) {
                    if (StringUtils.isNotBlank(aladdinSoftItemVo.getBrandAndSeries())) {
                        activeGift = activeGift + aladdinSoftItemVo.getBrandAndSeries();
                        if (aladdinSoftItemVo.getProductCount() != null && aladdinSoftItemVo.getProductCount() > 0) {
                            activeGift = activeGift + "*" + aladdinSoftItemVo.getProductCount();
                        }
                        activeGift = activeGift + "、";
                    }
                }
                if (StringUtils.isNotBlank(activeGift)) {
                    activeGift = activeGift.substring(0, activeGift.length() - 1);//去除最后一个顿号
                }
                response.setActiveGift(activeGift);
            }

            //需求确认信息
            AladdinRequireVo requiredDto = aladdinOrderDetailResponseVo.getRequiredDto();
            if (requiredDto != null) {
                if (requiredDto.getCheckResult() != null) {
                    response.setConfirmationFlag(requiredDto.getCheckResult());
                }
                if (StringUtils.isNotBlank(requiredDto.getDateStr())) {
                    response.setConfirmationTime(requiredDto.getDateStr());
                }
                if (StringUtils.isNotBlank(requiredDto.getPlanBeginDateStr())) {
                    response.setScheduledDate(requiredDto.getPlanBeginDateStr());
                }
            }
        }

        return response;
    }

    /**
     * 设置软装清单信息
     *
     * @param aladdinSoftItemVos
     * @param width
     * @return
     */
    private List<FurnitureEntity> setSoftItemInfo(List<AladdinSoftItemVo> aladdinSoftItemVos, Integer width) {
        List<FurnitureEntity> furnitureList = new ArrayList<FurnitureEntity>();
        for (AladdinSoftItemVo aladdinSoftItemVo : aladdinSoftItemVos) {
            FurnitureEntity furnitureEntity = new FurnitureEntity();
            if (StringUtils.isNotBlank(aladdinSoftItemVo.getProductName())) {
                furnitureEntity.setFurnitureName(aladdinSoftItemVo.getProductName().replaceAll(ProductProgramPraise.FURNITURE_ORDER_DESC_1, "").replaceAll(ProductProgramPraise.FURNITURE_ORDER_DESC_2, ""));
            }
            if (StringUtils.isNotBlank(aladdinSoftItemVo.getSpecifications())) {
                furnitureEntity.setItemSize(aladdinSoftItemVo.getSpecifications());
            }
            if (aladdinSoftItemVo.getProductCount() != null) {
                furnitureEntity.setItemCount(aladdinSoftItemVo.getProductCount());
            }
            if (StringUtils.isNotBlank(aladdinSoftItemVo.getBrandAndSeries())) {
                furnitureEntity.setColor(aladdinSoftItemVo.getBrandAndSeries());
            }
            if (StringUtils.isNotBlank(aladdinSoftItemVo.getImageUrl())) {
                furnitureEntity.setImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(aladdinSoftItemVo.getImageUrl(), width, -1));
            }
            if (aladdinSoftItemVo.getFurnitureType() != null) {
                furnitureEntity.setFurnitureType(aladdinSoftItemVo.getFurnitureType());
            }
            if (aladdinSoftItemVo.getProductStatus() != null) {
                furnitureEntity.setProductStatus(aladdinSoftItemVo.getProductStatus());
            }
            if (StringUtils.isNotBlank(aladdinSoftItemVo.getProductStatusName())) {
                furnitureEntity.setProductStatusName(aladdinSoftItemVo.getProductStatusName());
            }
            furnitureList.add(furnitureEntity);
        }
        return furnitureList;
    }

    /**
     * 设置硬装清单信息
     *
     * @param aladdinHardItemVos
     * @param width
     * @return
     */
    private List<ItemInfo> setHardItemInfo(List<AladdinHardItemVo> aladdinHardItemVos, Integer width) {
        List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
        for (AladdinHardItemVo aladdinHardItemVo : aladdinHardItemVos) {
            ItemInfo itemInfo = new ItemInfo();
            if (aladdinHardItemVo.getItemCount() != null) {
                itemInfo.setQuantity(aladdinHardItemVo.getItemCount());
            }
            if (StringUtils.isNotBlank(aladdinHardItemVo.getCategoryName())) {
                itemInfo.setCategory(aladdinHardItemVo.getCategoryName());
            }
            if (StringUtils.isNotBlank(aladdinHardItemVo.getProductImge())) {
                itemInfo.setImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(aladdinHardItemVo.getProductImge(), width, -1));
            }
            if (StringUtils.isNotBlank(aladdinHardItemVo.getProductName())) {
                itemInfo.setName(aladdinHardItemVo.getProductName());
            }
            if (StringUtils.isNotBlank(aladdinHardItemVo.getCategoryName())) {
                itemInfo.setCategory(aladdinHardItemVo.getCategoryName());
            }
            if (StringUtils.isNotBlank(aladdinHardItemVo.getItemDesciption())) {
                itemInfo.setItemDesciption(aladdinHardItemVo.getItemDesciption());
            }
            if (StringUtils.isNotBlank(aladdinHardItemVo.getUnit())) {
                itemInfo.setUnit(aladdinHardItemVo.getUnit());
            }
            itemInfos.add(itemInfo);
        }
        return itemInfos;
    }

    @Override
    public DemandConfirmationResponseVo demandConfirmation(DemandConfirmationRequest request) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", request.getOrderId());
        Integer type = 2; //2-需求确认 3-意见反馈
        if (StringUtils.isNotBlank(request.getFeedBack())) {
            type = 3;//2-需求确认 3-意见反馈
            paramMap.put("contactMatters", request.getFeedBack());
        }
        paramMap.put("type", type);
        Map<String, Object> resultMap = concurrentOrderAndPreferentialInfo(paramMap);
        ResponseVo<Boolean> confirmRequriementResponse = (ResponseVo<Boolean>) resultMap.get(ConcurrentTaskEnum.CONFIRM_REQURIE_MENT.name());
        if (confirmRequriementResponse == null) {
            throw new BusinessException(MessageConstant.FAILED);
        }
        boolean confirmateResult = confirmRequriementResponse.isSuccess();
        if (!confirmateResult) {
            throw new BusinessException(RequireConfirmResponseEnum.getDescription(confirmRequriementResponse.getCode()));
        }
        DemandConfirmationResponseVo result = new DemandConfirmationResponseVo();
        result.setDemandResult(confirmateResult);
        if (confirmateResult && type == 2) {
            //查询保价优惠和订单总价
            OrderDetailDto orderDetailDto = (OrderDetailDto) resultMap.get(ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name());
            FamilyOrderPayResponse familyOrderPayResponse = queryPayBaseInfo(orderDetailDto);
            if (familyOrderPayResponse != null && familyOrderPayResponse.getPreferentialAmount() != null) {
                result.setPreferentialAmount(familyOrderPayResponse.getPreferentialAmount())
                        .setFinalOrderPrice(familyOrderPayResponse.getFinalOrderPrice().getValue());
            }
        }
        return result;
    }

    /**
     * 查询开工日期和保价优惠金额
     *
     * @param paramMap
     * @return
     */
    private Map<String, Object> concurrentOrderAndPreferentialInfo(Map<String, Object> paramMap) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.queryOrderSummaryInfo((Integer) paramMap.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return proxy.confirmRequriement(paramMap);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.CONFIRM_REQURIE_MENT.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    @Override
    public HttpBaseResponse cancelOrderProgram(Integer orderId) {
        HttpBaseResponse response = new HttpBaseResponse();
        HttpMessage message = new HttpMessage();
        response.setCode(HttpResponseCode.SUCCESS);
        message.setMsg("重选失败，再试一次吧～");
        Map<String, Object> result = new HashMap<String, Object>();
        boolean afreshFlag = false;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderId);
        paramMap.put("source", 0);
        Integer resultCode = proxy.cancelScheme(paramMap);
        if (resultCode != null) {
            if (resultCode.equals(1)) {
                afreshFlag = true;
                message.setMsg(MessageConstant.SUCCESS);
            } else if (resultCode.equals(1000)) {
                message.setMsg("重选失败，订单不存在");
            } else if (resultCode.equals(1001)) {
                message.setMsg("重选失败，此阶段不支持重选方案");
            } else if (resultCode.equals(1002)) {
                message.setMsg("重选失败，订单已进入交付阶段");
            }
        }
        result.put("afreshFlag", afreshFlag);
        response.setObj(result);
        response.setExt(message);

        return response;
    }

    @Override
    public List<ContractInfoResponse> queryContractListByOrderId(Integer orderId) {
        List<ContractInfoResponse> contractInfoList = new ArrayList<ContractInfoResponse>();

        List<QueryContractListResponseVo> contractListResponseVos = proxy.queryContractList(orderId);
        if (CollectionUtils.isNotEmpty(contractListResponseVos)) {
            for (QueryContractListResponseVo queryContractListResponseVo : contractListResponseVos) {
                ContractInfoResponse contractInfoResponse = new ContractInfoResponse();
                if (StringUtils.isNotBlank(queryContractListResponseVo.getCreateTime())) {
                    contractInfoResponse.setCreateTime(queryContractListResponseVo.getCreateTime());
                }
                if (StringUtils.isNotBlank(queryContractListResponseVo.getFilePath())) {
                    contractInfoResponse.setFilePath(queryContractListResponseVo.getFilePath());
                }
                if (StringUtils.isNotBlank(queryContractListResponseVo.getTypeName())) {
                    contractInfoResponse.setTypeName(queryContractListResponseVo.getTypeName());
                }
                contractInfoResponse.setOrderId(queryContractListResponseVo.getOrderNum());
                if (queryContractListResponseVo.getType() != null) {
                    contractInfoResponse.setType(queryContractListResponseVo.getType());
                }
                contractInfoList.add(contractInfoResponse);
            }
        }

        return contractInfoList;
    }

    @Override
    public List<ContractInfoResponse> queryContractTemplateList() {
        List<ContractInfoResponse> contractList = new ArrayList<ContractInfoResponse>();
        DicListDto dicListResponseVo = dicProxy.getDicListByKey("CONTRACT_TEMPLATE");
        if (dicListResponseVo != null && CollectionUtils.isNotEmpty(dicListResponseVo.getDicList())) {
            List<DicDto> dicList = dicListResponseVo.getDicList();
            for (DicDto dicVo : dicList) {
                ElectronicContractTypeEnum contractTypeEnum = ElectronicContractTypeEnum.getElectronicContractTypeEnumByKey(dicVo.getKeyDesc());
                ContractInfoResponse contractInfoResponse = new ContractInfoResponse();
                contractInfoResponse.setType(contractTypeEnum.getCode());
                contractInfoResponse.setTypeName(contractTypeEnum.getDescription());
                contractInfoResponse.setFilePath(dicVo.getValueDesc());
                contractList.add(contractInfoResponse);
            }
        }
        return contractList;
    }

    /**
     * 全品家清单
     *
     * @param orderId
     * @param width
     * @return
     */
    @Override
    public SolutionSelected queryOrderSolutionSelectedList(Integer orderId, Integer width) {
        RoomUseEnum.getCode("客厅");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderId);

        Map<String, Object> resultMap = concurrentOrderSolutionSelectedInfo(paramMap);


        AladdinOrderDetailResponseVo responseVo = (AladdinOrderDetailResponseVo) resultMap.get(ConcurrentTaskEnum.QUERY_ORDER_LIST_BY_PARAM.name());

        if (responseVo == null) {
            LOG.info("aladdin-order.masterOrder-app.masterOrder.queryOrderListByParam response null");
            return null;
        }
        SolutionSelected solutionSelected = new SolutionSelected();

        SolutionInfo solutionInfo = (SolutionInfo) resultMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name());
        AppOrderBaseInfoResponseVo orderInfo = (AppOrderBaseInfoResponseVo) resultMap.get(ConcurrentTaskEnum.QUERY_MASTER_ORDER_BASE_INFO.name());
        if (orderInfo != null) {
            solutionSelected.setLockPriceFlag(orderInfo.getLockPriceFlag() == null ? -1 : orderInfo.getLockPriceFlag());
        }
        solutionSelected.setSolutionInfo(solutionInfo);
        List<SpaceDesign> spaceDesignList = new ArrayList<>();

        // beseList: 新&非代客下单
        if (CollectionUtils.isNotEmpty(responseVo.getBaseList())) {
            List<SpaceDesign> spaceDesignListTmp = handlerBaseList(responseVo.getBaseList(), width);
            spaceDesignList = addNewSpaceDesign(spaceDesignList, spaceDesignListTmp);
        }
        // softBaseList: 老订单、代客下单
        if (CollectionUtils.isNotEmpty(responseVo.getSoftBaseList())) {
            List<SpaceDesign> spaceDesignListTmp = handlerSoftBaseList(responseVo);
            spaceDesignList = addNewSpaceDesign(spaceDesignList, spaceDesignListTmp);
        }

        // softAddBagList: 代客下单
//        if (responseVo.getOrderSource() == ProductProgramPraise.ORDER_SOURCE_VALET){
        if (CollectionUtils.isNotEmpty(responseVo.getSoftAddBagList())) {
            List<SpaceDesign> spaceDesignListTmp = handlerAddBagList(responseVo.getSoftAddBagList());
            addNewSpaceDesign(spaceDesignList, spaceDesignListTmp);
            spaceDesignList = addNewSpaceDesign(spaceDesignList, spaceDesignListTmp);
        }

        // softPresentList:代客下单、老订单、新&非代客下单
        if (CollectionUtils.isNotEmpty(responseVo.getSoftPresentList())) {
            List<SpaceDesign> spaceDesignListTmp = handlerSoftPresentList(responseVo.getSoftPresentList());
            spaceDesignList = addNewSpaceDesign(spaceDesignList, spaceDesignListTmp);
        }

        // softFitemList:代客下单、老订单、新&非代客下单
        if (CollectionUtils.isNotEmpty(responseVo.getSoftFitemList())) {
            List<SpaceDesign> spaceDesignListTmp = handlerSoftFitemList(responseVo.getSoftFitemList());
            spaceDesignList = addNewSpaceDesign(spaceDesignList, spaceDesignListTmp);
        }
        if (CollectionUtils.isNotEmpty(spaceDesignList)) {
            for (SpaceDesign spaceDesign : spaceDesignList) {
                if (RoomUseEnum.ROOM_WHOLE.getCode().equals(spaceDesign.getSpaceUsageId())) {
                    spaceDesignList.remove(spaceDesign);
                    spaceDesignList.add(0, spaceDesign);
                    break;
                }
            }
        }
        solutionSelected.setBetaChangeFlag(responseVo.getBetaChangeFlag());

        replaceBomGroupImage(spaceDesignList);
        solutionSelected.setSpaceDesignSelected(spaceDesignList);
        solutionSelected.setTotalPrice(responseVo.getOriginalOrderAmount());
        solutionSelected.setHomeAdviserMobile(responseVo.getAdviseMoblie());
        solutionSelected.setOrderSource(responseVo.getOrderSource());
        solutionSelected.setNewOrderState(responseVo.getNewOrderState());
        if (CollectionUtils.isNotEmpty(responseVo.getServiceItemList())) {
            Map<Integer, List<ServiceItemDto>> collect = responseVo.getServiceItemList().stream().collect(Collectors.groupingBy(ServiceItemDto::getSkuId));
            responseVo.getServiceItemList().clear();
            collect.forEach((skuId, serviceItemDtoList) -> {
                Map<Integer, List<ServiceItemDto>> collectIntegerListMap = serviceItemDtoList.stream().collect(Collectors.groupingBy(ServiceItemDto::getSkuPriceRuleId));
                collectIntegerListMap.forEach((integer1, serviceItemDtos) -> {
                    ServiceItemDto serviceItemDto = serviceItemDtoList.remove(0);
                    if (CollectionUtils.isNotEmpty(serviceItemDtoList)) {
                        for (ServiceItemDto itemDto : serviceItemDtoList) {
                            serviceItemDto.setTotalMarketPrice(serviceItemDto.getTotalMarketPrice().add(itemDto.getTotalMarketPrice()));
                            serviceItemDto.setTotalPrice(serviceItemDto.getTotalPrice().add(itemDto.getTotalPrice()));
                            serviceItemDto.setTotalPurchasePrice(serviceItemDto.getTotalPurchasePrice().add(itemDto.getTotalPurchasePrice()));
                            serviceItemDto.setWallArea(serviceItemDto.getWallArea().add(itemDto.getWallArea()));
                        }
                    }
                    if (serviceItemDto.getSkuPriceRuleType() == null || !serviceItemDto.getSkuPriceRuleType().equals(2)) {
                        serviceItemDto.setPriceDesc("");
                    }
                    responseVo.getServiceItemList().add(serviceItemDto);
                });
            });
            responseVo.getServiceItemList().forEach(serviceItemDto -> serviceItemDto.setSkuImage(AliImageUtil.imageCompress(serviceItemDto.getSkuImage(), 2, 750, ImageConstant.SIZE_SMALL)));
            solutionSelected.setServiceItemList(productProgramService.mergeServiceItemList(responseVo.getServiceItemList()));
        }
        if (responseVo.getRequiredDto() != null) {
            solutionSelected.setConfirmationTime(responseVo.getRequiredDto().getDateStr());
        }
        DeliverySimpleInfoDto deliveryInfo = (DeliverySimpleInfoDto) resultMap.get(ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name());
        CheckIfCanDeliveryConfirmVo checkIfCanDeliveryConfirmVo = (CheckIfCanDeliveryConfirmVo) resultMap.get(ConcurrentTaskEnum.CHECK_IF_CAN_DELIVER_CONFIRM.name());
        solutionSelected.setConfirmationFlag(checkIfCanDeliveryConfirmVo.getCheckResult());
        if (deliveryInfo != null) {
            solutionSelected.setScheduledDate(deliveryInfo.getPlanBeginDate());
        }
        if (solutionSelected.getSolutionInfo() != null && solutionSelected.getSolutionInfo().getSolutionId() != null) {
            ServiceItemResponse serviceItemResponse = programProxy.querySolutionService(solutionSelected.getSolutionInfo().getSolutionId().longValue());
            if (serviceItemResponse != null) {
                solutionSelected.getSolutionInfo().setApartmentUrl(AliImageUtil.imageCompress(serviceItemResponse.getApartmentUrl(), 1, 750, ImageConstant.SIZE_MIDDLE));
                solutionSelected.getSolutionInfo().setReformApartmentUrl(AliImageUtil.imageCompress(serviceItemResponse.getReformApartmentUrl(), 1, 750, ImageConstant.SIZE_MIDDLE));
                solutionSelected.getSolutionInfo().setSolutionGraphicDesignUrl(AliImageUtil.imageCompress(serviceItemResponse.getSolutionGraphicDesignUrl(), 1, 750, ImageConstant.SIZE_MIDDLE));
                if (CollectionUtils.isNotEmpty(solutionSelected.getServiceItemList()) && CollectionUtils.isNotEmpty(serviceItemResponse.getServiceItemList())) {
                    for (ServiceItemDto serviceItemDto : solutionSelected.getServiceItemList()) {
                        for (ServiceItemDto itemDto : serviceItemResponse.getServiceItemList()) {
                            if (serviceItemDto.getSkuId().equals(itemDto.getSkuId()) && serviceItemDto.getSkuPriceRuleType().equals(2)) {
                                serviceItemDto.setPriceDesc(itemDto.getPriceDesc());
                            }
                        }
                    }
                }
            }
        }
        return solutionSelected;
    }


    /**
     * 全品家清单多线程
     *
     * @param paramMap
     * @return
     */
    private Map<String, Object> concurrentOrderSolutionSelectedInfo(Map<String, Object> paramMap) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(5);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return productProgramOrderProxy.querySolutionInfo((Integer) paramMap.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return proxy.queryOrderListByParam(paramMap);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_LIST_BY_PARAM.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return productProgramOrderProxy.queryAppOrderBaseInfo((Integer) paramMap.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_MASTER_ORDER_BASE_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return dmsProxy.getSimpleDeliveryInfo((Integer) paramMap.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.checkIfCanDeliveryConfirm((Integer) paramMap.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.CHECK_IF_CAN_DELIVER_CONFIRM.name();
            }
        });
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    private List<SpaceDesign> addNewSpaceDesign(List<SpaceDesign> spaceDesignList, List<SpaceDesign> spaceDesignListTmp) {
        if (!CollectionUtils.isNotEmpty(spaceDesignListTmp)) {
            return spaceDesignList;
        }
        if (!CollectionUtils.isNotEmpty(spaceDesignList)) {
            spaceDesignList = spaceDesignListTmp;
        } else {
            for (SpaceDesign spaceDesignTmp : spaceDesignListTmp) {
                if (spaceDesignTmp == null) {
                    continue;
                }
                boolean hasSet = false;
                for (SpaceDesign spaceDesign : spaceDesignList) {
                    if (spaceDesign == null) {
                        spaceDesign = spaceDesignTmp;
                    }
                    if (spaceDesignTmp.getSpaceUsageId().equals(spaceDesign.getSpaceUsageId())) {
                        hasSet = true;
                        if (CollectionUtils.isNotEmpty(spaceDesign.getOptionalSoftResponseList())) {
                            if (CollectionUtils.isNotEmpty(spaceDesignTmp.getOptionalSoftResponseList())) {
                                spaceDesign.getOptionalSoftResponseList().addAll(spaceDesignTmp.getOptionalSoftResponseList());
                            }
                        } else {
                            spaceDesign.setOptionalSoftResponseList(spaceDesignTmp.getOptionalSoftResponseList());
                        }

                        if (CollectionUtils.isNotEmpty(spaceDesign.getBomGroupList())) {
                            if (CollectionUtils.isNotEmpty(spaceDesignTmp.getBomGroupList())) {
                                spaceDesign.getBomGroupList().addAll(spaceDesignTmp.getBomGroupList());
                            }
                        } else {
                            spaceDesign.setBomGroupList(spaceDesignTmp.getBomGroupList());
                        }

                        if (CollectionUtils.isNotEmpty(spaceDesign.getSoftAddBagList())) {
                            if (CollectionUtils.isNotEmpty(spaceDesignTmp.getSoftAddBagList())) {
                                spaceDesign.getSoftAddBagList().addAll(spaceDesignTmp.getSoftAddBagList());
                            }
                        } else {
                            spaceDesign.setSoftAddBagList(spaceDesignTmp.getSoftAddBagList());
                        }

                        if (CollectionUtils.isNotEmpty(spaceDesign.getHardItemList())) {
                            if (CollectionUtils.isNotEmpty(spaceDesignTmp.getHardItemList())) {
                                spaceDesign.getHardItemList().addAll(spaceDesignTmp.getHardItemList());
                            }
                        } else {
                            spaceDesign.setHardItemList(spaceDesignTmp.getHardItemList());
                        }
                    }
                }
                if (!hasSet) {
                    spaceDesignList.add(spaceDesignTmp);
                }
            }
        }
        return spaceDesignList;
    }

    public void replaceBomGroupImage(List<SpaceDesign> spaceDesignList){
        if(CollectionUtils.isNotEmpty(spaceDesignList)){
            for(SpaceDesign spaceDesign : spaceDesignList){
                if(CollectionUtils.isNotEmpty(spaceDesign.getOptionalSoftResponseList())){
                    for(OptionalSoftResponse optionalSoft : spaceDesign.getOptionalSoftResponseList()){
                        if(CollectionUtils.isNotEmpty(optionalSoft.getGuiBomGroupQueryList()) &&
                                optionalSoft.getGuiBomGroupQueryList().size()>1){
                            optionalSoft.setItemImage(spaceDesign.getHeadImage());
                            optionalSoft.getCabinetBomGroup().setGroupImage(spaceDesign.getHeadImage());
                        }
                    }
                }
            }
        }
    }

    private List<SpaceDesign> handlerSoftFitemList(List<AladdinSoftItemVo> softFitemList) {
        List<SpaceDesign> spaceDesignList = new ArrayList<>();
        List<SpaceDesign> outSideList = new ArrayList<>();
        // softBaseList
        if (CollectionUtils.isNotEmpty(softFitemList)) {
            //空间基础信息
            SpaceDesign spaceDesign = new SpaceDesign();
            spaceDesign.setSpaceUsageName("全屋");
            spaceDesign.setSpaceUsageId(RoomUseEnum.getCode("全屋"));
            //软装vo转response
            List<OptionalSoftResponse> optionalSoftResponseList = new ArrayList<>();
            for (AladdinSoftItemVo softProductDetailDto : softFitemList) {
                optionalSoftResponseList.add(handlerSoftResponse(softProductDetailDto));
            }
            spaceDesign.setOptionalSoftResponseList(optionalSoftResponseList);
            spaceDesignList.add(spaceDesign);
        }
        spaceDesignList.sort(Comparator.comparingInt(spaceDesign -> RoomConstants.ROOM_USE_ORDER_LIST.indexOf(spaceDesign.getSpaceUsageId())));
        if (CollectionUtils.isNotEmpty(outSideList)) {
            spaceDesignList.addAll(outSideList);
        }
        return spaceDesignList;
    }

    private List<SpaceDesign> handlerSoftPresentList(List<AladdinSoftItemVo> softPresentList) {
        List<SpaceDesign> spaceDesignList = new ArrayList<>();
        List<SpaceDesign> outSideList = new ArrayList<>();
        // softBaseList
        if (CollectionUtils.isNotEmpty(softPresentList)) {
            //空间基础信息
            SpaceDesign spaceDesign = new SpaceDesign();
            spaceDesign.setSpaceUsageName("全屋");
            spaceDesign.setSpaceUsageId(RoomUseEnum.getCode("全屋"));
            //软装vo转response
            List<OptionalSoftResponse> optionalSoftResponseList = new ArrayList<>();
            for (AladdinSoftItemVo softProductDetailDto : softPresentList) {
                optionalSoftResponseList.add(handlerSoftResponse(softProductDetailDto));
            }
            spaceDesign.setOptionalSoftResponseList(optionalSoftResponseList);
            spaceDesignList.add(spaceDesign);
        }
        spaceDesignList.sort(Comparator.comparingInt(spaceDesign -> RoomConstants.ROOM_USE_ORDER_LIST.indexOf(spaceDesign.getSpaceUsageId())));
        if (CollectionUtils.isNotEmpty(outSideList)) {
            spaceDesignList.addAll(outSideList);
        }
        return spaceDesignList;
    }

    private List<SpaceDesign> handlerAddBagList(List<AladdinSoftItemVo> softAddBagList) {
        List<SpaceDesign> spaceDesignList = new ArrayList<>();
        List<SpaceDesign> outSideList = new ArrayList<>();
        // softBaseList
        if (CollectionUtils.isNotEmpty(softAddBagList)) {
            //空间基础信息
            SpaceDesign spaceDesign = new SpaceDesign();
            spaceDesign.setSpaceUsageName("全屋");
            spaceDesign.setSpaceUsageId(RoomUseEnum.getCode("全屋"));
            //软装vo转response
            List<OptionalSoftResponse> optionalSoftResponseList = new ArrayList<>();
            for (AladdinSoftItemVo softProductDetailDto : softAddBagList) {
                optionalSoftResponseList.add(handlerSoftResponse(softProductDetailDto));
            }
            spaceDesign.setOptionalSoftResponseList(optionalSoftResponseList);
            spaceDesignList.add(spaceDesign);
        }
        spaceDesignList.sort(Comparator.comparingInt(spaceDesign -> RoomConstants.ROOM_USE_ORDER_LIST.indexOf(spaceDesign.getSpaceUsageId())));
        if (CollectionUtils.isNotEmpty(outSideList)) {
            spaceDesignList.addAll(outSideList);
        }
        return spaceDesignList;
    }

    private List<SpaceDesign> handlerBaseList(List<RoomBaseDetailDto> baseList, Integer width) {
        List<SpaceDesign> spaceDesignList = new ArrayList<>();
        List<SpaceDesign> outSideList = new ArrayList<>();
        for (RoomBaseDetailDto roomDto : baseList) {
            //空间基础信息
            SpaceDesign spaceDesign = new SpaceDesign();
            spaceDesign.setSpaceDesignId(roomDto.getRoomId());
            spaceDesign.setSpaceUsageName(roomDto.getRoomName());
            spaceDesign.setExtData(new OrderRoomExtDataDto(roomDto.getTaskType(), roomDto.getReferenceOnlyFlag()));
            spaceDesign.setHeadImage(AliImageUtil.imageCompress(roomDto.getPictureUrl(), 2, width, ImageConstant.SIZE_MIDDLE));
            if (CollectionUtils.isNotEmpty(roomDto.getRoomPictureDtoList())) {
                roomDto.getRoomPictureDtoList().forEach(roomPictureDto -> {
                    roomPictureDto.setPictureUrlOrigin(roomPictureDto.getPictureUrl());
                    if (roomPictureDto.getPictureUrl() != null) {
                        roomPictureDto.setPictureUrl(AliImageUtil.imageCompress(roomPictureDto.getPictureUrl(), 2, width, ImageConstant.SIZE_MIDDLE));
                    }
                });
            }
            List<RoomPictureDto> taskList = Lists.newArrayList();
            List<RoomPictureDto> notTaskList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(roomDto.getRoomPictureDtoList())) {
                for (RoomPictureDto roomPictureDto : roomDto.getRoomPictureDtoList()) {
                    if (roomPictureDto.getTaskId() != null && roomPictureDto.getTaskId() != 0) {
                        taskList.add(roomPictureDto);
                    } else {
                        notTaskList.add(roomPictureDto);
                    }
                }
                if (CollectionUtils.isNotEmpty(taskList)) {
                    //1：渲染中，2。已完成
                    spaceDesign.setDrawProgress(1);
                    for (RoomPictureDto roomPictureDto : taskList) {
                        if (StringUtils.isNotBlank(roomPictureDto.getPictureUrl())) {
                            spaceDesign.setDrawProgress(2);
                            break;
                        }
                    }
                }
            }
            spaceDesign.setRoomPictureDtoList(Lists.newArrayList());
            if (spaceDesign.getDrawProgress().equals(2)) {
                spaceDesign.getRoomPictureDtoList().addAll(taskList);
            } else {
                List<RoomPictureDto> collect = notTaskList.stream().filter(roomPictureDto -> roomPictureDto.getPicFlag() != null && roomPictureDto.getPicFlag().equals(1)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    spaceDesign.getRoomPictureDtoList().addAll(collect);
                } else {
                    List<RoomPictureDto> collect1 = notTaskList.stream().filter(roomPictureDto -> roomPictureDto.getType() != null && roomPictureDto.getType().equals(1)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(collect1)) {
                        spaceDesign.getRoomPictureDtoList().addAll(collect1);
                    } else {
                        spaceDesign.getRoomPictureDtoList().addAll(notTaskList);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(spaceDesign.getRoomPictureDtoList())) {
                //过滤没有出结果的渲染图
                spaceDesign.setRoomPictureDtoList(spaceDesign.getRoomPictureDtoList().stream().filter(roomPictureDto -> roomPictureDto.getPictureUrl() != null).collect(Collectors.toList()));
            }
            //硬装列表合并
            List<HardProductDetailDto> hardDtoList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(roomDto.getHardItemList())) {
                hardDtoList.addAll(roomDto.getHardItemList());
            }
            if (CollectionUtils.isNotEmpty(roomDto.getHardAddBagList())) {
                hardDtoList.addAll(roomDto.getHardAddBagList());
            }
            //软装列表合并
            List<SoftProductDetailDto> softDtoList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(roomDto.getSoftItemList())) {
                softDtoList.addAll(roomDto.getSoftItemList());
            }
            if (CollectionUtils.isNotEmpty(roomDto.getSoftAddBagList())) {
                softDtoList.addAll(roomDto.getSoftAddBagList());
            }

            //硬装vo转response
            List<HardItem> hardItemList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(hardDtoList)) {
                Map<Integer, List<HardProductDetailDto>> hardGuiBomByGroupSecondCategoryId = hardDtoList.stream().filter(hardProductDetailDto -> hardProductDetailDto.getGroupType() != null && hardProductDetailDto.getGroupType().equals(9)).collect(Collectors.groupingBy(HardProductDetailDto::getGroupSecondCategoryId));
                hardDtoList.removeIf(hardProductDetailDto -> hardProductDetailDto.getGroupType() != null && hardProductDetailDto.getGroupType().equals(9));
                for (HardProductDetailDto hardProductDetailDto : hardDtoList) {
                    HardItem hardItem = new HardItem();
                    hardItem.setHardItemId(hardProductDetailDto.getRoomClassId());
                    hardItem.setHardItemName(hardProductDetailDto.getRoomClassName());
                    hardItem.setBomFlag(hardProductDetailDto.getBomFlag());
                    //全屋且是硬装选配包
                    if (roomDto.getRoomUseId() != null && RoomUseEnum.ROOM_WHOLE.getCode().equals(roomDto.getRoomUseId())) {
                        RoomHardPackageVo hardPackage = new RoomHardPackageVo();
                        hardPackage.setPackageUrl(QiniuImageUtils.compressImageAndSamePicTwo(hardProductDetailDto.getGroupImage(), width, -1));
                        hardPackage.setPackageSmallUrl(QiniuImageUtils.compressImageAndSamePic(hardProductDetailDto.getGroupImage(), 100, 100));
                        hardPackage.setPackageName(hardProductDetailDto.getGroupName());
                        hardPackage.setPackageDesc(hardProductDetailDto.getProductName());
                        hardItem.setHardPackageSelected(hardPackage);
                    }
                    HardItemSelection selection = new HardItemSelection();
                    selection.setSmallImage(QiniuImageUtils.compressImageAndSamePic(hardProductDetailDto.getImageUrl(), 100, 100));
                    selection.setHeadImage(QiniuImageUtils.compressImageAndSamePicTwo(hardProductDetailDto.getImageUrl(), width, -1));
                    selection.setHardSelectionId(hardProductDetailDto.getSkuId());
                    selection.setFurnitureType(hardProductDetailDto.getFurnitureType());
                    selection.setHardSelectionName(hardProductDetailDto.getProductName());
                    HardProcess hardProcess = new HardProcess();
                    hardProcess.setProcessId(hardProductDetailDto.getCraftId());
                    hardProcess.setProcessName(hardProductDetailDto.getCraftName());

                    if (CollectionUtils.isNotEmpty(hardProductDetailDto.getChildHardProductList())) {//全屋二级结构
                        List<HardItemSelection> hardItemSelection = new ArrayList<>();
                        hardProductDetailDto.getChildHardProductList().forEach(e -> {
                            HardItemSelection hardItemSelectionBean = new HardItemSelection();
                            HardProcess hardProcessSon = new HardProcess();
                            hardProcessSon.setProcessId(e.getCraftId());
                            hardProcessSon.setProcessName(e.getCraftName());
                            hardItemSelectionBean.setHardSelectionId(e.getSkuId());
                            hardItemSelectionBean.setHardSelectionName(e.getProductName());
                            hardItemSelectionBean.setSmallImage(QiniuImageUtils.compressImageAndSamePic(e.getImageUrl(), 100, 100));
                            hardItemSelectionBean.setHeadImage(QiniuImageUtils.compressImageAndSamePicTwo(e.getImageUrl(), width, -1));
                            hardItemSelectionBean.setProcessSelected(hardProcessSon);
                            hardItemSelection.add(hardItemSelectionBean);
                        });
                        hardProcess.setHardItemSelection(hardItemSelection);

                    }
                    selection.setProcessSelected(hardProcess);
                    hardItem.setHardItemSelected(selection);

                    hardItemList.add(hardItem);
                }
                //组装定制柜数据
                if (MapUtils.isNotEmpty(hardGuiBomByGroupSecondCategoryId)) {
                    hardGuiBomByGroupSecondCategoryId.forEach((secondCategoryId, hardProductDetailDtoList) -> {
                        HardProductDetailDto hardProductDetailDto = hardProductDetailDtoList.get(0);
                        HardItem hardItem = new HardItem();
                        hardItem.setBomFlag(102);
                        hardItem.setHardItemId(hardProductDetailDto.getGroupSecondCategoryId());
                        hardItem.setHardItemName("定制家具");
                        hardItem.setHardItemDesc(hardProductDetailDto.getGroupSecondCategoryName());
                        hardItem.setHardItemImage(AliImageUtil.imageCompress(hardProductDetailDto.getImageUrl(), 1, 750, ImageConstant.SIZE_SMALL));
                        CabinetBomDto cabinetBomDto = new CabinetBomDto();
                        cabinetBomDto.setFurnitureType(4);
                        cabinetBomDto.setGroupImage(AliImageUtil.imageCompress(hardProductDetailDto.getImageUrl(), 1, 750, ImageConstant.SIZE_SMALL));
                        cabinetBomDto.setGroupType(9);
                        cabinetBomDto.setSecondCategoryId(hardProductDetailDto.getGroupSecondCategoryId());
                        cabinetBomDto.setSecondCategoryName(hardProductDetailDto.getGroupSecondCategoryName());
                        List<ReplaceBomDto> replaceBomDtoList = hardProductDetailDtoList.stream().map(hardItemClass -> {
                            ReplaceBomDto replaceBomDto = new ReplaceBomDto();
                            HardBomGroup hardBomGroup = new HardBomGroup();
                            hardBomGroup.setCabinetTypeName(hardItemClass.getCabinetTypeName());
                            hardBomGroup.setGroupImage(AliImageUtil.imageCompress(hardItemClass.getImageUrl(), 1, 750, ImageConstant.SIZE_SMALL));
                            hardBomGroup.setGroupType(hardItemClass.getGroupType());
                            hardBomGroup.setGroupId(hardItemClass.getSkuId());
                            hardBomGroup.setGroupImage(AliImageUtil.imageCompress(hardBomGroup.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                            hardBomGroup.setCabinetType(hardItemClass.getCabinetType());
                            hardBomGroup.setGroupName(hardItemClass.getGroupName());
                            hardBomGroup.setSecondCategoryId(hardItemClass.getGroupSecondCategoryId());
                            hardBomGroup.setSecondCategoryName(hardItemClass.getGroupSecondCategoryName());
                            hardBomGroup.setFurnitureType(hardItemClass.getFurnitureType());
                            hardBomGroup.setPositionIndex(hardItemClass.getPositionIndex());
                            hardBomGroup.setProductName(hardItemClass.getProductName());
                            hardBomGroup.setCategoryName(hardItemClass.getGroupSecondCategoryName());
                            replaceBomDto.setBomGroupDefault(hardBomGroup);
                            replaceBomDto.setBomGroupSelect(hardBomGroup);
                            return replaceBomDto;
                        }).collect(Collectors.toList());
                        cabinetBomDto.setReplaceBomList(replaceBomDtoList);
                        hardItem.setCabinetBomGroup(cabinetBomDto);
                        List<HardBomGroup> collect = hardProductDetailDtoList.stream().map(hardProductDetailDtoFor -> {
                            HardBomGroup hardBomGroup = new HardBomGroup();
                            hardBomGroup.setGroupType(hardProductDetailDtoFor.getGroupType());
                            hardBomGroup.setGroupImage(hardProductDetailDtoFor.getGroupImage());
                            hardBomGroup.setCabinetType(hardProductDetailDtoFor.getCabinetType());
                            hardBomGroup.setCabinetTypeName(hardProductDetailDtoFor.getCabinetTypeName());
                            hardBomGroup.setCategoryName(hardProductDetailDtoFor.getCabinetTypeName());
                            hardBomGroup.setSecondCategoryId(hardProductDetailDtoFor.getGroupSecondCategoryId());
                            hardBomGroup.setSecondCategoryName(hardProductDetailDtoFor.getGroupSecondCategoryName());
                            hardBomGroup.setFurnitureType(hardProductDetailDtoFor.getFurnitureType());
                            hardBomGroup.setGroupId(hardProductDetailDtoFor.getSkuId());
                            return hardBomGroup;
                        }).collect(Collectors.toList());
                        hardItem.setGuiBomGroupList(collect);
                        hardItem.setGuiBomGroupQueryList(hardItem.getGuiBomGroupList().stream()
                                .map(hardBomGroup -> new QueryCabinetPropertyListRequest.GroupQueryRequest()
                                        .setCabinetType(hardBomGroup.getCabinetType())
                                        .setCabinetTypeName(hardBomGroup.getCabinetTypeName())
                                        .setGroupId(hardBomGroup.getGroupId())).collect(Collectors.toList()));
                        hardItemList.add(hardItem);
                    });
                }
            }

            //软装vo转response
            List<OptionalSoftResponse> optionalSoftResponseList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(softDtoList)) {
                Map<Integer, List<SoftProductDetailDto>> guiBomBySecondCategoryId = softDtoList.stream().filter(softProductDetailDto -> softProductDetailDto.getBomFlag().equals(1) && softProductDetailDto.getGroupType() != null && softProductDetailDto.getGroupType().equals(10)).collect(Collectors.groupingBy(SoftProductDetailDto::getGroupSecondCategoryId));
                softDtoList.removeIf(softProductDetailDto -> softProductDetailDto.getBomFlag().equals(1) && softProductDetailDto.getGroupType() != null && softProductDetailDto.getGroupType().equals(10));

                for (SoftProductDetailDto softProductDetailDto : softDtoList) {
                    OptionalSoftResponse softResponse = new OptionalSoftResponse();
                    softResponse.setCategory(softProductDetailDto.getCategoryName());
                    softResponse.setBomFlag(softProductDetailDto.getBomFlag());
                    FurnitureEntity furnitureEntity = new FurnitureEntity();
                    furnitureEntity.setSkuId(softProductDetailDto.getSkuId());
                    furnitureEntity.setFurnitureName(softProductDetailDto.getProductName());
                    furnitureEntity.setBrand(softProductDetailDto.getBrandAndSeries());
                    furnitureEntity.setItemCount(softProductDetailDto.getProductCount());
                    furnitureEntity.setImgUrl(softProductDetailDto.getImageUrl());
                    furnitureEntity.setSmallImage(QiniuImageUtils.compressImageAndSamePic(softProductDetailDto.getImageUrl(), 100, 100));
                    furnitureEntity.setProductStatus(softProductDetailDto.getProductStatus());
                    furnitureEntity.setProductStatusName(softProductDetailDto.getProductStatusName());
                    furnitureEntity.setMaterial(softProductDetailDto.getMaterial());
                    furnitureEntity.setFurnitureType(softProductDetailDto.getFurnitureType());
                    furnitureEntity.setColor(softProductDetailDto.getProductColor());
                    furnitureEntity.setBomFlag(softProductDetailDto.getBomFlag());
                    furnitureEntity.setFurnitureType(softProductDetailDto.getFurnitureType());
                    if (furnitureEntity.getFurnitureType() != null && furnitureEntity.getFurnitureType() == 3) {//新定制品对app端 转为'定制家具'
                        furnitureEntity.setRootCategoryId(Constants.CUSTOMIZED_ROOT_CATEGORY_ID);
                        furnitureEntity.setRootCategoryName(Constants.CUSTOMIZED_ROOT_CATEGORY_NAME);
                    }
                    furnitureEntity.setLastCategoryName(softProductDetailDto.getLastCategoryName());
                    furnitureEntity.setLastCategoryId(softProductDetailDto.getLastCategoryId());
                    softResponse.setFurnitureSelected(furnitureEntity);
                    if (furnitureEntity.getFurnitureType() != null && furnitureEntity.getFurnitureType() == 3) {//新定制品对app端 转为'定制家具'
                        softResponse.setRootCategoryId(Constants.CUSTOMIZED_ROOT_CATEGORY_ID);
                        softResponse.setRootCategoryName(Constants.CUSTOMIZED_ROOT_CATEGORY_NAME);
                    }
                    softResponse.setLastCategoryId(softProductDetailDto.getLastCategoryId());
                    softResponse.setLastCategoryName(softProductDetailDto.getLastCategoryName());

                    optionalSoftResponseList.add(softResponse);
                }
                if (MapUtils.isNotEmpty(guiBomBySecondCategoryId)) {

                    guiBomBySecondCategoryId.forEach((secondCategoryId, softProductDetailDtoList) -> {
                        SoftProductDetailDto softProductDetailDto = softProductDetailDtoList.get(0);
                        OptionalSoftResponse softResponse = new OptionalSoftResponse();
                        softResponse.setCategory("定制家具");
                        softResponse.setItemImage(AliImageUtil.imageCompress(softProductDetailDto.getImageUrl(), 1, 100, ImageConstant.SIZE_SMALL));
                        softResponse.setItemName(softProductDetailDto.getGroupSecondCategoryName());
                        softResponse.setBomFlag(102);
                        CabinetBomDto cabinetBomDto = new CabinetBomDto();
                        cabinetBomDto.setFurnitureType(4);
                        cabinetBomDto.setGroupImage(AliImageUtil.imageCompress(softProductDetailDto.getImageUrl(), 1, 750, ImageConstant.SIZE_SMALL));
                        cabinetBomDto.setGroupType(9);
                        cabinetBomDto.setSecondCategoryId(softProductDetailDto.getGroupSecondCategoryId());
                        cabinetBomDto.setSecondCategoryName(softProductDetailDto.getGroupSecondCategoryName());
                        List<ReplaceBomDto> replaceBomDtoList = softProductDetailDtoList.stream().map(softProductDetailDto1 -> {
                            ReplaceBomDto replaceBomDto = new ReplaceBomDto();
                            HardBomGroup hardBomGroup = new HardBomGroup();
                            hardBomGroup.setCabinetTypeName(softProductDetailDto1.getCabinetTypeName());
                            hardBomGroup.setGroupImage(AliImageUtil.imageCompress(softProductDetailDto1.getImageUrl(), 1, 750, ImageConstant.SIZE_SMALL));
                            hardBomGroup.setGroupType(softProductDetailDto1.getGroupType());
                            hardBomGroup.setGroupId(softProductDetailDto1.getSkuId());
                            hardBomGroup.setGroupImage(AliImageUtil.imageCompress(AliImageUtil.imageCompress(softProductDetailDto1.getImageUrl(), 1, 750, ImageConstant.SIZE_SMALL), 1, 750, ImageConstant.SIZE_SMALL));
                            hardBomGroup.setCabinetType(softProductDetailDto1.getCabinetType());
                            hardBomGroup.setGroupName(softProductDetailDto1.getProductName());
                            hardBomGroup.setSecondCategoryId(softProductDetailDto1.getGroupSecondCategoryId());
                            hardBomGroup.setSecondCategoryName(softProductDetailDto1.getGroupSecondCategoryName());
                            hardBomGroup.setFurnitureType(softProductDetailDto1.getFurnitureType());
                            hardBomGroup.setPositionIndex(softProductDetailDto1.getPositionIndex());
                            hardBomGroup.setProductName(softProductDetailDto1.getProductName());
                            hardBomGroup.setCategoryName(softProductDetailDto1.getGroupSecondCategoryName());
                            replaceBomDto.setBomGroupDefault(hardBomGroup);
                            replaceBomDto.setBomGroupSelect(hardBomGroup);
                            return replaceBomDto;
                        }).collect(Collectors.toList());
                        cabinetBomDto.setReplaceBomList(replaceBomDtoList);
                        softResponse.setCabinetBomGroup(cabinetBomDto);
                        List<HardBomGroup> collect = softProductDetailDtoList.stream().map(softProductDetailDto1 -> {
                            HardBomGroup hardBomGroup = new HardBomGroup();
                            hardBomGroup.setGroupType(softProductDetailDto1.getGroupType());
                            hardBomGroup.setGroupImage(softProductDetailDto1.getImageUrl());
                            hardBomGroup.setCabinetType(softProductDetailDto1.getCabinetType());
                            hardBomGroup.setCabinetTypeName(softProductDetailDto1.getCabinetTypeName());
                            hardBomGroup.setCategoryName(softProductDetailDto1.getCabinetTypeName());
                            hardBomGroup.setSecondCategoryName(softProductDetailDto1.getGroupSecondCategoryName());
                            hardBomGroup.setSecondCategoryId(softProductDetailDto1.getGroupSecondCategoryId());
                            hardBomGroup.setFurnitureType(softProductDetailDto1.getFurnitureType());
                            hardBomGroup.setGroupId(softProductDetailDto1.getSkuId());
                            return hardBomGroup;
                        }).collect(Collectors.toList());
                        softResponse.setGuiBomGroupList(collect);
                        softResponse.setGuiBomGroupQueryList(softResponse.getGuiBomGroupList().stream()
                                .map(hardBomGroup -> new QueryCabinetPropertyListRequest.GroupQueryRequest()
                                        .setCabinetTypeName(hardBomGroup.getCabinetTypeName())
                                        .setCabinetType(hardBomGroup.getCabinetType())
                                        .setGroupId(hardBomGroup.getGroupId())).collect(Collectors.toList()));
                        optionalSoftResponseList.add(softResponse);
                    });
                }
            }
            spaceDesign.setHardItemList(hardItemList);
            spaceDesign.setOptionalSoftResponseList(optionalSoftResponseList);
            if (RoomUseEnum.getCode(roomDto.getRoomName()) != null) {
                spaceDesign.setSpaceUsageId(RoomUseEnum.getCode(roomDto.getRoomName()));
                spaceDesignList.add(spaceDesign);
            } else {
                outSideList.add(spaceDesign);
            }

        }
        spaceDesignList.sort(Comparator.comparingInt(spaceDesign -> RoomConstants.ROOM_USE_ORDER_LIST.indexOf(spaceDesign.getSpaceUsageId())));
        if (CollectionUtils.isNotEmpty(outSideList)) {
            spaceDesignList.addAll(outSideList);
        }
        return spaceDesignList;
    }


    private List<SpaceDesign> handlerSoftBaseList(AladdinOrderDetailResponseVo responseVo) {
        List<SpaceDesign> spaceDesignList = new ArrayList<>();
        List<SpaceDesign> outSideList = new ArrayList<>();
        for (AladdinSoftSpaceVo roomDto : responseVo.getSoftBaseList()) {
            //空间基础信息
            SpaceDesign spaceDesign = new SpaceDesign();
            spaceDesign.setExtData(new OrderRoomExtDataDto(roomDto.getTaskType(), roomDto.getReferenceOnlyFlag()));
            spaceDesign.setSpaceUsageName(roomDto.getRoomName() == null ? RoomUseEnum.ROOM_WHOLE.getDescription() : roomDto.getRoomName());
            spaceDesign.setSpaceUsageId(roomDto.getRoomId() == null ? RoomUseEnum.ROOM_WHOLE.getCode() : roomDto.getRoomId());
            if (CollectionUtils.isNotEmpty(roomDto.getRoomPictureDtoList())) {
                roomDto.getRoomPictureDtoList().forEach(roomPictureDto -> {
                    roomPictureDto.setPictureUrlOrigin(roomPictureDto.getPictureUrl());
                    if (roomPictureDto.getPictureUrl() != null) {
                        roomPictureDto.setPictureUrl(AliImageUtil.imageCompress(roomPictureDto.getPictureUrl(), 2, 750, ImageConstant.SIZE_MIDDLE));
                    }
                });
            }
            List<RoomPictureDto> taskList = Lists.newArrayList();
            List<RoomPictureDto> notTaskList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(roomDto.getRoomPictureDtoList())) {
                for (RoomPictureDto roomPictureDto : roomDto.getRoomPictureDtoList()) {
                    if (roomPictureDto.getTaskId() != null && roomPictureDto.getTaskId() != 0) {
                        taskList.add(roomPictureDto);
                    } else {
                        notTaskList.add(roomPictureDto);
                    }
                }
                if (CollectionUtils.isNotEmpty(taskList)) {
                    //1：渲染中，2。已完成
                    spaceDesign.setDrawProgress(1);
                    for (RoomPictureDto roomPictureDto : taskList) {
                        if (StringUtils.isNotBlank(roomPictureDto.getPictureUrl())) {
                            spaceDesign.setDrawProgress(2);
                            break;
                        }
                    }
                }
            }
            spaceDesign.setRoomPictureDtoList(Lists.newArrayList());
            if (spaceDesign.getDrawProgress().equals(2)) {
                spaceDesign.getRoomPictureDtoList().addAll(taskList);
            } else {
                spaceDesign.getRoomPictureDtoList().addAll(notTaskList.stream().filter(roomPictureDto -> roomPictureDto.getType().equals(0)).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(spaceDesign.getRoomPictureDtoList())) {
                //过滤没有出结果的渲染图
                spaceDesign.setRoomPictureDtoList(spaceDesign.getRoomPictureDtoList().stream().filter(roomPictureDto -> roomPictureDto.getPictureUrl() != null).collect(Collectors.toList()));
            }
            //软装vo转response
            List<OptionalSoftResponse> optionalSoftResponseList = new ArrayList<>();
            List<AladdinSoftItemVo> softItemList = roomDto.getSoftItemList();
            if (CollectionUtils.isNotEmpty(softItemList)) {
                for (AladdinSoftItemVo softProductDetailDto : softItemList) {
                    optionalSoftResponseList.add(handlerSoftResponse(softProductDetailDto));
                }
            }
            spaceDesign.setOptionalSoftResponseList(optionalSoftResponseList);
            if (RoomUseEnum.getCode(roomDto.getRoomName()) != null) {
                spaceDesign.setSpaceUsageId(RoomUseEnum.getCode(roomDto.getRoomName()));
                spaceDesignList.add(spaceDesign);
            } else {
                outSideList.add(spaceDesign);
            }
        }

        spaceDesignList.sort(Comparator.comparingInt(spaceDesign -> RoomConstants.ROOM_USE_ORDER_LIST.indexOf(spaceDesign.getSpaceUsageId())));
        if (CollectionUtils.isNotEmpty(outSideList)) {
            spaceDesignList.addAll(outSideList);
        }
        return spaceDesignList;
    }

    private OptionalSoftResponse handlerSoftResponse(AladdinSoftItemVo softProductDetailDto) {
        OptionalSoftResponse softResponse = new OptionalSoftResponse();
        softResponse.setCategory(softProductDetailDto.getCategoryName());
        FurnitureEntity furnitureEntity = new FurnitureEntity();
        furnitureEntity.setSkuId(softProductDetailDto.getSkuId());
        furnitureEntity.setFurnitureName(softProductDetailDto.getProductName());
        furnitureEntity.setBrand(softProductDetailDto.getBrandAndSeries());
        furnitureEntity.setItemCount(softProductDetailDto.getProductCount());
        furnitureEntity.setImgUrl(softProductDetailDto.getImageUrl());
        furnitureEntity.setSmallImage(QiniuImageUtils.compressImageAndSamePic(softProductDetailDto.getImageUrl(), 100, 100));
        furnitureEntity.setProductStatus(softProductDetailDto.getProductStatus());
        furnitureEntity.setProductStatusName(softProductDetailDto.getProductStatusName());
        furnitureEntity.setFurnitureType(softProductDetailDto.getFurnitureType());
        furnitureEntity.setFurnitureType(softProductDetailDto.getFurnitureType());
        softResponse.setFurnitureSelected(furnitureEntity);
        if (furnitureEntity.getFurnitureType() != null && furnitureEntity.getFurnitureType() == 3) {//新定制品对app端 转为'定制家具'
            softResponse.setRootCategoryId(Constants.CUSTOMIZED_ROOT_CATEGORY_ID);
            softResponse.setRootCategoryName(Constants.CUSTOMIZED_ROOT_CATEGORY_NAME);
        }
        softResponse.setLastCategoryName(softProductDetailDto.getLastCategoryName());
        softResponse.setLastCategoryId(softProductDetailDto.getLastCategoryId());

        return softResponse;
    }

    @Override
    public AllProductOrderResponse queryProductOrderDetailForAPPById(Integer orderId, Integer width, String source) {
        Integer orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_HARDSOFT;// 默认11（套装、硬装+软装）
        String category = ProductProgramPraise.HARD_SOFT;// 默认（硬装+软装）
        Integer orderProgramTypeCode = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT;// 默认（1整套方案）
        Integer widthBag = 0;
        if (width != null && width > 0) {
            if (!source.equals("homecard")) {
                width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
            }
            widthBag = widthBag * ImageSize.WIDTH_PER_SIZE_26 / ImageSize.WIDTH_PER_SIZE_100;
        } else {
            width = 0;
        }
        AllProductOrderResponse response = new AllProductOrderResponse();
        String depositMoneyDefalut = "5000";
        DicDto dicVo = dicProxy.queryDicByKey(ProductProgramPraise.GLOBAL_DEPOSIT_MONEY_DEFAULT);
        // 数据库覆盖掉
        if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
            depositMoneyDefalut = dicVo.getValueDesc();
        }
        // 全局订金默认值
        response.setDepositMoneyDefalut(new BigDecimal(depositMoneyDefalut));
        if (orderId != null && orderId > 0) {
            AladdinOrderResultDto aladdinOrderResponseVo = this.queryAllProductOrderDetailById(orderId, true);

            if (aladdinOrderResponseVo != null && aladdinOrderResponseVo.getOrderInfo() != null) {
                if (aladdinOrderResponseVo.getOldOrder() != null) {
                    response.setOldFlag(aladdinOrderResponseVo.getOldOrder() == 0);
                }
                if (aladdinOrderResponseVo.getOrderInfo().getGradeId() != null && !aladdinOrderResponseVo.getOrderInfo().getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_CONTACT_STAGE.getStatus())) {
                    response.setGradeId(aladdinOrderResponseVo.getOrderInfo().getGradeId());
                    response.setGradeName(aladdinOrderResponseVo.getOrderInfo().getGradeName());
                }
                // 订单信息
                AladdinOrderBaseInfoVo orderInfo = aladdinOrderResponseVo.getOrderInfo();

                // ------蒋军 2018/04/12
                response.setSource(orderInfo.getSource());
                response.setCompleteTime(formate(orderInfo.getCompleteTime()));
                response.setExceptTime(setExceptTime(orderInfo));

                response.setOrderId(orderInfo.getId());
                // orderSaleType售卖类型：0：软装+硬装，1：软装 isAutoMatch 是否自由搭配
                if (orderInfo.getOrderSaleType() != null) {
                    if (ProductProgramPraise.HARD_STANDARD_ALL.equals(orderInfo.getOrderSaleType())) {
                        category = ProductProgramPraise.HARD_SOFT;
                        // 硬装+软装
                        if (orderInfo.getIsAutoMatch() == null) {

                        } else if (orderInfo.getIsAutoMatch()) {
                            // 自由搭配
                            orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_HARDSOFT;
                            orderProgramTypeCode = ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM;
                        } else {
                            // 套装
                            orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_HARDSOFT;
                            orderProgramTypeCode = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT;
                        }
                    } else if (ProductProgramPraise.HARD_STANDARD_SOFT.equals(orderInfo.getOrderSaleType())) {
                        category = ProductProgramPraise.HARD_STANDARD_SOFT_DESC;
                        // 纯软装
                        if (orderInfo.getIsAutoMatch() == null) {

                        } else if (orderInfo.getIsAutoMatch()) {
                            // 自由搭配
                            orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_SOFT;
                            orderProgramTypeCode = ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM;
                        } else {
                            // 套装
                            orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_SOFT;
                            orderProgramTypeCode = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT;
                        }
                    }
                }
                response.setOrderType(orderType); // 订单类型
                response.setOrderNum(orderInfo.getId().toString());// 大订单没有订单编号概念
                // 订单状态扭转
                if (orderInfo.getOrderStatus() != null) {
                    response.setState(homeBuildingService.getOrderStatus(orderInfo.getOrderStatus()));
                }
                if (StringUtils.isNotBlank(orderInfo.getOrderStatusStr())) {
                    response.setStateDesc(orderInfo.getOrderStatusStr());
                }
                if (orderInfo.getCreateTime() != null) {
                    response.setCreateTime(dayFormat_DAY.format(orderInfo.getCreateTime()));
                }
                if (orderInfo.getContractAmount() != null) {
                    response.setTotalPrice(orderInfo.getContractAmount());// 订单总价
                }

                // 方案原价
                if (orderInfo.getPreferAmount() != null) {
                    response.setOriginalPrice(orderInfo.getPreferAmount());
                } else {
                    response.setOriginalPrice(new BigDecimal(0));
                }

                //订单优惠金额
                if (orderInfo.getDiscountAmount() != null) {
                    response.setDiscountPrice(orderInfo.getDiscountAmount());
                } else {
                    response.setDiscountPrice(new BigDecimal(0));
                }

                // 交易信息
                AladdinDealInfoVo transactionInfo = aladdinOrderResponseVo.getTransactionInfo();
                if (transactionInfo != null) {
                    if (transactionInfo.getTranstionAmount() != null) {
                        response.setActualPayMent(transactionInfo.getTranstionAmount());// 应收金额
                    }
                    if (transactionInfo.getRemainAmount() != null) {
                        response.setUnpaidMoney(transactionInfo.getRemainAmount());// 剩余金额
                    }
                    if (transactionInfo.getPayedAmount() != null) {
                        response.setPaidMoney(transactionInfo.getPayedAmount());// 已交金额
                    }
                    BigDecimal originalAmount = transactionInfo.getOriginalAmount();// 优惠前原价
                    if (originalAmount != null) {
                        response.setOriginalAmount(originalAmount);
                    }
                    if (transactionInfo.getDstTime() != null) {
                        response.setPaymentTime(dayFormat_DAY.format(transactionInfo.getDstTime()));// 交款日期
                    }
                }

                // 用户信息
                UserInfoResponse userInfoResponse = new UserInfoResponse();
                AladdinUserInfoVo userInfo = aladdinOrderResponseVo.getUserInfo();
                if (userInfo != null) {
                    userInfoResponse.setUserId(userInfo.getId());
                    userInfoResponse.setUserName(userInfo.getUserName());
                    if (StringUtils.isNotBlank(userInfo.getMobile())) {
                        userInfoResponse.setMobileNum(userInfo.getMobile());
                        userInfoResponse.setHideMobileNum(userInfo.getMobile().replaceAll(
                                ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
                    }
                }

                // 房产信息
                HouseInfoResponseVo houseInfo = aladdinOrderResponseVo.getHouseInfo();
                if (houseInfo != null) {

                    response.setDeliverTime(houseInfo.getDeliverTime());

                    setHouseInfo(houseInfo, userInfoResponse);
                }

                // 置家顾问信息
                AladdinAdviserInfoVo adviserInfo = aladdinOrderResponseVo.getAdviserInfo();
                if (adviserInfo != null && StringUtils.isNotBlank(adviserInfo.getMobile())) {
                    userInfoResponse.setAdviserMobileNum(adviserInfo.getMobile());
                } else {
                    userInfoResponse.setAdviserMobileNum(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);
                }
                response.setUserInfo(userInfoResponse);
                response.setServiceMobile(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);// 客服

                // 可选方案信息
                AladdinBuildingProjectInfoVo buildingInfo = aladdinOrderResponseVo.getBuildingInfo();
                if (buildingInfo != null) {
                    response.setSelectSolutionInfo(setSelectSolutionInfo(buildingInfo, width));
                }

                // 已选方案信息
                AladdinProgramInfoVo solutionSelectedInfo = aladdinOrderResponseVo.getSolutionSelectedInfo();
                if (solutionSelectedInfo != null) {
                    List<SolutionRoomDetailVo> solutionRoomDetailVoList = aladdinOrderResponseVo.getSolutionRoomDetailVoList();
                    response.setSolutionOrderInfo(setSolutionOrderInfo(solutionSelectedInfo, orderProgramTypeCode, solutionRoomDetailVoList, widthBag, category));
                }

                // 硬装订单信息
                AladdinHardOrderInfoVo hardOrderInfo = aladdinOrderResponseVo.getHardOrderInfo();
                if (hardOrderInfo != null) {
                    HardConstructInfo hardConstructInfo = new HardConstructInfo();
                    hardConstructInfo.setHardOrderId(hardOrderInfo.getId());
                    hardConstructInfo.setHardOrderStatus(hardOrderInfo.getHardOrderStatus());
                    hardConstructInfo.setHardOrderStatusStr(hardOrderInfo.getHardOrderStatusStr());
                    if (hardOrderInfo.getCommenceTime() != null) {
                        hardConstructInfo.setConstructTime(dayFormat_DAY.format(hardOrderInfo.getCommenceTime()));
                    }
                    response.setHardConstructInfo(hardConstructInfo);
                }

                // 软装订单信息
                AladdinSoftOrderInfoVo softOrderInfo = aladdinOrderResponseVo.getSoftOrderInfo();
                if (softOrderInfo != null) {
                    SoftDeliveryInfo softDeliveryInfo = new SoftDeliveryInfo();
                    softDeliveryInfo.setSoftOrderId(softOrderInfo.getId());
                    softDeliveryInfo.setSoftOrderStatus(softOrderInfo.getSoftOrderStatus());
                    softDeliveryInfo.setSoftOrderStatusStr(softOrderInfo.getSoftOrderStatusStr());
                    response.setSoftDeliveryInfo(softDeliveryInfo);
                }

            }

        }

        return response;
    }

    @Override
    public FamilyOrderPayResponse queryPayBaseInfo(Integer orderId) {
        OrderDetailDto orderDetailDto = orderProxy.queryOrderSummaryInfo(orderId);
        if (orderDetailDto != null) {
            FamilyOrderPayResponse response = new FamilyOrderPayResponse();
            response.setUpItemAmount(orderDetailDto.getUpItemAmount());//选配升级项金额
            response.setOrderStatus(FamilyOrderStatus.getOrderStatus(orderDetailDto.getOrderStatus()));
            response.setOrderSubStatus(orderDetailDto.getOrderSubStatus());
            response.setAdviserName(StringUtils.isBlank(orderDetailDto.getAdviserName()) ? null : orderDetailDto.getAdviserName());
            response.setAdviserMobile(StringUtils.isBlank(orderDetailDto.getAdviserMobile()) ? null : orderDetailDto.getAdviserMobile());
            response.setOrderSource(orderDetailDto.getSource());
            response.setGradeId(orderDetailDto.getGradeId());
            response.setGradeName(orderDetailDto.getGradeName());
            response.setPreferentialAmount(orderDetailDto.getPreferentialAmount());

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
            BigDecimal contractAmount = orderDetailDto.getContractAmount() == null ? BigDecimal.ZERO : orderDetailDto.getContractAmount();

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

            response.setMinDeposit(globalDepositMoney);
            StringBuilder houseInfo = new StringBuilder();
            String buildingInfo = orderDetailDto.getBuildingInfo();
            if (StringUtils.isNotBlank(buildingInfo)) {
                JSONObject jsonObject = JSON.parseObject(buildingInfo);
                String type = jsonObject.getString("type");
                if ("3".equals(type)) {
                    orderDetailDto.setBuildingName(jsonObject.getString("projectName"));
                    orderDetailDto.setHousingNum(jsonObject.getString("buildingNo"));
                    orderDetailDto.setUnitNum(jsonObject.getString("unitNo"));
                    orderDetailDto.setRoomNum(jsonObject.getString("roomNo"));
                }
            }
            if (StringUtils.isNotBlank(orderDetailDto.getBuildingName())) {
                houseInfo.append(orderDetailDto.getBuildingName());
            }
            if (StringUtils.isNotBlank(orderDetailDto.getHousingNum())) {
                houseInfo.append(orderDetailDto.getHousingNum() + "栋");
            }
            if (StringUtils.isNotBlank(orderDetailDto.getUnitNum())) {
                houseInfo.append(orderDetailDto.getUnitNum() + "单元");
            }
            if (StringUtils.isNotBlank(orderDetailDto.getRoomNum())) {
                houseInfo.append(orderDetailDto.getRoomNum() + "室");
            }
            response.setHouseInfo(houseInfo.toString());
            response.setCanLoanAmount(response.getNewRestAmount() != null && response.getNewRestAmount().compareTo(BigDecimal.ZERO) > 0 ? response.getNewRestAmount() : UserConstants.CAN_LOAN_AMOUNT.subtract(response.getPaidAmount() == null ? BigDecimal.ZERO : response.getPaidAmount()));
            response.setCanLoanAmount(response.getCanLoanAmount().compareTo(BigDecimal.ZERO) >= 0 ? response.getCanLoanAmount() : BigDecimal.ZERO);
            if (isFinalAndPaidAmountEmpty(response)) {
                if (response.getFinalOrderPrice().getValue().compareTo(response.getPaidAmount()) == 0) {
                    response.setAllMoney(1);
                } else if (response.getFinalOrderPrice().getValue().compareTo(response.getPaidAmount()) < 0) {
                    response.setAllMoney(2);
                }
            }
            return response;
        }
        return null;
    }

    @Override
    public BigDecimal queryMinDepositByUserAndOrder(Integer userId, Integer orderId) {
        //默认定金
        BigDecimal depositMoneyDefault = globalDepositMoney;

        Boolean isToCOrder = false;

        OrderSimpleInfoRequestVo params = new OrderSimpleInfoRequestVo();
        List<Integer> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        params.setOrderNums(orderIds);
        List<OrderSimpleInfoResponseVo> response = orderProxy.querySimpleInfoByOrderNums(params);
        if (CollectionUtils.isNotEmpty(response) && response.get(0).getUserId() != null) {
            isToCOrder = response.get(0).getOriginalSource() == 21;
        }

        if (isToCOrder) {//判断是否toC订单
            depositMoneyDefault = toCDepositMoney;
        } else if (tocService.queryCurrentUserIsNewUserWithInvitationCode(userId)) {//判断用户是否是有邀请码的新用户
            //设置限制金额
            depositMoneyDefault = invitationDepositMoney;
        }

        return depositMoneyDefault;
    }

    @Override
    public Boolean appointOfflinePay(Integer orderId) {
        return orderProxy.appointOfflinePay(orderId);
    }

    @Override
    public Integer preConfirmSolution(Integer orderId) {
        return orderProxy.preConfirmSolution(orderId);
    }

    @Override
    public TransactionDetail queryPaymentRecordDetailById(ProgramOrderRecordRequest params) {
        return orderProxy.queryPaymentRecordDetailById(ImmutableBiMap.of("id", params.getId()));
    }

    @Override
    public SoftListResponse querySoftListByOrderId(ProgramOrderDetailRequest request) {
        Integer width = request.getWidth() == null ? 750 : request.getWidth();
        SoftListResponse softListResponse = new SoftListResponse();
        List<Integer> fourCategoryIds = new ArrayList<Integer>();

        if (null != request.getIsValetOrder() && request.getIsValetOrder() == 1) {
            // 代客下单
            ValetOrderResultDto valetOrderResultDto = productProgramOrderProxy.queryValetOrderDetail(request.getOrderId());
            if (null == valetOrderResultDto) {
                return null;
            }
            softListResponse = assembleOrderDetailResult(valetOrderResultDto, width, fourCategoryIds);
            AppOrderBaseInfoResponseVo orderInfo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());
            if (orderInfo != null && null != softListResponse) {
                softListResponse.setSoftTotal(orderInfo.getTotalProductCount());
                softListResponse.setSoftFinishNum(orderInfo.getCompleteDelivery());
            }
        } else {
            softListResponse = getSkuListByParams(request.getOrderId(), width, fourCategoryIds);
        }
        // 查询二级类目
        if (null != softListResponse && CollectionUtils.isNotEmpty(fourCategoryIds)) {
            proccessProductCategory(softListResponse.getOrderSoftDetailResultDto(), fourCategoryIds);
        }
        return softListResponse;
    }

    @Override
    public SoftListResponse getSkuListByOrderId(Integer orderId){
        return getSkuListByParams(orderId, 750, new ArrayList<Integer>());
    }

    public SoftListResponse getSkuListByParams(Integer orderId, Integer width, List<Integer> fourCategoryIds){
        SoftListResponse softListResponse = new SoftListResponse();
        OrderSoftDetailResultSimpleDto orderSoftDetailResultSimpleDto = new OrderSoftDetailResultSimpleDto();
        OrderDetailResultDto orderDetailResultDto = productProgramOrderProxy.queryOrderDetailById(orderId);
        if (null == orderDetailResultDto) {
            return null;
        }
        OrderSoftDetailResultDto orderSoftDetailResultDto = orderDetailResultDto.getOrderSoftDetailResultDto();
        if (null == orderSoftDetailResultDto) {
            return null;
        }

        List<SoftRoomSkuSimpleInfo> softRoomSkuSimpleInfos = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(orderSoftDetailResultDto.getSoftRoomInfos())) {
            for (SoftRoomSkuInfo roomInfo : orderSoftDetailResultDto.getSoftRoomInfos()) {
                SoftRoomSkuSimpleInfo softRoomSkuSimpleInfo = new SoftRoomSkuSimpleInfo();
                softRoomSkuSimpleInfo.setRoomId(roomInfo.getRoomId());
                softRoomSkuSimpleInfo.setRoomName(roomInfo.getRoomName());
                softRoomSkuSimpleInfo.setRoomImage(AliImageUtil.imageCompress(roomInfo.getRoomImage(), 2, width, ImageConstant.SIZE_SMALL));
                softRoomSkuSimpleInfo.setSpaceUsageId(RoomUseEnum.getCode(roomInfo.getRoomName()));
                List<SoftSkuSimpleInfo> softSkuSimpleInfos = new ArrayList<>();
                List<SoftRoomBomInfo> softBomSimpleInfos = new ArrayList<>();

                // 处理sku
                if (CollectionUtils.isNotEmpty(roomInfo.getSkuInfos())) {
                    for (SimpleRoomSkuDto skuInfo : roomInfo.getSkuInfos()) {
                        softSkuSimpleInfos.add(transferSoftVo(skuInfo, width));
                        fourCategoryIds.add(skuInfo.getProductCategory());
                    }
                }

                // 处理bom
                if(CollectionUtils.isNotEmpty(roomInfo.getBomGroupInfoDtos())){
                    softBomSimpleInfos = transferBomInfo(roomInfo, fourCategoryIds, width);
                }

                softRoomSkuSimpleInfo.setBomInfos(softBomSimpleInfos);
                softRoomSkuSimpleInfo.setSkuInfos(softSkuSimpleInfos);
                softRoomSkuSimpleInfos.add(softRoomSkuSimpleInfo);
            }
            softRoomSkuSimpleInfos.sort(Comparator.comparingInt(softRoom -> RoomConstants.ROOM_USE_ORDER_LIST.indexOf(softRoom.getSpaceUsageId())));
        }
        orderSoftDetailResultSimpleDto.setSoftRoomInfos(softRoomSkuSimpleInfos);

        List<SoftSkuSimpleInfo> softSkuSimpleInfos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderSoftDetailResultDto.getSoftNoRoomList())) {
            for (SimpleRoomSkuDto skuInfo : orderSoftDetailResultDto.getSoftNoRoomList()) {
                softSkuSimpleInfos.add(transferSoftVo(skuInfo, width));
                fourCategoryIds.add(skuInfo.getProductCategory());
            }
        }
        orderSoftDetailResultSimpleDto.setSoftNoRoomList(softSkuSimpleInfos);
        softListResponse.setOrderSoftDetailResultDto(orderSoftDetailResultSimpleDto);
        // 获取软装数量
        getSoftNum(softListResponse);
        return softListResponse;
    }

    public void getSoftNum(SoftListResponse softListResponse){
        Integer softTotal = 0;
        Integer softFinishNum = 0;
        if(CollectionUtils.isNotEmpty(softListResponse.getOrderSoftDetailResultDto().getSoftNoRoomList())){
            for(SoftSkuSimpleInfo noRoomTmp : softListResponse.getOrderSoftDetailResultDto().getSoftNoRoomList()){
                if(noRoomTmp.getProductStatus() >= ProductStatusEnum.COMPLETE_DELIVERY.getCode()){
                    softFinishNum += noRoomTmp.getProductCount();
                }
                softTotal += noRoomTmp.getProductCount();
            }
        }

        if(CollectionUtils.isNotEmpty(softListResponse.getOrderSoftDetailResultDto().getSoftRoomInfos())){
            for(SoftRoomSkuSimpleInfo roomTmp : softListResponse.getOrderSoftDetailResultDto().getSoftRoomInfos()){
                if(CollectionUtils.isNotEmpty(roomTmp.getSkuInfos())){
                    for(SoftSkuSimpleInfo skuTmp : roomTmp.getSkuInfos()){
                        if(skuTmp.getProductStatus() >= ProductStatusEnum.COMPLETE_DELIVERY.getCode()){
                            softFinishNum += skuTmp.getProductCount();
                        }
                        softTotal += skuTmp.getProductCount();
                    }
                }
                if(CollectionUtils.isNotEmpty(roomTmp.getBomInfos())){
                    for(SoftRoomBomInfo bomTmp : roomTmp.getBomInfos()){
                        if(bomTmp.getProductStatus() >= ProductStatusEnum.COMPLETE_DELIVERY.getCode()){
                            softFinishNum++;
                        }
                    }
                    softTotal += roomTmp.getBomInfos().size();
                }
            }
        }
        softListResponse.setSoftFinishNum(softFinishNum);
        softListResponse.setSoftTotal(softTotal);
    }

    public List<SoftRoomBomInfo> transferBomInfo(SoftRoomSkuInfo roomInfo, List<Integer> fourCategoryIds, Integer width){
        List<SoftRoomBomInfo> softBomSimpleInfos = new ArrayList<>();

        for (SoftRoomBomInfo bomInfo : roomInfo.getBomGroupInfoDtos()){
            ProductStatusEnum productStatus = transferBomStatus(bomInfo.getNewStatus());
            bomInfo.setProductStatus(productStatus.getCode());
            bomInfo.setProductStatusStr(productStatus.getDescription());
            bomInfo.setCategoryName(bomInfo.getGroupCategoryName());
            if(bomInfo.getGroupType() == 10){
                bomInfo.setCategoryName(bomInfo.getGroupSecondCategoryName());
            }

            SoftRoomBomInfo.GroupQueryRequest groupQueryRequest = new SoftRoomBomInfo.GroupQueryRequest();
            groupQueryRequest.setCabinetType(bomInfo.getCabinetType());
            groupQueryRequest.setCabinetTypeName(bomInfo.getCabinetTypeName());
            groupQueryRequest.setDefaultGroupId(bomInfo.getGroupId());
            groupQueryRequest.setGroupId(bomInfo.getGroupId());
            groupQueryRequest.setDefaultGroupNum(bomInfo.getGroupNum());
            Boolean hasExist = false;
            if(CollectionUtils.isNotEmpty(softBomSimpleInfos) && bomInfo.getGroupType() == 10){
                for(SoftRoomBomInfo bomInfoTmp : softBomSimpleInfos){
                    if(bomInfo.getGroupSecondCategoryId().equals(bomInfoTmp.getGroupSecondCategoryId())){
                        bomInfoTmp.getQueryList().add(groupQueryRequest);
                        hasExist = true;
                        // 如果新加入的柜体，状态要靠前，则使用更靠前的状态作为bom的状态
                        if(bomInfo.getProductStatus() < bomInfoTmp.getProductStatus() &&
                                !ProductStatusEnum.COMPLETE.getCode().equals(bomInfo.getProductStatus())){
                            bomInfoTmp.setProductStatus(bomInfo.getProductStatus());
                            bomInfoTmp.setProductStatusStr(bomInfo.getProductStatusStr());
                        }
                        // 用空间图
                        bomInfoTmp.setGroupImage(AliImageUtil.imageCompress(roomInfo.getRoomImage() == null ? bomInfo.getGroupImage() : roomInfo.getRoomImage(), 2, width, ImageConstant.SIZE_SMALL));
                        break;
                    }
                }
            }
            if(Boolean.FALSE.equals(hasExist)){
                bomInfo.setGroupImage(AliImageUtil.imageCompress(bomInfo.getGroupImage(), 2, width, ImageConstant.SIZE_SMALL));
                bomInfo.setQueryList(new ArrayList<SoftRoomBomInfo.GroupQueryRequest>());
                bomInfo.getQueryList().add(groupQueryRequest);
                softBomSimpleInfos.add(bomInfo);
            }
        }
        return softBomSimpleInfos;
    }

    public ProductStatusEnum transferProductStatus(Integer newStatus){
        if(newStatus == null){
            return ProductStatusEnum.NEED_PAYING;
        }
        if(DmsProductStatusConstants.WAITING_PURCHASE.equals(newStatus)){
            // 待采购 => 待采购
            return ProductStatusEnum.NEED_PURCHASE;
        }else if(DmsProductStatusConstants.WAITING_MANUFACTURER_RECEIVE.equals(newStatus) ||
                DmsProductStatusConstants.WAITING_MANUFACTURER_SHIPMENT.equals(newStatus) ||
                DmsProductStatusConstants.MANUFACTURER_SHIPMENT_COMPLETED.equals(newStatus)){
            // 待厂家接单、待厂家出货、厂家出货完成 => 采购中
            return ProductStatusEnum.IN_PURCHASE;
        }else if(DmsProductStatusConstants.WAITING_LOGISTICS_ORDER.equals(newStatus) ||
                DmsProductStatusConstants.WAITING_SEND_LOGISTICS.equals(newStatus)){
            // 创建物流订单、待派物流单 => 待送货
            return ProductStatusEnum.NEED_DELIVERY;
        }else if(DmsProductStatusConstants.WAITING_ORDERS.equals(newStatus) ||
                DmsProductStatusConstants.WAITING_COLLECT.equals(newStatus) ||
                DmsProductStatusConstants.WAITING_SHIPMENT.equals(newStatus) ||
                DmsProductStatusConstants.WAITING_ARRIVAL.equals(newStatus)) {
            // 待接单、待揽收、待发货、待到货/送货中 => 送货中
            return ProductStatusEnum.IN_DELIVERY;
        }else if(DmsProductStatusConstants.WAITING_INSTALL.equals(newStatus)) {
            // 待安装 => 待安装
            return ProductStatusEnum.WAIT_INSTALL;
        }else if(DmsProductStatusConstants.WAITING_CHECK.equals(newStatus)) {
            // 待验收 => 待验收
            return ProductStatusEnum.WAIT_CHECK;
        }else if(DmsProductStatusConstants.COMPLETED.equals(newStatus)) {
            // 完结 => 已完成
            return ProductStatusEnum.COMPLETE;
        }
        return ProductStatusEnum.NEED_PAYING;
    }

    public ProductStatusEnum transferBomStatus(Integer newStatus){
        if(newStatus == null){
            return ProductStatusEnum.NEED_PAYING;
        }
        if(DmsBomStatusEnum.WAITING_PURCHASE.getStatus() == newStatus){
            // 待采购 => 待采购
            return ProductStatusEnum.NEED_PURCHASE;
        }else if(DmsBomStatusEnum.WAITING_MANUFACTURER_RECEIVE.getStatus() == newStatus ||
                DmsBomStatusEnum.WAITING_MANUFACTURER_SHIPMENT.getStatus() == newStatus ||
                DmsBomStatusEnum.MANUFACTURER_SHIPMENT_COMPLETED.getStatus() == newStatus){
            // 待厂家接单、待厂家出货、厂家出货完成 => 采购中
            return ProductStatusEnum.IN_PURCHASE;
        }else if(DmsBomStatusEnum.WAITING_LOGISTICS_ORDER.getStatus() == newStatus ||
                DmsBomStatusEnum.WAITING_SEND_LOGISTICS.getStatus() == newStatus){
            // 创建物流订单、待派物流单 => 待送货
            return ProductStatusEnum.NEED_DELIVERY;
        }else if(DmsBomStatusEnum.WAITING_ORDERS.getStatus() == newStatus ||
                DmsBomStatusEnum.WAITING_COLLECT.getStatus() == newStatus ||
                DmsBomStatusEnum.WAITING_SHIPMENT.getStatus() == newStatus ||
                DmsBomStatusEnum.WAITING_ARRIVAL.getStatus() == newStatus) {
            // 待接单、待揽收、待发货、待到货/送货中 => 送货中
            return ProductStatusEnum.IN_DELIVERY;
        }else if(DmsBomStatusEnum.WAITING_INSTALL.getStatus() == newStatus) {
            // 待安装 => 待安装
            return ProductStatusEnum.WAIT_INSTALL;
        }else if(DmsBomStatusEnum.WAITING_CHECK.getStatus() == newStatus) {
            // 待验收 => 待验收
            return ProductStatusEnum.WAIT_CHECK;
        }else if(DmsBomStatusEnum.COMPLETED.getStatus() == newStatus) {
            // 完结 => 已完成
            return ProductStatusEnum.COMPLETE;
        }
        return ProductStatusEnum.NEED_PAYING;
    }

    public SoftSkuSimpleInfo transferSoftVo(SimpleRoomSkuDto skuInfo, Integer width){
        SoftSkuSimpleInfo softSkuSimpleInfoTmp = new SoftSkuSimpleInfo();
        softSkuSimpleInfoTmp.setSkuId(skuInfo.getSkuId());
        softSkuSimpleInfoTmp.setRoomId(skuInfo.getRoomId());
        softSkuSimpleInfoTmp.setProductName(skuInfo.getProductName());
        softSkuSimpleInfoTmp.setProductImage(AliImageUtil.imageCompress(skuInfo.getProductImage(), 2, width, ImageConstant.SIZE_SMALL));
        softSkuSimpleInfoTmp.setFurnitureType(skuInfo.getFurnitureType());
        softSkuSimpleInfoTmp.setProductCount(skuInfo.getProductCount());
        softSkuSimpleInfoTmp.setProductCategory(skuInfo.getProductCategory());
        softSkuSimpleInfoTmp.setCategoryName(skuInfo.getCategoryName());
        softSkuSimpleInfoTmp.setLastCategoryId(skuInfo.getLastCategoryId());
        softSkuSimpleInfoTmp.setLastCategoryName(skuInfo.getLastCategoryName());
        softSkuSimpleInfoTmp.setNewStatus(skuInfo.getNewStatus());
        softSkuSimpleInfoTmp.setNewStatusName(skuInfo.getNewStatusName());
        softSkuSimpleInfoTmp.setGiftFlag(skuInfo.getGiftFlag());
        ProductStatusEnum productStatus = transferProductStatus(skuInfo.getNewStatus());
        softSkuSimpleInfoTmp.setProductStatus(productStatus.getCode());
        softSkuSimpleInfoTmp.setProductStatusStr(productStatus.getDescription());
        return softSkuSimpleInfoTmp;
    }

    /**
     * 下单价格异步校验
     *
     * @param result
     */
    @Override
    public void CheckPrice(AladdinCreateOrderResponseVo result, CreateFamilyOrderRequest request) {
        List<TaskAction<?>> taskActions = new ArrayList<>();
        taskActions.add(new TaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                try {
                    BigDecimal contractAmount = result.getContractAmount().setScale(0, BigDecimal.ROUND_DOWN);
                    QueryDraftRequest queryDraftRequest = new QueryDraftRequest();
                    queryDraftRequest.setOrderId(request.getOrderId());
                    SolutionDraftResponse response = homeV5PageService.querySolutionDraft(queryDraftRequest);
                    DraftSimpleResponse draftSimpleResponse = JsonUtils.json2obj(response.getDraftJsonStr(), DraftSimpleResponse.class);
                    BigDecimal draftTotal = draftSimpleResponse.getTotalPrice().setScale(0, BigDecimal.ROUND_DOWN);
                    if (!contractAmount.equals(draftTotal)) {
                        String mobileAndOrderId = "手机号：" + request.getMobileNum() + "，订单号：" + request.getOrderId();
                        //发送钉钉消息
                        aiDingTalk.sendDingTalkInfo("CreateFamilyOrder.CheckPrice", mobileAndOrderId,
                                draftSimpleResponse.getTotalPrice(), contractAmount);
                    }

                } catch (Exception e) {
                    LOG.info("CheckPrice Exception", e);
                }
                return 1;
            }
        });
        // 执行任务
        Executor.getInvokeOuterServiceFactory().asyncExecuteTask(taskActions);

    }

    private void proccessProductCategory(OrderSoftDetailResultSimpleDto orderSoftDetailResultDto,
                                         List<Integer> fourCategoryIds) {
        if (null == orderSoftDetailResultDto) {
            return;
        }

        Map<String, ProductCategoryVo> productCategoryMap = productProxy.batchQueryCategoryFullPathByIds(fourCategoryIds);
        if (!org.springframework.util.CollectionUtils.isEmpty(productCategoryMap)) {
            List<SoftRoomSkuSimpleInfo> softRoomInfos = orderSoftDetailResultDto.getSoftRoomInfos();
            if (CollectionUtils.isNotEmpty(softRoomInfos)) {
                for (SoftRoomSkuSimpleInfo roomSkuInfo : softRoomInfos) {
                    List<SoftSkuSimpleInfo> softRoomList = roomSkuInfo.getSkuInfos();
                    if (CollectionUtils.isNotEmpty(softRoomList)) {
                        for (SoftSkuSimpleInfo softRoomSku : softRoomList) {
                            ProductCategoryVo categoryVo = productCategoryMap.get("" + softRoomSku.getProductCategory());
                            if (null != categoryVo && !StringUtil.isNullOrEmpty(categoryVo.getSecondCategoryName())) {
                                softRoomSku.setCategoryName(categoryVo.getSecondCategoryName());
                            }
                        }
                    }
                }
            }

            List<SoftSkuSimpleInfo> softNoRoomList = orderSoftDetailResultDto.getSoftNoRoomList();
            if (CollectionUtils.isNotEmpty(softNoRoomList)) {
                for (SoftSkuSimpleInfo softNoRoomSku : softNoRoomList) {
                    ProductCategoryVo categoryVo = productCategoryMap.get("" + softNoRoomSku.getProductCategory());
                    if (null != categoryVo && !StringUtil.isNullOrEmpty(categoryVo.getSecondCategoryName())) {
                        softNoRoomSku.setCategoryName(categoryVo.getSecondCategoryName());
                    }
                }
            }

        }

    }

    private SoftListResponse assembleOrderDetailResult(ValetOrderResultDto dto, Integer width, List<Integer> fourCategoryIds) {
        SoftListResponse orderDetailResultDto = new SoftListResponse();

        OrderSoftDetailResultSimpleDto orderSoftDetailResultDto = new OrderSoftDetailResultSimpleDto();
        List<SoftSkuSimpleInfo> softNoRoomList = new ArrayList<>();


        if (CollectionUtils.isNotEmpty(dto.getSoftOrderInfoDto())) {
            for (SoftOrderProductResultDto productVo : dto.getSoftOrderInfoDto()) {
                SoftSkuSimpleInfo skuDto = new SoftSkuSimpleInfo();
                skuDto.setSkuId(productVo.getSkuId());
                skuDto.setProductName(productVo.getName());
                skuDto.setProductCount(productVo.getAmount());
                skuDto.setProductStatus(productVo.getProductStatus());
                skuDto.setProductStatusStr(productVo.getProductStatusStr());
                skuDto.setProductImage(AliImageUtil.imageCompress(productVo.getImageUrl(), 2, width, ImageConstant.SIZE_MIDDLE));
                skuDto.setFurnitureType(productVo.getType());
                skuDto.setCategoryName(productVo.getCategoryName());
                skuDto.setLastCategoryId(productVo.getLastCategoryId());
                skuDto.setLastCategoryName(productVo.getLastCategoryName());
                softNoRoomList.add(skuDto);

                fourCategoryIds.add(productVo.getProductCategory());
            }
        }

        if (CollectionUtils.isNotEmpty(dto.getSoftIncreaseInfoDto())) {
            for (SoftOrderProductResultDto productVo : dto.getSoftIncreaseInfoDto()) {
                SoftSkuSimpleInfo skuDto = new SoftSkuSimpleInfo();
                skuDto.setSkuId(productVo.getSkuId());
                skuDto.setProductName(productVo.getName());
                skuDto.setProductCount(productVo.getAmount());
                skuDto.setProductStatus(productVo.getProductStatus());
                skuDto.setProductStatusStr(productVo.getProductStatusStr());
                skuDto.setProductImage(AliImageUtil.imageCompress(productVo.getImageUrl(), 2, width, ImageConstant.SIZE_MIDDLE));
                skuDto.setFurnitureType(productVo.getType());
                skuDto.setCategoryName(productVo.getCategoryName());
                skuDto.setLastCategoryId(productVo.getLastCategoryId());
                skuDto.setLastCategoryName(productVo.getLastCategoryName());
                softNoRoomList.add(skuDto);
            }
        }

        orderSoftDetailResultDto.setSoftNoRoomList(softNoRoomList);
        orderDetailResultDto.setOrderSoftDetailResultDto(orderSoftDetailResultDto);
        return orderDetailResultDto;
    }

    @Override
    public AladdinOrderResultDto queryAllProductOrderDetailById(Integer masterOrderId, boolean isOld) {
        AladdinOrderResultDto masterOrderInfoVo = new AladdinOrderResultDto();
        // 1、查询出大订单详情
        AppMasterOrderDetailDto masterOrderResultDto = personalCenterProxy.queryMasterOrderDetail(masterOrderId);
        if (null == masterOrderResultDto) {
            return null;
        }

        List<IdentityTaskAction<Object>> identityTaskActionList = new ArrayList<>();
        // 2、组装订单基本信息对象
        AladdinOrderBaseInfoVo orderInfo = new AladdinOrderBaseInfoVo();
        orderInfo.setId(masterOrderId);

        AppMasterOrderInfoDto appMasterOrderInfoDto = masterOrderResultDto.getMasterOrderInfo();
        if (null != appMasterOrderInfoDto) {
            identityTaskActionList.add(getOrderBaseInfo(masterOrderResultDto, masterOrderInfoVo, orderInfo));
        }

        if (isOld) {
            //组装户型方案信息
            identityTaskActionList.add(getBuildingInfoVo(masterOrderResultDto, masterOrderInfoVo));
            //组装软装、硬装订单信息对象
            identityTaskActionList.add(getSubOrderInfo(masterOrderResultDto, masterOrderInfoVo));
        }
        //组装用户基本信息对象
        AladdinUserInfoVo userInfo = new AladdinUserInfoVo();
        AppMasterOrderCustomerInfoDto appMasterOrderCustomerInfoDto = masterOrderResultDto.getCustomerInfo();
        if (null != appMasterOrderCustomerInfoDto) {
            userInfo.setId(appMasterOrderCustomerInfoDto.getUserId());
            userInfo.setMobile(appMasterOrderCustomerInfoDto.getUserMobile());
            userInfo.setUserName(appMasterOrderCustomerInfoDto.getUserName());
        }
        //组装房产信息
        identityTaskActionList.add(getHouseInfoVo(masterOrderResultDto, masterOrderInfoVo));

        // 是否选择方案，查询快照中，是否有空间商品信息,如果有，说明选择了方案，如果没有，说明没选择方案
        AladdinProgramInfoVo solutionSelectedInfo = new AladdinProgramInfoVo();

        //添加查询方案空间详情线程
        identityTaskActionList
                .add(getSolutionRoomDetailVosTask(masterOrderId, solutionSelectedInfo, appMasterOrderInfoDto));
        //添加查询订单账户收款信息线程
        identityTaskActionList.add(getAccountInfoVo(masterOrderResultDto));

        // 执行任务
        Map<String, Object> taskReusltMap = TaskProcessManager.getTaskProcess().executeIdentityTask(identityTaskActionList);

        List<SolutionRoomDetailVo> solutionRoomDetailVoList = (List<SolutionRoomDetailVo>) taskReusltMap
                .get("getSolutionRoomDetailVos");

        AladdinDealInfoVo transactionInfo = (AladdinDealInfoVo) taskReusltMap.get("getAccountInfoVo");
        if (masterOrderInfoVo.getSoftOrderInfo() != null) {
            masterOrderInfoVo.getSoftOrderInfo().setDstTime(transactionInfo.getDstTime());
        }
        // 封装是否参加1219活动的信息
        masterOrderInfoVo.setJoinActFlag(masterOrderResultDto.getJoinActFlag());
        masterOrderInfoVo.setJoinTime(masterOrderResultDto.getJoinTime());
        masterOrderInfoVo.setJoinTimeStr(masterOrderResultDto.getJoinTimeStr());
        masterOrderInfoVo.setCheckResult(masterOrderResultDto.getCheckResult());
        masterOrderInfoVo.setOldOrder(masterOrderResultDto.getOldOrder());
        masterOrderInfoVo.setUserInfo(userInfo);
        masterOrderInfoVo.setSolutionSelectedInfo(solutionSelectedInfo);
        masterOrderInfoVo.setNoUpgradeInfos(masterOrderResultDto.getNoUpgradeInfos());
        masterOrderInfoVo.setUpgradeInfos(masterOrderResultDto.getUpgradeInfos());
        masterOrderInfoVo.setSolutionRoomDetailVoList(solutionRoomDetailVoList);
        masterOrderInfoVo.setTransactionInfo(transactionInfo);
        if (null != masterOrderResultDto.getRefundInfo()) {
            masterOrderInfoVo.setRefundInfo(masterOrderResultDto.getRefundInfo());
        }

        List<AladdinAddBagInfoVo> bagsProductDtos = masterOrderResultDto.getAddBags();
        if (!CollectionUtils.isEmpty(bagsProductDtos)) {
            masterOrderInfoVo.setAddBags(bagsProductDtos);
        }

        if (null != masterOrderInfoVo.getSolutionSelectedInfo() && null != masterOrderInfoVo.getSolutionSelectedInfo().getFurnitureCount()) {
            masterOrderInfoVo.getSolutionSelectedInfo().setFurnitureCount(masterOrderResultDto.getProductCount());
        }


        //toc老带新用户手填信息回显处理
        HouseInfoResponseVo houseInfoResponseVo = masterOrderInfoVo.getHouseInfo();
        if (houseInfoResponseVo != null && com.ihomefnt.common.util.StringUtil.isNotBlank(houseInfoResponseVo.getBuildingInfo())) {//手填信息不为空
            String buildingInfo = houseInfoResponseVo.getBuildingInfo();
            JSONObject jasonObject = JSONObject.parseObject(buildingInfo);
            Map map = (Map) jasonObject;
            String type = String.valueOf(map.get("type"));
            if ("2".equals(type)) {//type=2表示手填数据
                houseInfoResponseVo.setProvinceName(String.valueOf(map.get("province")));
                houseInfoResponseVo.setCityName(String.valueOf(map.get("city")));
                houseInfoResponseVo.setHouseProjectName(String.valueOf(map.get("community")));
                houseInfoResponseVo.setHousingNum(String.valueOf(map.get("buildNo")));
                houseInfoResponseVo.setUnitNum(String.valueOf(map.get("unitNo")));
                houseInfoResponseVo.setRoomNum(String.valueOf(map.get("roomNo")));
                if (com.ihomefnt.common.util.StringUtil.isBlank(houseInfoResponseVo.getSize())) {
                    houseInfoResponseVo.setSize(String.valueOf(map.get("area")));
                }
                masterOrderInfoVo.setHouseInfo(houseInfoResponseVo);
            }
        }
        return masterOrderInfoVo;
    }

    @Override
    public AladdinSolutionDetailResponseVo querySolutionDetailWithMasterOrderId(Integer masterOrderId) {
        /**
         * 1、根据大订单id查询大订单信息(会查订单快照进行信息补充)
         *
         * 2、根据空间id集合查询空间信息（批量）
         *
         * 3、查询风格信息
         *
         * 4、查询系列信息
         *
         */
        List<IdentityTaskAction<Object>> identityTaskActionList = new ArrayList<>();
        identityTaskActionList.add(new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                return personalCenterProxy.queryMasterOrderDetail(masterOrderId);
            }

            @Override
            public String identity() {
                return "queryMasterOrderDetail";
            }
        });
        identityTaskActionList.add(new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                return proxy.batchQueryRoomSkuInfo(masterOrderId);
            }

            @Override
            public String identity() {
                return "batchQueryRoomSkuInfo";
            }
        });
        // 执行
        Map<String, Object> taskResultMap = TaskProcessManager.getTaskProcess().executeIdentityTask(identityTaskActionList);

        AppMasterOrderDetailDto appMasterOrderDetailDto = (AppMasterOrderDetailDto) taskResultMap.get("queryMasterOrderDetail");
        if (null == appMasterOrderDetailDto) {
            return null;
        }

        AladdinSolutionDetailResponseVo solutionDetailResultVo = new AladdinSolutionDetailResponseVo();

        Integer houseId = null;
        boolean isAutoMatch = false;
        BigDecimal contractAmount = BigDecimal.ZERO;
        AppMasterOrderInfoDto appMasterOrderInfoDto = appMasterOrderDetailDto.getMasterOrderInfo();
        if (null != appMasterOrderInfoDto) {
            houseId = appMasterOrderInfoDto.getCustomerHouseId();
            contractAmount = appMasterOrderInfoDto.getContractAmount();
            solutionDetailResultVo.setHouseProjectId(appMasterOrderInfoDto.getBuildingId());
            solutionDetailResultVo.setDecorationType(appMasterOrderInfoDto.getOrderSaleType());

            // 封装楼盘信息
            Integer buildingId = appMasterOrderInfoDto.getBuildingId();
            if (null != buildingId) {
                ProjectResponse buildingResultDto = homeCardBossProxy.queryBuildingDetail(buildingId);
                if (null != buildingResultDto) {
                    solutionDetailResultVo.setHouseProjectName(buildingResultDto.getBuildingName());
                }
            }
        }

        if (null != houseId) {
            HousePropertyInfoResultDto housePropertyInfoResultDto = appMasterOrderDetailDto.getHouseInfo();
            if (null != housePropertyInfoResultDto) {
                solutionDetailResultVo.setZoneId(housePropertyInfoResultDto.getZoneId());
                solutionDetailResultVo.setPartitionName(housePropertyInfoResultDto.getPartitionName());
                solutionDetailResultVo.setUnitNum(housePropertyInfoResultDto.getUnitNum());
                Integer houseTypeId = housePropertyInfoResultDto.getLayoutId();
                // 查询户型信息
                if (null != houseTypeId) {
                    List<THouseResponse> houseTypeDtos = programProxy.batchQueryHouse(Arrays.asList(houseTypeId));
                    if (!org.springframework.util.CollectionUtils.isEmpty(houseTypeDtos)) {
                        THouseResponse houseTypeDto = houseTypeDtos.get(0);
                        if (null != houseTypeDto) {
                            solutionDetailResultVo.setHouseTypeId(houseTypeId);
                            solutionDetailResultVo.setHouseTypeName(houseTypeDto.getHouseName());

                            HouseLayoutVo houseLayoutVo = ModelMapperUtil.strictMap(houseTypeDto, HouseLayoutVo.class);
                            solutionDetailResultVo.setHouseLayout(houseLayoutVo);
                        }
                    }
                }
            }
        }

        AppSoftOrderInfoDto appSoftOrderInfoDto = appMasterOrderDetailDto.getSoftOrderInfo();
        if (null != appSoftOrderInfoDto) {
            // 是否自由搭配方案
            isAutoMatch = (0 == appSoftOrderInfoDto.getSuitType());

            if (null != appMasterOrderInfoDto) {
                solutionDetailResultVo.setIsAutoMatch(isAutoMatch);
            }
        }

        List<SolutionRoomDetailVo> solutionRoomDetailVoList = new ArrayList<>();

        List<SnapshotOrderRoomInfoDto> snapshotOrderRoomInfoDtoList = (List<SnapshotOrderRoomInfoDto>) taskResultMap.get("batchQueryRoomSkuInfo");
        ;
        if (null == snapshotOrderRoomInfoDtoList) {
            snapshotOrderRoomInfoDtoList = new ArrayList<>();
        }

        for (SnapshotOrderRoomInfoDto item : snapshotOrderRoomInfoDtoList) {
            if (null == item.getOrderNum() || !masterOrderId.equals(item.getOrderNum())) {
                continue;
            }

            // 因为现在方案没有做单独的快照，方案信息下沉到空间里面，所以如果是整套方案，直接取其中一个空间的方案信息即可
            if (null == solutionDetailResultVo.getSolutionId() && !isAutoMatch) {
                // TODO 判断是否自由搭配方案, 进而判断是否需要查询方案快照信息
                solutionDetailResultVo.setSolutionId(item.getSolutionId());
                solutionDetailResultVo.setSolutionName(item.getSolutionName());
                solutionDetailResultVo.setSolutionSeriesName(item.getSuitName());
                solutionDetailResultVo.setSolutionStyleName(item.getSolutionStyleName());
                solutionDetailResultVo.setSolutionGraphicDesignUrl(item.getGraphicDesignUrl());
                // solutionDetailResultVo.setDecorationType(item.getDecorationType());
                // solutionDetailResultVo.setSolutionDiscount(item.);
                // solutionDetailResultVo.setSolutionDesignIdea(solutionDesignIdea);
                solutionDetailResultVo.setSolutionTotalSalePrice(item.getRoomSalePrice());
                solutionDetailResultVo.setSolutionTotalDiscountPrice(contractAmount);
                // solutionDetailResultVo.setSolutionTotalHardDecorationSalePrice(item.getRoomSoftSalePrice());
                // solutionDetailResultVo.setSolutionTotalSoftDecorationDiscountPrice(item.getRoomSoftSalePrice());
                solutionDetailResultVo.setSolutionAdvantage(item.getSolutionAdvantage());
                solutionDetailResultVo.setSolutionTags(item.getSolutionTags());
            }

            SolutionRoomDetailVo solutionRoomDetailVo = new SolutionRoomDetailVo();
            // 组装快照空间到方案空间vo
            convertSolutionRoomDetail(solutionRoomDetailVo, item);
            solutionRoomDetailVoList.add(solutionRoomDetailVo);
        }

        // 获取客厅信息
        LivingRoomDto livingRoomDto = getLivingRoomInfo(snapshotOrderRoomInfoDtoList);
        if (null != livingRoomDto) {
            solutionDetailResultVo.setSolutionHeadImgURL(livingRoomDto.getSolutionHeadImgURL());
        }

        solutionDetailResultVo.setSolutionRoomDetailVoList(solutionRoomDetailVoList);

        // 获取所有家具数
        solutionDetailResultVo.setSolutionTotalItemCount(getRoomSkuCount(snapshotOrderRoomInfoDtoList));

        // 增配包商品列表集合
        solutionDetailResultVo.setAddBags(appMasterOrderDetailDto.getAddBags());

        // 硬装升级项
        solutionDetailResultVo.setNoUpgradeInfos(appMasterOrderDetailDto.getNoUpgradeInfos());
        solutionDetailResultVo.setUpgradeInfos(appMasterOrderDetailDto.getUpgradeInfos());

        return solutionDetailResultVo;
    }

    /**
     * 查询账户信息
     *
     * @param masterOrderResultDto
     * @return
     */
    private IdentityTaskAction<Object> getAccountInfoVo(AppMasterOrderDetailDto masterOrderResultDto) {
        return new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                AladdinDealInfoVo transactionInfo = new AladdinDealInfoVo();

                // 收款信息
                AppMasterOrderAccountInfoDto accountInfo = masterOrderResultDto.getAccountInfo();

                if (null != accountInfo) {
                    transactionInfo = new AladdinDealInfoVo();

                    // 已确认收款金额
                    BigDecimal confirmedAmount = accountInfo.getConfirmedAmount();
                    if (null == confirmedAmount) {
                        confirmedAmount = BigDecimal.ZERO;
                    }

                    // 已确认退款金额
                    BigDecimal confirmedRefundAmt = accountInfo.getRefundedAmount();
                    if (null == confirmedRefundAmt) {
                        confirmedRefundAmt = BigDecimal.ZERO;
                    }

                    // 实际已支付金额 = 已确认收款金额 - 已确认退款金额
                    BigDecimal payedAmount = confirmedAmount.subtract(confirmedRefundAmt);
                    transactionInfo.setPayedAmount(payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP));

                    BigDecimal contractAmount = masterOrderResultDto.getMasterOrderInfo().getContractAmount();
                    if (null == contractAmount) {
                        contractAmount = BigDecimal.ZERO;
                    }

                    transactionInfo.setTranstionAmount(contractAmount.setScale(2, BigDecimal.ROUND_HALF_UP));

                    if (contractAmount.compareTo(payedAmount) > 0) {
                        transactionInfo.setRemainAmount(
                                contractAmount.subtract(payedAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else {
                        transactionInfo.setRemainAmount(BigDecimal.ZERO);
                    }

                    if (null == accountInfo.getOrderTime()) {
                        // 最新收款时间
                        transactionInfo.setDstTime(accountInfo.getLastUpdateTime());
                    } else {
                        // 签约日期
                        transactionInfo.setDstTime(accountInfo.getOrderTime());
                    }
                }

                return transactionInfo;
            }

            @Override
            public String identity() {
                return "getAccountInfoVo";
            }
        };
    }

    private IdentityTaskAction getSolutionRoomDetailVosTask(Integer masterOrderId, AladdinProgramInfoVo solutionSelectedInfo, AppMasterOrderInfoDto appMasterOrderInfoDto) {
        return new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                return getSolutionRoomDetailVos(masterOrderId, solutionSelectedInfo, appMasterOrderInfoDto);
            }

            @Override
            public String identity() {
                return "getSolutionRoomDetailVos";
            }
        };
    }

    private List<SolutionRoomDetailVo> getSolutionRoomDetailVos(Integer masterOrderId, AladdinProgramInfoVo solutionSelectedInfo, AppMasterOrderInfoDto appMasterOrderInfoDto) {
        // 获取空间商品图片
        List<SolutionRoomDetailVo> solutionRoomDetailVoList = new ArrayList<>();
        SolutionRoomDetailVo solutionRoomDetailVo = null;

        // 方案下单时候查询方案信息,代客下单不查
        if (!OrderConstant.VALETORDER_SOURCE.equals(appMasterOrderInfoDto.getSource())) {
            List<SnapshotOrderRoomInfoDto> snapshotOrderRoomInfoDtoList = proxy.batchQueryRoomSkuInfo(masterOrderId);
            if (null == snapshotOrderRoomInfoDtoList) {
                snapshotOrderRoomInfoDtoList = new ArrayList<>();
            }

            // 所有空间风格
            List<AladdinStyleInfoVo> styleList = new ArrayList<AladdinStyleInfoVo>();

            for (SnapshotOrderRoomInfoDto item : snapshotOrderRoomInfoDtoList) {

                if (null == item.getOrderNum() || !masterOrderId.equals(item.getOrderNum())) {
                    continue;
                }

                if (null == solutionSelectedInfo.getId()) {
                    solutionSelectedInfo.setId(item.getSolutionId());
                    solutionSelectedInfo.setName(item.getSolutionName());
                    // 方案类型
                    solutionSelectedInfo.setType(item.getDecorationType());
                }

                AladdinStyleInfoVo styleInfoVo = new AladdinStyleInfoVo();
                styleInfoVo.setName(item.getSolutionStyleName());
                styleList.add(styleInfoVo);

                solutionRoomDetailVo = new SolutionRoomDetailVo();
                // 组装快照空间到方案空间vo
                convertSolutionRoomDetail(solutionRoomDetailVo, item);
                solutionRoomDetailVoList.add(solutionRoomDetailVo);
            }

            // 获取客厅信息
            LivingRoomDto livingRoomDto = getLivingRoomInfo(snapshotOrderRoomInfoDtoList);
            if (null != livingRoomDto) {
                solutionSelectedInfo.setPic(livingRoomDto.getSolutionHeadImgURL());
                solutionSelectedInfo.setSeriesStr(livingRoomDto.getSeriesStr());
            }

            // 没有客厅取第一个
            if (!CollectionUtils.isEmpty(snapshotOrderRoomInfoDtoList) && null == livingRoomDto) {
                solutionSelectedInfo.setPic(snapshotOrderRoomInfoDtoList.get(0).getRoomImage());
                solutionSelectedInfo.setSeriesStr(snapshotOrderRoomInfoDtoList.get(0).getSuitName());
            }

            // 获取家具总数
            solutionSelectedInfo.setFurnitureCount(getRoomSkuCount(snapshotOrderRoomInfoDtoList));

            solutionSelectedInfo.setStyleList(styleList);
        }
        return solutionRoomDetailVoList;
    }

    /**
     * 查询所属项目相关信息
     *
     * @param masterOrderResultDto
     * @param masterOrderInfoVo
     * @return
     */
    private IdentityTaskAction<Object> getBuildingInfoVo(AppMasterOrderDetailDto masterOrderResultDto, AladdinOrderResultDto masterOrderInfoVo) {
        return new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                AladdinBuildingProjectInfoVo buildingInfo = new AladdinBuildingProjectInfoVo();

                // 房产信息
                HousePropertyInfoResultDto housePropertyInfoResultDto = masterOrderResultDto.getHouseInfo();

                List<AladdinHouseTypeInfoVo> buildingHouseList = new ArrayList<AladdinHouseTypeInfoVo>();

                if (null != housePropertyInfoResultDto) {
                    // 用户id
                    Integer userId = housePropertyInfoResultDto.getUserId();

                    // 项目id
                    Integer buildingId = housePropertyInfoResultDto.getBuildingId();

                    // 组装户型信息
                    Integer houseTypeId = housePropertyInfoResultDto.getLayoutId();

                    AladdinHouseTypeInfoVo houseTypeInfoVo = new AladdinHouseTypeInfoVo();

                    houseTypeInfoVo.setBuildingHouseId(houseTypeId);
                    houseTypeInfoVo.setBuildingHouseName(housePropertyInfoResultDto.getLayoutName());

                    // 意向阶段以及定金阶段需要查询该用户该户型的可选方案
                    Integer orderStatus = masterOrderResultDto.getMasterOrderInfo().getOrderStatus();

                    List<AladdinProgramInfoVo> solutionAvailableList = new ArrayList<AladdinProgramInfoVo>();

                    if (MasterOrderStatusEnum.ORDER_STATUS_INTENTIONAL_PHASE.getStatus().equals(orderStatus)
                            || MasterOrderStatusEnum.ORDER_STATUS_DEPOSIT_PHASE.getStatus().equals(orderStatus)) {
                        // 查询该用户该楼盘该户型的可选方案
                        List<SolutionSketchInfoResponseVo> solutionSketchInfoResponseVoList = programProxy
                                .getUserSpecificProgram(userId, buildingId, housePropertyInfoResultDto.getZoneId(), houseTypeId);

                        if (null == solutionSketchInfoResponseVoList) {
                            solutionSketchInfoResponseVoList = new ArrayList<>();
                        }

                        for (SolutionSketchInfoResponseVo solutionSketchItem : solutionSketchInfoResponseVoList) {

                            List<SolutionBaseInfoVo> solutionBaseInfoVoList = solutionSketchItem.getSeriesSolutionList();
                            if (null == solutionBaseInfoVoList) {
                                solutionBaseInfoVoList = new ArrayList<>();
                            }

                            for (SolutionBaseInfoVo solutionItem : solutionBaseInfoVoList) {
                                AladdinProgramInfoVo solutionInfoVo = new AladdinProgramInfoVo();
                                solutionInfoVo.setId(solutionItem.getSolutionId());

                                // 方案名称
                                solutionInfoVo.setName(solutionItem.getSolutionName());
                                solutionInfoVo.setPic(solutionItem.getHeadImgURL());
                                solutionInfoVo.setFurnitureCount(solutionItem.getItemCount());
                                solutionInfoVo.setSeriesStr(solutionSketchItem.getSeriesName());
                                solutionInfoVo.setType(solutionItem.getDecorationType());

                                solutionAvailableList.add(solutionInfoVo);
                            }
                        }
                    }

                    houseTypeInfoVo.setSolutionAvailableList(solutionAvailableList);
                    houseTypeInfoVo.setSolutionAvailableCount(solutionAvailableList.size());

                    buildingHouseList.add(houseTypeInfoVo);
                }

                buildingInfo.setBuildingHouseList(buildingHouseList);
                buildingInfo.setBuildingHouseCount(buildingHouseList.size());

                masterOrderInfoVo.setBuildingInfo(buildingInfo);
                return true;
            }

            @Override
            public String identity() {
                return "getBuildingInfoVo";
            }
        };
    }

    // 组装房产信息
    private IdentityTaskAction<Object> getHouseInfoVo(AppMasterOrderDetailDto masterOrderResultDto, AladdinOrderResultDto masterOrderInfoVo) {
        return new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                AladdinAdviserInfoVo adviserInfo = new AladdinAdviserInfoVo();

                // 房产信息
                HousePropertyInfoResultDto housePropertyInfoResultDto = masterOrderResultDto.getHouseInfo();

                if (null != housePropertyInfoResultDto) {
                    // 置家顾问信息
                    adviserInfo.setMobile(housePropertyInfoResultDto.getAdviserMobile());
                    adviserInfo.setUserName(housePropertyInfoResultDto.getAdviserName());

                    // 封装房产信息
                    HouseInfoResponseVo houseInfo = new HouseInfoResponseVo();
//
                    houseInfo.setHouseTypeName(housePropertyInfoResultDto.getLayoutName());
                    houseInfo.setHousingNum(housePropertyInfoResultDto.getHousingNum());
                    houseInfo.setId(housePropertyInfoResultDto.getCustomerHouseId());
                    houseInfo.setLayoutId(housePropertyInfoResultDto.getLayoutId());
                    houseInfo.setLayoutBalcony(housePropertyInfoResultDto.getLayoutBalcony());
                    houseInfo.setLayoutCloak(housePropertyInfoResultDto.getLayoutCloak());
                    houseInfo.setLayoutKitchen(housePropertyInfoResultDto.getLayoutKitchen());
                    houseInfo.setLayoutLiving(housePropertyInfoResultDto.getLayoutLiving());
                    houseInfo.setLayoutRoom(housePropertyInfoResultDto.getLayoutRoom());
                    houseInfo.setLayoutStorage(housePropertyInfoResultDto.getLayoutStorage());
                    houseInfo.setLayoutToliet(housePropertyInfoResultDto.getLayoutToilet());
                    houseInfo.setRoomNum(housePropertyInfoResultDto.getRoomNum());
                    houseInfo.setUnitNum(housePropertyInfoResultDto.getUnitNum());
                    houseInfo.setHouseProjectId(housePropertyInfoResultDto.getBuildingId());
                    houseInfo.setHouseProjectName(housePropertyInfoResultDto.getBuildingName());
                    houseInfo.setHouseTypeId(housePropertyInfoResultDto.getLayoutId());
                    houseInfo.setZoneId(housePropertyInfoResultDto.getZoneId());
                    houseInfo.setPartitionName(housePropertyInfoResultDto.getPartitionName());
                    houseInfo.setCreateTime(housePropertyInfoResultDto.getCreateTime());
                    houseInfo.setBuildingInfo(housePropertyInfoResultDto.getBuildingInfo());
                    if (!StringUtils.isEmpty(housePropertyInfoResultDto.getArea())) {
                        //兼容非结构化数据，面积有可能字符串
                        try {
                            houseInfo.setSize(housePropertyInfoResultDto.getArea());
                        } catch (Exception e) {
                        }
                    }

                    // 项目id
                    Integer buildingId = housePropertyInfoResultDto.getBuildingId();
                    ProjectResponse projectResponse = homeCardBossProxy.queryBuildingDetail(buildingId);
                    if (null != projectResponse) {
                        if (null != projectResponse.getFidDistrict()) {
                            AreaInfoDto areaInfoDto = areaService.getAreaInfo(Long.valueOf(projectResponse.getFidDistrict()));
                            if (null != areaInfoDto) {
                                houseInfo.setAreaId(areaInfoDto.getAreaId());
                                houseInfo.setAreaName(areaInfoDto.getAreaName());
                                houseInfo.setCityId(areaInfoDto.getCityId());
                                houseInfo.setCityName(areaInfoDto.getCityName());
                                houseInfo.setProvinceId(areaInfoDto.getProvinceId());
                                houseInfo.setProvinceName(areaInfoDto.getProvinceName());
                            }
                        }

                    }

                    masterOrderInfoVo.setHouseInfo(houseInfo);
                    masterOrderInfoVo.setAdviserInfo(adviserInfo);
                }
                return true;
            }

            @Override
            public String identity() {
                return "getHouseInfoVo";
            }
        };
    }

    private IdentityTaskAction<Object> getSubOrderInfo(AppMasterOrderDetailDto masterOrderResultDto, AladdinOrderResultDto masterOrderInfoVo) {
        return new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                AladdinHardOrderInfoVo hardOrderInfo = new AladdinHardOrderInfoVo();

                AladdinSoftOrderInfoVo softOrderInfo = new AladdinSoftOrderInfoVo();

                // 代客下单
                List<AppValetOrderInfoSoftDetailVo> valetSoftOrderInfo = masterOrderResultDto.getValetSoftOrderInfo();

                // 增减项信息
                AladdinIncrementItemVo incrementResultDto = masterOrderResultDto.getIncrementResultDto();

                // 订单号
                Integer orderNum = masterOrderResultDto.getOrderNum();
                Integer orderStatus = masterOrderResultDto.getMasterOrderInfo().getOrderStatus();
                Integer softOrderStatus = null;
                Integer hardOrderStatus = null;

                if (!MasterOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus().equals(orderStatus)) {
                    Integer productStatus = ProductStatusEnum.NEED_PAYING.getCode();
                    // 签约阶段、待交付
                    if (MasterOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus().equals(orderStatus)
                            || MasterOrderStatusEnum.ORDER_STATUS_PRE_DELIVERY.getStatus().equals(orderStatus)) {
                        productStatus = ProductStatusEnum.NEED_PAYING.getCode();
                        softOrderStatus = SoftOrderStatusEnum.SOFT_ORDER_PRE_PAY.getStatus();
                        hardOrderStatus = HardOrderStatusEnum.HARD_ORDER_PRE_PAY.getStatus();
                    }
                    // 已完成
                    if (MasterOrderStatusEnum.ORDER_STATUS_COMPLETED.getStatus().equals(orderStatus)) {
                        productStatus = ProductStatusEnum.COMPLETE_DELIVERY.getCode();
                        softOrderStatus = SoftOrderStatusEnum.SOFT_ORDER_COMPLETED.getStatus();
                        hardOrderStatus = HardOrderStatusEnum.HARD_ORDER_COMPLETED.getStatus();
                    }
                    // 已取消
                    if (MasterOrderStatusEnum.ORDER_STATUS_CANCELED.getStatus().equals(orderStatus)) {
                        productStatus = ProductStatusEnum.CANCELED.getCode();
                        softOrderStatus = SoftOrderStatusEnum.SOFT_ORDER_CANCLE.getStatus();
                        hardOrderStatus = HardOrderStatusEnum.HARD_ORDER_CANCLE.getStatus();
                    }


                    if (!CollectionUtils.isEmpty(valetSoftOrderInfo)) {
                        for (AppValetOrderInfoSoftDetailVo vo : valetSoftOrderInfo) {
                            vo.setProductStatus(productStatus);
                            vo.setProductStatusStr(ProductStatusEnum.getDescription(productStatus));
                        }
                    }

                    if (null != incrementResultDto
                            && !CollectionUtils.isEmpty(incrementResultDto.getSoftFitmentInfo())) {
                        for (AppValetOrderInfoSoftDetailVo vo : incrementResultDto.getSoftFitmentInfo()) {
                            vo.setProductStatus(productStatus);
                            vo.setProductStatusStr(ProductStatusEnum.getDescription(productStatus));
                        }
                    }
                }

                if (MasterOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus().equals(orderStatus)) {
                    Map<String, ProductStatusListVo> statusMap = new HashMap<>();
                    //dms查询商品状态 update by 2018/6/5
                    List<ProductStatusListVo> productStatusListVos = deliveryProxy.queryProductStatus(orderNum);

                    ProductStatusListVo statusListVo = null;
                    if (!CollectionUtils.isEmpty(productStatusListVos)) {
                        for (ProductStatusListVo vo : productStatusListVos) {
                            statusMap.put(vo.getSuperKey(), vo);
                        }

                        if (!CollectionUtils.isEmpty(valetSoftOrderInfo)) {
                            for (AppValetOrderInfoSoftDetailVo vo : valetSoftOrderInfo) {
                                statusListVo = statusMap.get(vo.getSuperKey());
                                if (null != statusListVo) {
                                    vo.setProductStatus(statusListVo.getStatus());
                                    vo.setProductStatusStr(statusListVo.getStatusName());
                                }

                            }
                        }

                        if (null != incrementResultDto
                                && !CollectionUtils.isEmpty(incrementResultDto.getSoftFitmentInfo())) {
                            for (AppValetOrderInfoSoftDetailVo vo : incrementResultDto.getSoftFitmentInfo()) {
                                statusListVo = statusMap.get(vo.getSuperKey());
                                if (null != statusListVo) {
                                    vo.setProductStatus(statusListVo.getStatus());
                                    vo.setProductStatusStr(statusListVo.getStatusName());
                                }
                            }
                        }
                        // 取出最小的商品状态作为软装状态

                        Optional<ProductStatusListVo> productStatusListVo = productStatusListVos.stream().min(Comparator.comparing(ProductStatusListVo::getStatus));
                        if (productStatusListVo.isPresent()) {
                            softOrderStatus = productStatusListVo.get().getStatus() + 1;
                        }
                    }

                    // 查询硬装状态
                    if (null != hardOrderInfo) {
                        ProjectDetailProgressInfoVo progressInfoVo = hbmsProxy.queryHbmsProgress(orderNum);
                        if (null != progressInfoVo) {
                            HardStatusReflectEnum hardStatusReflectEnum = HardStatusReflectEnum.getOrderHardStatus(progressInfoVo.getListStatus());
                            hardOrderStatus = hardStatusReflectEnum.getStatus();
                        }
                    }
                }

                // 软装子订单信息
                AppSoftOrderInfoDto appSoftOrderInfoDto = masterOrderResultDto.getSoftOrderInfo();
                if (null != appSoftOrderInfoDto) {
                    softOrderInfo.setId(appSoftOrderInfoDto.getSoftOrderId());
                    softOrderInfo.setSoftOrderStatus(softOrderStatus);
                    softOrderInfo.setSoftOrderStatusStr(softOrderStatus == null ? null : SoftOrderStatusEnum.getMsg(softOrderStatus));
                }

                // 硬装子订单信息
                AppHardOrderInfoDto appHardOrderInfoDto = masterOrderResultDto.getHardOrderInfo();
                if (null != appHardOrderInfoDto) {
                    hardOrderInfo.setId(appHardOrderInfoDto.getHardOrderId());
                    hardOrderInfo.setHardOrderStatus(hardOrderStatus);
                    hardOrderInfo.setHardOrderStatusStr(hardOrderStatus == null ? null : HardOrderStatusEnum.getMsg(hardOrderStatus));
                    // 调用hbms接口查询硬装施工日期
                    GetBeginTimeByOrderIdResultDto getBeginTimeByOrderIdResultDto = hbmsProxy.getBeginTimeByOrderId(orderNum);
                    if (null != getBeginTimeByOrderIdResultDto) {
                        hardOrderInfo.setCommenceTime(getBeginTimeByOrderIdResultDto.getBeginTime());
                    }
                }

                masterOrderInfoVo.setValetSoftOrderInfo(valetSoftOrderInfo);
                masterOrderInfoVo.setIncrementResultDto(incrementResultDto);
                masterOrderInfoVo.setHardOrderInfo(hardOrderInfo);
                masterOrderInfoVo.setSoftOrderInfo(softOrderInfo);
                return true;
            }

            @Override
            public String identity() {
                return "getSubOrderInfo";
            }
        };
    }

    /**
     * 查询订单基本信息
     *
     * @param appMasterOrderDetailDto
     * @return
     */
    private IdentityTaskAction getOrderBaseInfo(AppMasterOrderDetailDto appMasterOrderDetailDto, AladdinOrderResultDto masterOrderInfoVo, AladdinOrderBaseInfoVo orderInfo) {
        return new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                AppMasterOrderInfoDto appMasterOrderInfoDto = appMasterOrderDetailDto.getMasterOrderInfo();

                if (null != appMasterOrderInfoDto) {
                    orderInfo.setContractAmount(appMasterOrderInfoDto.getContractAmount());
                    orderInfo.setCreateTime(appMasterOrderInfoDto.getOrderTime());
                    orderInfo.setOrderStatus(appMasterOrderInfoDto.getOrderStatus());
                    orderInfo.setOrderStatusStr(appMasterOrderInfoDto.getOrderStatusName());
                    orderInfo.setOrderType(appMasterOrderInfoDto.getOrderType());
                    orderInfo.setOrderTypeStr(appMasterOrderInfoDto.getOrderTypeName());
                    orderInfo.setOrderSaleType(appMasterOrderInfoDto.getOrderSaleType());
                    orderInfo.setBuildingId(appMasterOrderInfoDto.getBuildingId());
                    orderInfo.setCompanyId(appMasterOrderInfoDto.getCompanyId());
                    orderInfo.setPreferAmount(appMasterOrderInfoDto.getPreferAmount());
                    orderInfo.setDiscountAmount(appMasterOrderInfoDto.getDiscountAmount());
                    orderInfo.setSource(appMasterOrderInfoDto.getSource());
                    orderInfo.setExpectTime(appMasterOrderInfoDto.getExpectTime());
                    if (MasterOrderStatusEnum.ORDER_STATUS_COMPLETED.getStatus().equals(appMasterOrderInfoDto.getOrderStatus())) {
                        orderInfo.setCompleteTime(com.ihomefnt.common.util.DateUtils.dateToString(appMasterOrderInfoDto.getUpdateTime()));
                    }

                    // 订单等级
                    orderInfo.setGradeId(appMasterOrderInfoDto.getGradeId());
                    orderInfo.setGradeName(appMasterOrderInfoDto.getGradeName());
                    orderInfo.setRightsVersion(appMasterOrderInfoDto.getRightsVersion());
                    if (appMasterOrderInfoDto.getRightsVersion() != null && appMasterOrderInfoDto.getRightsVersion() == 4) {
                        orderInfo.setGradeId(-1);
                    }

                    AppSoftOrderInfoDto appSoftOrderInfoDto = appMasterOrderDetailDto.getSoftOrderInfo();
                    if (null != appSoftOrderInfoDto) {
                        // 是否自由搭配
                        if (null != appSoftOrderInfoDto.getSuitType()) {
                            orderInfo.setIsAutoMatch(0 == appSoftOrderInfoDto.getSuitType());
                        }
                    }
                }

                masterOrderInfoVo.setOrderInfo(orderInfo);

                return true;
            }

            @Override
            public String identity() {
                return "getOrderBaseInfo";
            }
        };
    }

    /**
     * 全品家订单详情
     *
     * @param request
     * @return
     */
    @Override
    public FamilyOrderDetailResponse queryFamilyOrderDetail(ProgramOrderDetailRequest request) {
        Map<String, Object> taskData = this.concurrentQueryFamilyOrderDetailNeedData(request.getOrderId());
        FamilyOrderDetailResponse familyOrderDetail = new FamilyOrderDetailResponse();
        AladdinOrderResultDto aladdinOrderResponseVo = (AladdinOrderResultDto) taskData.get(ConcurrentTaskEnum.QUERY_ALLPRODUCT_ORDER_DETAIL.name());
        FamilyOrderDetailResponse.UserInfo userInfo = new FamilyOrderDetailResponse.UserInfo();

        if (aladdinOrderResponseVo != null && aladdinOrderResponseVo.getOrderInfo() != null) {
            AladdinUserInfoVo aladdinUserInfo = aladdinOrderResponseVo.getUserInfo();
            if (aladdinUserInfo != null) {
                userInfo.setGradeId(aladdinOrderResponseVo.getOrderInfo().getGradeId());
                userInfo.setRightsVersion(aladdinOrderResponseVo.getOrderInfo().getRightsVersion());
                userInfo.setGradeName(aladdinOrderResponseVo.getOrderInfo().getGradeName());
                userInfo.setUserId(aladdinUserInfo.getId());
                userInfo.setUserName(aladdinUserInfo.getUserName());
                if (StringUtils.isNotBlank(aladdinUserInfo.getMobile())) {
                    userInfo.setMobile(aladdinUserInfo.getMobile());
                    userInfo.setHideMobile(aladdinUserInfo.getMobile().replaceAll(
                            ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
                }
            }

            // 房产信息
            HouseInfoResponseVo houseInfo = aladdinOrderResponseVo.getHouseInfo();
            if (houseInfo != null) {
                FamilyOrderDetailResponse.HouseInfo orderHouseInfo = new FamilyOrderDetailResponse.HouseInfo();


                HouseInfo houseInfoResponse = homeBuildingService.setHouseInfoStandard(houseInfo);

                // 项目名称+几+栋+几+室
                StringBuffer maintainAddress = new StringBuffer("");
                if (StringUtils.isNotBlank(houseInfo.getHouseProjectName())) {
                    orderHouseInfo.setBuildingName(houseInfoResponse.getBuildingName());
                    maintainAddress.append(houseInfoResponse.getBuildingName());
                }
                if (StringUtils.isNotBlank(houseInfo.getHousingNum())) {
                    maintainAddress.append(houseInfo.getHousingNum()).append("栋");
                }
                if (StringUtils.isNotBlank(houseInfo.getUnitNum())) {
                    maintainAddress.append(houseInfo.getUnitNum()).append("单元");
                }
                if (StringUtils.isNotBlank(houseInfo.getRoomNum())) {
                    maintainAddress.append(houseInfo.getRoomNum()).append("室");
                }
                orderHouseInfo.setMaintainAddress(maintainAddress.toString());
                orderHouseInfo.setBuildingAddress(houseInfoResponse.getBuildingAddress());
                orderHouseInfo.setHouseArea(houseInfoResponse.getSize());
                orderHouseInfo.setHouseName(houseInfoResponse.getHouseTypeName());
                orderHouseInfo.setHousePattern(houseInfoResponse.getHousePattern());
                orderHouseInfo.setHouseFullName(houseInfoResponse.getUnitRoomNo());
                orderHouseInfo.setBuildingId(houseInfo.getHouseProjectId());
                orderHouseInfo.setHouseId(houseInfo.getId());
                orderHouseInfo.setHouseTypeId(houseInfo.getHouseTypeId());
                familyOrderDetail.setHouseInfoResponseVo(orderHouseInfo);
            }
            FamilyOrderDetailResponse.OrderInfo orderInfo = new FamilyOrderDetailResponse.OrderInfo();

            // 订单信息
            AladdinOrderBaseInfoVo aladdinOrderInfo = aladdinOrderResponseVo.getOrderInfo();

            if (aladdinOrderResponseVo.getOldOrder() != null) {
                orderInfo.setOldFlag(aladdinOrderResponseVo.getOldOrder() == 0);
            }

            orderInfo.setOrderId(aladdinOrderInfo.getId());
            Integer orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_HARDSOFT;// 默认11（套装、硬装+软装）
            // orderSaleType售卖类型：0：软装+硬装，1：软装 isAutoMatch 是否自由搭配
            if (aladdinOrderInfo.getOrderSaleType() != null) {
                if (ProductProgramPraise.HARD_STANDARD_ALL.equals(aladdinOrderInfo.getOrderSaleType())) {
                    // 硬装+软装
                    if (aladdinOrderInfo.getIsAutoMatch() == null) {

                    } else if (aladdinOrderInfo.getIsAutoMatch()) {
                        // 自由搭配
                        orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_HARDSOFT;
                    } else {
                        // 套装
                        orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_HARDSOFT;
                    }
                } else if (ProductProgramPraise.HARD_STANDARD_SOFT.equals(aladdinOrderInfo.getOrderSaleType())) {
                    // 纯软装
                    if (aladdinOrderInfo.getIsAutoMatch() == null) {

                    } else if (aladdinOrderInfo.getIsAutoMatch()) {
                        // 自由搭配
                        orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_SOFT;
                    } else {
                        // 套装
                        orderType = ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_SOFT;
                    }
                }
            }
            orderInfo.setOrderType(orderType); // 订单类型
            // 订单状态扭转
            if (aladdinOrderInfo.getOrderStatus() != null) {
                orderInfo.setState(homeBuildingService.getOrderStatus(aladdinOrderInfo.getOrderStatus()));
                FamilyOrderDetailResponse.TransformInfo transformInfo = getTransformInfo(orderInfo.getState());
                familyOrderDetail.setTransformInfo(transformInfo);
            }
            FamilyOrderPayResponse familyOrderPayResponse = (FamilyOrderPayResponse) taskData.get(ConcurrentTaskEnum.QUERY_PAY_BASEINFO.name());
            orderInfo.setCreateTime(houseInfo != null ? houseInfo.getCreateTime() : null);//房产创建时间
            AladdinDealInfoVo transactionInfo = aladdinOrderResponseVo.getTransactionInfo();
            orderInfo.setUpdateTime(transactionInfo.getDstTime());
            orderInfo.setPaidAmount(familyOrderPayResponse.getPaidAmount());
            orderInfo.setSource(aladdinOrderInfo.getSource());


            // 已选方案信息
            AladdinProgramInfoVo solutionSelectedInfo = aladdinOrderResponseVo.getSolutionSelectedInfo();
            FamilyOrderDetailResponse.SolutionInfo solutionInfo = new FamilyOrderDetailResponse.SolutionInfo();
            if (solutionSelectedInfo != null) {
                solutionInfo.setSolutionId(solutionSelectedInfo.getId());
                if (StringUtils.isNotBlank(solutionSelectedInfo.getPic())) {
                    solutionInfo.setPictureUrl(AliImageUtil.imageCompress(solutionSelectedInfo.getPic(), 2, request.getWidth() == null ? 750 : request.getWidth(), ImageConstant.SIZE_MIDDLE));
                }

            }

            //其他优惠
            solutionInfo.setOtherDisAmount(familyOrderPayResponse.getOtherDisAmount());
            //订单总价(款项优化后)
            solutionInfo.setFinalOrderPrice(familyOrderPayResponse.getFinalOrderPrice().getValue());
            if (solutionInfo.getFinalOrderPrice() != null && orderInfo.getPaidAmount() != null && aladdinOrderInfo.getOrderStatus() != null && ProductProgramPraise.ALADDIN_ORDER_STATUS_SIGN.equals(orderInfo.getState())
                    && solutionInfo.getFinalOrderPrice().compareTo(orderInfo.getPaidAmount()) <= 0) {
                familyOrderDetail.getTransformInfo().setPayEntry(0);
            }
            //剩余应付(款项优化后)
            solutionInfo.setNewRestAmount(familyOrderPayResponse.getNewRestAmount());
            // 方案原价
            solutionInfo.setOriginalPrice(familyOrderPayResponse.getSolutionAmount());
            //艾升级优惠
            solutionInfo.setUpgradeDisAmount(familyOrderPayResponse.getRightAmount());
            //其它优惠
            solutionInfo.setOtherDisAmount(familyOrderPayResponse.getOtherDisAmount());

            // 订单退款信息
            AladdinRefundInfoVo aladdinRefundInfoVo = aladdinOrderResponseVo.getRefundInfo();
            if (aladdinRefundInfoVo != null) {
                familyOrderDetail.setOrderRefundInfo(setRefundInfo(aladdinRefundInfoVo));
            }

            if (orderInfo.getState() != null && orderInfo.getState() > ProductProgramPraise.ALADDIN_ORDER_STATUS_SIGN) {
                List<FamilyOrderDetailResponse.Node> noteList = getDmsNodeList(request.getAppVersion(), request.getOrderId(), orderInfo);

                familyOrderDetail.setDeliveryNode(noteList);
            }
            familyOrderDetail.setSolutionInfo(solutionInfo);
            familyOrderDetail.setOrderInfo(orderInfo);


            familyOrderDetail.setUserInfo(userInfo);
        }

        return familyOrderDetail;
    }


    private Map<String, Object> concurrentQueryFamilyOrderDetailNeedData(Integer orderId) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        //区分新老用户
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return queryAllProductOrderDetailById(orderId, false);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ALLPRODUCT_ORDER_DETAIL.name();
            }
        });

        // 查询用户购物车
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return queryPayBaseInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_PAY_BASEINFO.name();
            }
        });
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 转化动作入口信息
     *
     * @param state
     * @return
     */
    private FamilyOrderDetailResponse.TransformInfo getTransformInfo(Integer state) {
        FamilyOrderDetailResponse.TransformInfo transformInfo = new FamilyOrderDetailResponse.TransformInfo();
        switch (state) {
            case 1://接触阶段
                transformInfo.setDownEntry(1);
                break;
            case 4://签约阶段
                transformInfo.setPayEntry(1);
                break;
        }
        return transformInfo;
    }

    /**
     * 获取交付节点信息
     *
     * @param orderId
     * @param orderInfo
     * @return
     */
    private List<FamilyOrderDetailResponse.Node> getDmsNodeList(String appVersion, Integer orderId, FamilyOrderDetailResponse.OrderInfo orderInfo) {
        List<FamilyOrderDetailResponse.Node> noteList = new ArrayList();
        Map<String, Object> dataMap = concurrentQueryDeliveryInfo(appVersion, orderId);
        DeliverySimpleInfoDto simpleDeliveryInfo = (DeliverySimpleInfoDto) dataMap.get(ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name());
        HardDetailVo hardDetail = (HardDetailVo) dataMap.get(ConcurrentTaskEnum.QUERY_HARD_DETAIL.name());
        AppOrderBaseInfoResponseVo masterOrderInfo = (AppOrderBaseInfoResponseVo) dataMap.get(ConcurrentTaskEnum.QUERY_MASTER_ORDER_BASE_INFO.name());
        if (simpleDeliveryInfo == null || simpleDeliveryInfo.getDeliverStatus() == null)
            return null;
        if (ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_HARDSOFT.equals(orderInfo.getOrderType())
                || ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_HARDSOFT.equals(orderInfo.getOrderType())) {//软+硬

            String actualBeginDate = null;
            if (hardDetail != null && hardDetail.getHardOrderDetailVo() != null) {
                actualBeginDate = hardDetail.getHardOrderDetailVo().getActualBeginDate();
            }
            if (simpleDeliveryInfo.getDeliverStatus() < ProductProgramPraise.IN_DELIVERY || (actualBeginDate == null && ProductProgramPraise.IN_DELIVERY.equals(simpleDeliveryInfo.getDeliverStatus()))) {//未开工
                if (StringUtils.isBlank(simpleDeliveryInfo.getPlanBeginDate())) {//未排期
                    Integer constructionPeriod = simpleDeliveryInfo.getConstructionPeriod() == null ? ProductProgramPraise.CONST_RUCTIONPERIOD_DEFAULT : simpleDeliveryInfo.getConstructionPeriod();
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.CONSTRUCTION_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_ONTHE_WAY,
                            "(工期预计" + constructionPeriod + "个工作日，正在为您排期)"));
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.LAY_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, null));
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.HARD_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, null));
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.FOST_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, null));
                } else {
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.CONSTRUCTION_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, null));
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.LAY_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_ONTHE_WAY, "(预计开工日期：" + simpleDeliveryInfo.getPlanBeginDate() + ")"));
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.HARD_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, "(准备开工)"));
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.FOST_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, "(软装备货中)"));
                }
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.COMPLETED_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, null));
            } else if (simpleDeliveryInfo.getDeliverStatus() < ProductProgramPraise.DELIVERY_COMPLETED || !getSoftDeliverEnd(masterOrderInfo)) {//硬装未竣工或软装未结束
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.CONSTRUCTION_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, null));
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.LAY_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, "(开工日期：" + actualBeginDate + ")"));
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.HARD_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, getHardDeliverComment(appVersion, hardDetail)));
                if (getSoftDeliverEnd(masterOrderInfo)) {
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.FOST_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, "(已经全部到货)"));
                } else {
                    Integer completeDeliveryCount = 0;
                    if (masterOrderInfo != null && masterOrderInfo.getCompleteDelivery() != null) {
                        completeDeliveryCount = masterOrderInfo.getCompleteDelivery();
                    }
                    String comment;
                    if (completeDeliveryCount != null && completeDeliveryCount == 0) {
                        comment = "(软装备货中)";
                    } else {
                        comment = "(已到货" + completeDeliveryCount + "件)";
                    }

                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.FOST_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_ONTHE_WAY, comment));
                }

                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.COMPLETED_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, null));

            } else if (simpleDeliveryInfo.getDeliverStatus() >= ProductProgramPraise.DELIVERY_COMPLETED && getSoftDeliverEnd(masterOrderInfo)) {
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.CONSTRUCTION_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, null));
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.LAY_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, "(开工日期：" + actualBeginDate + ")"));
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.HARD_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, getHardDeliverComment(appVersion, hardDetail)));
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.FOST_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, "(已全部到货)"));
                if (simpleDeliveryInfo.getOwnerCheckStatus() == null || simpleDeliveryInfo.getOwnerCheckStatus() < ProductProgramPraise.CHECK_PASS) {
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.COMPLETED_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, "(新家装修完成，请移步去验收吧)"));
                } else {
                    CommentsDto commentsDto = (CommentsDto) dataMap.get(ConcurrentTaskEnum.QUERY_ORDER_COMMENT_INFO.name());
                    if ((commentsDto == null || commentsDto.getOrderId() == null) ? false : true) {//已点评
                        noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.COMPLETED_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, "(艾佳为您提供极速报修服务)"));
                    } else {//未点评
                        noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.COMPLETED_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, "(新家装修完成，谈谈您的感受吧)"));
                    }
                }

            }

        } else {//纯软

            if (simpleDeliveryInfo.getDeliverStatus() < ProductProgramPraise.IN_DELIVERY) {//未开工
                if (StringUtils.isBlank(simpleDeliveryInfo.getPlanBeginDate())) {//未排期
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.CONSTRUCTION_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_ONTHE_WAY, "(正在为您排期)"));
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.FOST_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, null));
                } else {
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.CONSTRUCTION_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, null));
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.FOST_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, "(紧急采购中)"));
                }
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.COMPLETED_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, null));
            } else if (!getSoftDeliverEnd(masterOrderInfo)) {//软装未结束
                Integer completeDeliveryCount = 0;
                if (masterOrderInfo != null && masterOrderInfo.getCompleteDelivery() != null) {
                    completeDeliveryCount = masterOrderInfo.getCompleteDelivery();
                }
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.CONSTRUCTION_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, null));
                String comment;
                if (completeDeliveryCount != null && completeDeliveryCount == 0) {
                    comment = "(软装备货中)";
                } else {
                    comment = "(已到货" + completeDeliveryCount + "件)";
                }
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.FOST_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_ONTHE_WAY, comment));
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.COMPLETED_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, null));
            } else {
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.CONSTRUCTION_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, null));
                noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.FOST_PROCESS_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, "(已全部到货)"));
                if (simpleDeliveryInfo.getOwnerCheckStatus() == null || simpleDeliveryInfo.getOwnerCheckStatus() < ProductProgramPraise.CHECK_PASS) {
                    noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.COMPLETED_WORDS, ProductProgramPraise.NODE_STATUS_UN_START, "(新家装修完成，请移步去验收吧)"));
                } else {
                    CommentsDto commentsDto = (CommentsDto) dataMap.get(ConcurrentTaskEnum.QUERY_ORDER_COMMENT_INFO.name());
                    if ((commentsDto == null || commentsDto.getOrderId() == null) ? false : true) {//已点评
                        noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.COMPLETED_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, "(艾佳为您提供极速报修服务)"));
                    } else {//未点评
                        noteList.add(new FamilyOrderDetailResponse.Node(ProductProgramPraise.COMPLETED_WORDS, ProductProgramPraise.NODE_STATUS_COMPLETED, "(新家装修完成，谈谈您的感受吧)"));
                    }
                }
            }


        }
        return noteList;
    }

    /**
     * 软装是否结束
     *
     * @param masterOrderInfo
     * @return
     */
    private boolean getSoftDeliverEnd(AppOrderBaseInfoResponseVo masterOrderInfo) {
        if (masterOrderInfo == null) {
            return false;
        }
        if (masterOrderInfo.getCompleteDelivery() != null && masterOrderInfo.getCompleteDelivery() > 0) {
            if (masterOrderInfo.getCompleteDelivery().equals(masterOrderInfo.getTotalProductCount())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 硬装进度显示文字
     *
     * @param hardDetail
     * @return
     */
    private String getHardDeliverComment(String appVersion, HardDetailVo hardDetail) {
        String comment = "(开工交底)";
        if (hardDetail != null && hardDetail.getHardOrderDetailVo() != null) {
            if (VersionUtil.mustUpdate(appVersion, "5.4.5")) {
                if (hardDetail.getHardOrderDetailVo().getHardStatus() == 2) {
                    comment = "(水电阶段)";
                } else if (hardDetail.getHardOrderDetailVo().getHardStatus() == 3) {
                    comment = "(瓦木阶段)";
                } else if (hardDetail.getHardOrderDetailVo().getHardStatus() == 4) {
                    comment = "(竣工阶段)";
                } else if (hardDetail.getHardOrderDetailVo().getHardStatus() == 5) {
                    comment = "(完工日期";
                    if (hardDetail.getHardOrderDetailVo().getActualEndDate() != null) {
                        comment = comment + "：" + hardDetail.getHardOrderDetailVo().getActualEndDate();
                    }
                    comment = comment + ")";
                } else if (hardDetail.getHardOrderDetailVo().getHardStatus() == 7) {
                    comment = "(自施工阶段)";
                }
            } else {
                if (hardDetail.getHardOrderDetailVo().getHardStatus() == 2) {
                    comment = "(水电阶段)";
                } else if (hardDetail.getHardOrderDetailVo().getHardStatus() == 3) {
                    comment = "(自施工阶段)";
                } else if (hardDetail.getHardOrderDetailVo().getHardStatus() == 4) {
                    comment = "(瓦木阶段)";
                } else if (hardDetail.getHardOrderDetailVo().getHardStatus() == 5) {
                    comment = "(竣工阶段)";
                } else if (hardDetail.getHardOrderDetailVo().getHardStatus() == 6) {
                    comment = "(完工日期";
                    if (hardDetail.getHardOrderDetailVo().getActualEndDate() != null) {
                        comment = comment + "：" + hardDetail.getHardOrderDetailVo().getActualEndDate();
                    }
                    comment = comment + ")";
                }
            }
        }
        return comment;

    }

    @Autowired
    private CommentService commentService;


    private Map<String, Object> concurrentQueryDeliveryInfo(String appVersion, Integer orderId) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(4);

        //查询节点状态信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return deliveryProxy.getHardDetail(appVersion, orderId.toString());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_HARD_DETAIL.name();
            }
        });
        //查询大订单信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return productProgramOrderProxy.queryAppOrderBaseInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_MASTER_ORDER_BASE_INFO.name();
            }
        });
        //查询交付信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return dmsProxy.getSimpleDeliveryInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name();
            }
        });

        //查询点评信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return commentService.getComment(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_COMMENT_INFO.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);

    }

}
