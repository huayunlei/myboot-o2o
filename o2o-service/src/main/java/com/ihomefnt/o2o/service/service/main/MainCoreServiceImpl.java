package com.ihomefnt.o2o.service.service.main;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.o2o.constant.FamilyOrderStatusEnum;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;
import com.ihomefnt.o2o.intf.domain.lechange.dto.GetDeviceListResultVo;
import com.ihomefnt.o2o.intf.domain.main.dto.DeliverySimpleInfoDto;
import com.ihomefnt.o2o.intf.domain.main.vo.SolutionInfo;
import com.ihomefnt.o2o.intf.domain.main.vo.request.MainFrameRequest;
import com.ihomefnt.o2o.intf.domain.main.vo.response.MainPageNewResponse;
import com.ihomefnt.o2o.intf.domain.main.vo.vo.*;
import com.ihomefnt.o2o.intf.domain.order.vo.CheckIfCanDeliveryConfirmVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.ImageInfoDto;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.AppSolutionDesignResponseVo;
import com.ihomefnt.o2o.intf.domain.program.enums.DecorationTypeEnum;
import com.ihomefnt.o2o.intf.domain.program.enums.SolutionTypeEnum;
import com.ihomefnt.o2o.intf.domain.program.vo.response.SolutionEffectResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionEffectInfo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DraftSimpleRequest;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DraftSimpleRequestPage;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.SoftListResponse;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import com.ihomefnt.o2o.intf.manager.constant.home.MasterOrderStatusEnum;
import com.ihomefnt.o2o.intf.manager.constant.maincore.MainCoreConstants;
import com.ihomefnt.o2o.intf.manager.constant.personalneed.DesignTaskAppEnum;
import com.ihomefnt.o2o.intf.manager.constant.personalneed.DesignTaskSystemEnum;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.designdemand.PersonalNeedProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.lechange.HbmsProxy;
import com.ihomefnt.o2o.intf.proxy.main.DmsProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.paintscreen.UserScreenProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.service.designDemand.ProgramPersonalNeedService;
import com.ihomefnt.o2o.intf.service.main.MainCoreService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.o2o.service.proxy.dms.enums.DeliveryStatusEnum;
import com.ihomefnt.o2o.service.proxy.dms.enums.DmsProjectAppStatusEnums;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainCoreServiceImpl implements MainCoreService {
    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private OrderProxy orderProxy;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Autowired
    private HomeCardWcmProxy homeCardWcmProxy;

    @Autowired
    private DmsProxy dmsProxy;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PersonalNeedProxy personalNeedProxy;

    @Autowired
    private HbmsProxy hbmsProxy;

    @Autowired
    private UserScreenProxy userScreenProxy;

    @Autowired
    private ProgramPersonalNeedService programPersonalNeedService;

    @Autowired
    private ProductProgramOrderService productProgramOrderService;

    /**
     * 核心区头部信息
     */
    @NacosValue(value = "${main.core.head.info}", autoRefreshed = true)
    private String mainCoreHeadInfo;
    /**
     * 核心区内容部分
     */
    @NacosValue(value = "${main.core.content.info}", autoRefreshed = true)
    private String mainCoreContentInfo;
    /**
     * 核心区按钮部分
     */
    @NacosValue(value = "${main.core.action.info}", autoRefreshed = true)
    private String mainCoreActionInfo;
    /**
     * 核心区浮层
     */
    @NacosValue(value = "${main.core.float.info}", autoRefreshed = true)
    private String mainCoreFloatInfo;
    /**
     * 核心区右上角角标（contentType=3图片+角标）
     */
    @NacosValue(value = "${main.core.corner.right.top.info}", autoRefreshed = true)
    private String mainCoreCornerRightTopInfo;

    /**
     * 核心区大图默认宽高（contentType=3图片+角标）
     */
    @NacosValue(value = "${main.core.content.picture.default.height}", autoRefreshed = true)
    private Integer mainCoreContentPictureDefaultHeight;
    @NacosValue(value = "${main.core.content.picture.default.width}", autoRefreshed = true)
    private Integer mainCoreContentPictureDefaultWidth;

    /**
     * 各文案替换通配符
     */
    @NacosValue(value = "${order.id.replace.str}", autoRefreshed = true)
    private String orderIdReplaceStr;
    @NacosValue(value = "${delivery.diff.replace.str}", autoRefreshed = true)
    private String deliveryDiffReplaceStr;
    @NacosValue(value = "${camera.brand.replace.str}", autoRefreshed = true)
    private String cameraBrandReplaceStr;
    @NacosValue(value = "${camera.sn.replace.str}", autoRefreshed = true)
    private String cameraSnReplaceStr;
    @NacosValue(value = "${mokan.token.replace.str}", autoRefreshed = true)
    private String mokanTokenReplaceStr;
    @NacosValue(value = "${data.type.replace.str}", autoRefreshed = true)
    private String dataTypeReplaeStr;
    @NacosValue(value = "${program.id.replace.str}", autoRefreshed = true)
    private String programIdReplaceStr;
    @NacosValue(value = "${draft.profile.num.replace.str}", autoRefreshed = true)
    private String draftProfileNumReplaceStr;
    @NacosValue(value = "${delivery.start.date.replace.str}", autoRefreshed = true)
    private String deliveryStartDateReplaceStr;
    @NacosValue(value = "${delivery.days.replace.str}", autoRefreshed = true)
    private String deliveryDaysReplaceStr;
    @NacosValue(value = "${user.accessToken.replace.str}", autoRefreshed = true)
    private String userAccessTokenReplaceStr;

    /**
     * 核心区大图角标location枚举（contentType=3图片+角标）
     */
    @NacosValue(value = "${corner.marker.left.top.name}", autoRefreshed = true)
    private String cornerMarkerLeftTopName;
    @NacosValue(value = "${corner.marker.left.bottom.name}", autoRefreshed = true)
    private String cornerMarkerLeftBottomName;
    @NacosValue(value = "${corner.marker.right.top.name}", autoRefreshed = true)
    private String cornerMarkerRightTopName;
    @NacosValue(value = "${corner.marker.right.bottom.name}", autoRefreshed = true)
    private String cornerMarkerRightBottomName;

    /**
     * 核心区大图 跳方案详情 dataType参数枚举（contentType=3图片+角标）（order、draft、solution）
     */
    @NacosValue(value = "${program.detail.url.data.type.order}", autoRefreshed = true)
    private String programDetailUrlDataTypeOrder;
    @NacosValue(value = "${program.detail.url.data.type.draft}", autoRefreshed = true)
    private String programDetailUrlDataTypeDraft;
    @NacosValue(value = "${delivery.diff.after.button.copywriting}", autoRefreshed = true)
    private String deliveryDiffAfterButtonCopywriting;
    @NacosValue(value = "${program.detail.url.data.type.solution}", autoRefreshed = true)
    private String programDetailUrlDataTypeSolution;

    /**
     * 魔看品牌ID 2
     */
    @NacosValue(value = "${mokan.brand.id}", autoRefreshed = true)
    private Integer mokanBrandId;
    /**
     * 爱家贷专属方案id 3232
     */
    @NacosValue(value = "${loan.program.id}", autoRefreshed = true)
    private Integer loanProgramId;
    /**
     * 交付默认工期 75
     */
    @NacosValue(value = "${default.delivery.days}", autoRefreshed = true)
    private String defaultDeliveryDays;

    /**
     * 交付开始阶段文案配置
     * 距开发商交房还有{deliveryDiff}天
     * 已交房{deliveryDiff}天
     * 等待开发商交房
     * 预计{deliveryStartDate}开工，
     * 工期为{deliveryDays}个工作日
     */
    @NacosValue(value = "${delivery.diff.copywriting.before}", autoRefreshed = true)
    private String deliveryDiffCopywritingBefore;
    @NacosValue(value = "${delivery.diff.copywriting.after}", autoRefreshed = true)
    private String deliveryDiffCopywritingAfter;
    @NacosValue(value = "${delivery.diff.copywriting.unknow}", autoRefreshed = true)
    private String deliveryDiffCopywritingUnknow;
    @NacosValue(value = "${delivery.complete.sub.title.first}", autoRefreshed = true)
    private String deliveryCompleteSubTitleFirst;
    @NacosValue(value = "${delivery.complete.sub.title.second}", autoRefreshed = true)
    private String deliveryCompleteSubTitleSecond;
    @NacosValue(value = "${delivery.soft.complete.copywriting}", autoRefreshed = true)
    private String deliverySoftCompleteCopywriting;
    @NacosValue(value = "${delivery.all.complete.title}", autoRefreshed = true)
    private String deliveryAllCompleteTitle;

    /**
     * 商品下单的清单跳转地址
     */
    @NacosValue(value = "${dolly.order.picture.open.url}", autoRefreshed = true)
    private String dollyOrderPictureOpenUrl;

    /**
     * 纯软装查看施工计划跳转修改为查看全品家清单
     */
    @NacosValue(value = "${soft.delivery.plan.button.name}", autoRefreshed = true)
    private String softDeliveryPlanButtonName;
    @NacosValue(value = "${soft.delivery.plan.button.url}", autoRefreshed = true)
    private String softDeliveryPlanButtonUrl;
    @NacosValue(value = "${im.version.must.version}", autoRefreshed = true)
    private String imVersionMustVersion;

    @Override
    public MainPageNewResponse getMainCore(MainFrameRequest request) {
        // 兜底无房产
        MainPageNewResponse response = getMainCoreByName(MainCoreConstants.NO_ROOM);
        // 未登录，返回未登录形态1.1
        if (StringUtil.isNullOrEmpty(request.getAccessToken())) {
            return getMainCoreByName(MainCoreConstants.UN_SIGNED);
        } else if (request.getOrderId() == null) {
            // 无订单编号，返回无房产形态2.1
            return getMainCoreByName(MainCoreConstants.NO_ROOM);
        }

        Map<String, Object> dataMapForOrder = concurrentQueryOrderBaseAndOrderSolution(request.getOrderId());
        AppOrderBaseInfoResponseVo orderInfo = (AppOrderBaseInfoResponseVo) dataMapForOrder.get(ConcurrentTaskEnum.QUERY_MASTER_ORDER_BASE_INFO.name());

        // 无订单信息，返回无房产形态 2.1
        if (orderInfo == null || orderInfo.getOrderNum() == null || orderInfo.getOrderStatus() == null) {
            return replaceResponseOpenUrl(getMainCoreByName(MainCoreConstants.NO_ROOM), request);
        } else if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_CONTACT_STAGE.getStatus()) ||
                orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_CANCELED.getStatus())) {
            // 接触中、已取消 返回接触中状态3.1.1
            return replaceResponseOpenUrl(getMainCoreByName(MainCoreConstants.TOUCHING), request);
        }

        SolutionInfo solutionInfo = (SolutionInfo) dataMapForOrder.get(ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name());
        // 意向阶段
        if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_INTENTIONAL_PHASE.getStatus()) ||
                // 定金阶段
                orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_DEPOSIT_PHASE.getStatus()) ||
                // 爱家贷方案签约&签约中的未签约
                (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus()) &&
                        ((solutionInfo == null || solutionInfo.getPurchaseType().equals(2)) ||
                                FamilyOrderStatusEnum.ORDER_STATUS_UN_SIGNING.getStatus().equals(orderInfo.getOrderSubstatus()))
                )) {
            Map<String, Object> params = new HashMap<>();
            params.put("apartmentId", orderInfo.getLayoutId());
            params.put("orderNum", request.getOrderId());
            params.put("userId", request.getUserInfo() == null ? 0 : request.getUserInfo().getId());
            Map<String, Object> dataMapForSolution = concurrentQueryProgramAndDraftAndTask(params);
            SolutionEffectResponse solutionEffectResponse =
                    (SolutionEffectResponse) dataMapForSolution.get(ConcurrentTaskEnum.QUERY_SOLUTION_LIST.name());
            DraftSimpleRequestPage draftList =
                    (DraftSimpleRequestPage) dataMapForSolution.get(ConcurrentTaskEnum.QUERY_DRAFT_LIST.name());
            List<AppSolutionDesignResponseVo> solutionDesigns =
                    (List<AppSolutionDesignResponseVo>) dataMapForSolution.get(ConcurrentTaskEnum.QUERY_DESIGN_DEMOND_HISTORY_INFO.name());

            // 剔除爱家贷专用方案
            int availableSolutionCount = 0;
            if (solutionEffectResponse != null && !solutionEffectResponse.getSolutionEffectInfoList().isEmpty()) {
                int solutionRemoveKey = -1;
                for (int i = 0; i < solutionEffectResponse.getSolutionEffectInfoList().size(); i++) {
                    if (loanProgramId.equals(solutionEffectResponse.getSolutionEffectInfoList().get(i).getSolutionId())) {
                        solutionRemoveKey = i;
                        break;
                    }
                }
                if (solutionRemoveKey >= 0) {
                    solutionEffectResponse.getSolutionEffectInfoList().remove(solutionRemoveKey);
                }

                availableSolutionCount = solutionEffectResponse.getSolutionEffectInfoList().size();
            }
            if (!draftList.getOrderDraftSimpleList().isEmpty()) {
                // 有草稿3.2.4&3.3.1
                response = getMainCoreByName(MainCoreConstants.WATCH_PROGRAM);

                DraftSimpleRequest draftInfo = draftList.getOrderDraftSimpleList().get(0);
                response.getCoreOperationAreaInfo().getContentInfo().setPictureUrl(draftInfo.getUrl());

                // 头图跳转草稿详情
                response.getCoreOperationAreaInfo().getContentInfo().setPictureOpenUrl(transferProgramDetailUrl(
                        response.getCoreOperationAreaInfo().getContentInfo().getPictureOpenUrl(), programDetailUrlDataTypeDraft,
                        orderInfo.getOrderNum(), draftInfo.getSolutionId(), draftInfo.getDraftProfileNum()));

                // 方案图角标
                String rightTopCornerName;
                if (Constants.DRAFT_SIGN_STATUS_HAS_SIGN == draftInfo.getDraftSignStatus()) {
                    rightTopCornerName = MainCoreConstants.SIGN_PROGRAM_RIGHT_TOP_CORNER;
                } else if (Constants.DRAFT_SIGN_STATUS_NO_SIGN == draftInfo.getDraftSignStatus()) {
                    rightTopCornerName = MainCoreConstants.DRAFT_RIGHT_TOP_CORNER;
                } else if (Constants.DRAFT_SIGN_STATUS_HISTORY_SIGNED == draftInfo.getDraftSignStatus()) {
                    rightTopCornerName = MainCoreConstants.DRAFT_HISTORY_RIGHT_TOP_CORNER;
                } else if (Constants.DRAFT_SIGN_STATUS_LATEST == draftInfo.getDraftSignStatus()) {
                    rightTopCornerName = MainCoreConstants.DRAFT_RIGHT_TOP_CORNER;
                } else {
                    rightTopCornerName = MainCoreConstants.DEFAULT_RIGHT_TOP_CORNER;
                }

                if (availableSolutionCount > 0) {
                    for (SolutionEffectInfo solutionInfoTmp : solutionEffectResponse.getSolutionEffectInfoList()) {
                        if (solutionInfoTmp.getSolutionId().equals(draftInfo.getSolutionId())) {
                            draftInfo.setDecorationType(solutionInfoTmp.getDecorationType());
                        }
                    }
                }

                response.getCoreOperationAreaInfo().getContentInfo().setCornerMarkers(
                        packageCornerMarker(draftInfo.getTotalPrice().toString(), rightTopCornerName, draftInfo.getDecorationType()));

            } else if (availableSolutionCount > 0) {
                // 有方案3.2.4&3.3.1
                response = getMainCoreByName(MainCoreConstants.WATCH_PROGRAM);
                SolutionEffectInfo solutionEffectInfo = solutionEffectResponse.getSolutionEffectInfoList().get(0);
                for (SolutionEffectInfo solutionTmp : solutionEffectResponse.getSolutionEffectInfoList()) {
                    if (solutionTmp.getSolutionType() == 1) {
                        solutionEffectInfo = solutionTmp;
                        break;
                    }
                }
                if (!StringUtil.isNullOrEmpty(solutionEffectInfo.getHeadImage())) {
                    response.getCoreOperationAreaInfo().getContentInfo().setPictureUrl(solutionEffectInfo.getHeadImage());
                }
                // 方案图角标
                String rightTopCornerName;
                if (SolutionTypeEnum.CUSTOMIZED.getType().equals(solutionEffectInfo.getSolutionType())) {
                    rightTopCornerName = MainCoreConstants.CUSTOMIZED_PROGRAM_RIGHT_TOP_CORNER;
                } else if (SolutionTypeEnum.NEIGHBOR.getType().equals(solutionEffectInfo.getSolutionType()) ||
                        SolutionTypeEnum.LIKE.getType().equals(solutionEffectInfo.getSolutionType())) {
                    rightTopCornerName = MainCoreConstants.NEIGHBOR_PROGRAM_RIGHT_TOP_CORNER;
                } else {
                    rightTopCornerName = MainCoreConstants.DEFAULT_RIGHT_TOP_CORNER;
                }
                response.getCoreOperationAreaInfo().getContentInfo().setCornerMarkers(
                        packageCornerMarker(solutionEffectInfo.getSolutionPrice().toString(), rightTopCornerName,
                                solutionEffectInfo.getDecorationType()));
                // 方案头图跳转方案详情
                response.getCoreOperationAreaInfo().getContentInfo().setPictureOpenUrl(transferProgramDetailUrl(
                        response.getCoreOperationAreaInfo().getContentInfo().getPictureOpenUrl(), programDetailUrlDataTypeSolution,
                        orderInfo.getOrderNum(), solutionEffectInfo.getSolutionId(), null));
            } else if (CollectionUtils.isEmpty(solutionDesigns)) {
                // 无方案、无草稿、无设计需求3.2.1
                response = getMainCoreByName(MainCoreConstants.SUBMIT_DESIGN_DEMAND);
            } else {
                // 无方案、无草稿、有设计需求
                AppSolutionDesignResponseVo designingSolutionDesign = null;
                for (AppSolutionDesignResponseVo solutionDesign : solutionDesigns) {
                    // 获取设计中方案（加一个待分配状态，也显示催一催）
                    if (solutionDesign != null && solutionDesign.getTaskStatus() != null &&
                            (DesignTaskAppEnum.IN_DESIGN.getTaskStatus().equals(
                                    programPersonalNeedService.transferDesignStatus(solutionDesign.getTaskStatus())) ||
                                    DesignTaskSystemEnum.WAIT_DESIGN.getTaskStatus().equals(solutionDesign.getTaskStatus()))) {
                        designingSolutionDesign = solutionDesign;
                        break;
                    }
                }
                if (designingSolutionDesign != null) {
                    // 无方案、无草稿、有设计需求、有设计中3.2.2
                    response = getMainCoreByName(MainCoreConstants.DESIGN_DEMAND_DESIGNING);
                } else {
                    // 无方案、无草稿、有设计需求、无设计中3.2.3
                    response = getMainCoreByName(MainCoreConstants.SUBMIT_DESIGN_DEMAND_AGAIN);
                }

            }
        } else if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus())) {
            // 签约中 3.3.2（查询订单概要信息） 或者签约阶段，未结清款项
            if (FamilyOrderStatusEnum.ORDER_STATUS_SIGNING.getStatus().equals(orderInfo.getOrderSubstatus()) ||
                    1 == BigDecimal.ONE.compareTo(orderInfo.getFundProcess()==null?BigDecimal.ZERO:orderInfo.getFundProcess())) {
                response = getMainCoreByName(MainCoreConstants.SIGN_ING);
                if (!StringUtil.isNullOrEmpty(orderInfo.getSolutionUrl())) {
                    response.getCoreOperationAreaInfo().getContentInfo().setPictureUrl(orderInfo.getSolutionUrl());
                }
                // 头图跳转全品家清单(source=6为代客下商品订单，跳转单独的清单页面)
                if (orderInfo.getSourceType() != null && orderInfo.getSourceType() == 6) {
                    response.getCoreOperationAreaInfo().getContentInfo().setPictureOpenUrl(
                            dollyOrderPictureOpenUrl.replace(orderIdReplaceStr, orderInfo.getOrderNum().toString()));
                } else {
                    response.getCoreOperationAreaInfo().getContentInfo().setPictureOpenUrl(transferProgramDetailUrl(
                            response.getCoreOperationAreaInfo().getContentInfo().getPictureOpenUrl(), programDetailUrlDataTypeOrder,
                            orderInfo.getOrderNum(), orderInfo.getSolutionStyleId(), null));
                }

                // 方案图角标
                response.getCoreOperationAreaInfo().getContentInfo().setCornerMarkers(
                        packageCornerMarker(orderInfo.getOrderTotalAmount().toString(),
                                MainCoreConstants.SIGN_PROGRAM_RIGHT_TOP_CORNER, orderInfo.getOrderSaleType()));

            } else if (FamilyOrderStatusEnum.ORDER_STATUS_SIGNED.getStatus().equals(orderInfo.getOrderSubstatus())) {
                Map<String, Object> dataMapForDelivery = concurrentQueryDeliveryInfoAndDeliveryConfirm(request.getOrderId());
                DeliverySimpleInfoDto deliveryInfo =
                        (DeliverySimpleInfoDto) dataMapForDelivery.get(ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name());
                CheckIfCanDeliveryConfirmVo checkIfCanDeliveryConfirmVo =
                        (CheckIfCanDeliveryConfirmVo) dataMapForDelivery.get(ConcurrentTaskEnum.CHECK_IF_CAN_DELIVER_CONFIRM.name());

                // 签约成功 可确认开工 3.3.4
                if (Boolean.TRUE.equals(checkIfCanDeliveryConfirmVo.getCheckResult())) {
                    response = getMainCoreByName(MainCoreConstants.NEED_CONFIRM_START);
                } else if (deliveryInfo != null && !deliveryInfo.getConfirmFlag() && StringUtils.isNotBlank(deliveryInfo.getConfirmDateStr())) {
                    // 已确认开工 未进入交付--
                    response = getMainCoreByName(MainCoreConstants.HAS_CONFIRM_START);
                } else {
                    // 签约成功 不可确认开工3.3.3
                    response = getMainCoreByName(MainCoreConstants.CAN_NOT_CONFIRM_START);
                    if (orderInfo.getDeliverDiff() != null) {
                        if (orderInfo.getDeliverDiff() >= 0) {
                            response.getCoreOperationAreaInfo().getContentInfo().setTitle(
                                    deliveryDiffCopywritingBefore.replace(deliveryDiffReplaceStr, orderInfo.getDeliverDiff().toString()));
                        } else {
                            int diffDay = 0 - orderInfo.getDeliverDiff();
                            response.getCoreOperationAreaInfo().getContentInfo().setTitle(
                                    deliveryDiffCopywritingAfter.replace(deliveryDiffReplaceStr, String.valueOf(diffDay)));
                            response.getCoreOperationAreaInfo().getActionInfo().setButtonCopywriting(deliveryDiffAfterButtonCopywriting);
                        }
                    } else {
                        response.getCoreOperationAreaInfo().getContentInfo().setTitle(deliveryDiffCopywritingUnknow);
                    }
                }
            }
        } else if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_PRE_DELIVERY.getStatus()) ||
                orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus())) {
            // 待交付、交付中 待查询交付状态
            DeliverySimpleInfoDto deliveryInfo = dmsProxy.getSimpleDeliveryInfo(request.getOrderId());

            // 待排期兜底 3.4.1
            response = getMainCoreByNameNoPlanData(request.getAppVersion());
            // 未开工 未排期 3.4.1(获取失败默认待排期)
            if (deliveryInfo == null || (deliveryInfo.getDeliverStatus() <= DeliveryStatusEnum.DELIVER_READY.getCode() &&
                    StringUtils.isBlank(deliveryInfo.getPlanBeginDate()))) {
                response = response = getMainCoreByNameNoPlanData(request.getAppVersion());
            } else if (StringUtils.isNotBlank(deliveryInfo.getPlanBeginDate()) &&
                    deliveryInfo.getDeliverStatus() <= DeliveryStatusEnum.DELIVER_READY.getCode()) {
                // 已排期 未开工 3.4.2
                response = getMainCoreByName(MainCoreConstants.WAIT_CONSTRICTION);

                String subTitleFirst = deliveryInfo.getPlanBeginDate() == null ? "" :
                        deliveryCompleteSubTitleFirst.replace(deliveryStartDateReplaceStr, deliveryInfo.getPlanBeginDate());
                String subTitleSecond = deliveryCompleteSubTitleSecond.replace(deliveryDaysReplaceStr,
                        deliveryInfo.getConstructionPeriod() == null ? defaultDeliveryDays : deliveryInfo.getConstructionPeriod().toString());
                response.getCoreOperationAreaInfo().getContentInfo().setSubTitle(subTitleFirst + subTitleSecond);

                // 纯软装，把按钮换成查看全品家清单
                if (DecorationTypeEnum.SOFT.getType().equals(orderInfo.getOrderSaleType())) {
                    response.getCoreOperationAreaInfo().getActionInfo().setButtonCopywriting(softDeliveryPlanButtonName);
                    response.getCoreOperationAreaInfo().getActionInfo().setButtonOpenUrl(softDeliveryPlanButtonUrl);
                }


            } else if ((deliveryInfo.getDeliverStatus() >= DeliveryStatusEnum.CONSTRUCTING.getCode() &&
                    deliveryInfo.getDeliverStatus() <= DeliveryStatusEnum.INSTALL.getCode()) ||
                    (DeliveryStatusEnum.COMPLETE.getCode() == deliveryInfo.getDeliverStatus() &&
                            (2 != deliveryInfo.getMasterCheckStatus() && Boolean.FALSE.equals(deliveryInfo.getFastCheckApproval())))) {
                // 施工中 3.4.3 (施工中、软装安装阶段 或者 交付阶段但是艾师傅未验收)
                response = getMainCoreByName(MainCoreConstants.CONSTRICTING);
                setConstricting(response, orderInfo, deliveryInfo);

            } else if (DeliveryStatusEnum.COMPLETE.getCode() == deliveryInfo.getDeliverStatus() &&
                    (2 == deliveryInfo.getMasterCheckStatus() || Boolean.TRUE.equals(deliveryInfo.getFastCheckApproval())) &&
                    2 != deliveryInfo.getOwnerCheckStatus()) {
                // 已完工 待验收/验收不通过 (竣工阶段且艾师傅验收通过) 3.4.4
                response = getMainCoreByName(MainCoreConstants.NEED_ACCEPTANCE);
            } else if (DeliveryStatusEnum.WARRANT.getCode() <= deliveryInfo.getDeliverStatus()) {
                // 如果走快速验收，客户验收通过艾师傅验收不通过，全品家订单未进入质保，但是交付状态进入质保，给客人要显示质保
                response = transferCompleteOrder(request.getOrderId());
            }

            // 替换艾师傅手机号
            if (response.getCoreOperationAreaInfo().getHeadInfo().getCopywritingMiddle() != null) {
                if (deliveryInfo.getManagerMobile() != null) {
                    response.getCoreOperationAreaInfo().getHeadInfo().getCopywritingMiddle().setOpenUrl(
                            response.getCoreOperationAreaInfo().getHeadInfo().getCopywritingMiddle().getOpenUrl().
                                    replace("{managerMobile}", deliveryInfo.getManagerMobile()));
                } else {
                    response.getCoreOperationAreaInfo().getHeadInfo().setCopywritingMiddle(null);
                }
            }
        } else if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_COMPLETED.getStatus())) {
            response = transferCompleteOrder(request.getOrderId());
        }
        return replaceResponseOpenUrl(response, request);
    }

    MainPageNewResponse getMainCoreByNameNoPlanData(String appVersion) {
        // 待排期兜底 3.4.1
        if (VersionUtil.mustUpdate(appVersion, imVersionMustVersion)) {
            return getMainCoreByName(MainCoreConstants.NO_PLAN);
        } else {
            return getMainCoreByName(MainCoreConstants.NO_PLAN_NEW);
        }
    }

    private MainPageNewResponse transferCompleteOrder(Integer orderId) {
        MainPageNewResponse response;
        // 已完成，待判断 已点评=>维保 未点评=>点评
        CommentsDto commentsDto = commentService.getComment(orderId);
        // 未点评=>待点评 3.4.5
        if (commentsDto == null || commentsDto.getOrderId() == null) {
            response = getMainCoreByName(MainCoreConstants.NEED_COMMENT);
        } else {
            // 已点评 3.5.1
            response = getMainCoreByName(MainCoreConstants.ACCEPTANCED);
        }
        return response;
    }

    /**
     * 拼装方案图片跳转链接
     *
     * @param pictureUrl
     * @param dataType
     * @param orderNum
     * @param programId
     * @param draftProfileNum
     * @return
     */
    private String transferProgramDetailUrl(String pictureUrl, String dataType, Integer orderNum, Integer programId, Long draftProfileNum) {
        pictureUrl = pictureUrl.replace(dataTypeReplaeStr, dataType);
        pictureUrl = pictureUrl.replace(orderIdReplaceStr,
                orderNum == null ? "0" : orderNum.toString());
        pictureUrl = pictureUrl.replace(programIdReplaceStr,
                programId == null ? "0" : programId.toString());
        pictureUrl = pictureUrl.replace(draftProfileNumReplaceStr,
                draftProfileNum == null ? "0" : Long.toString(draftProfileNum));
        return pictureUrl;
    }

    /**
     * 根据名称获取nacos配置的预置信息
     *
     * @param name
     * @return
     */
    private MainPageNewResponse getMainCoreByName(String name) {
        CoreOperationAreaInfoVo coreOperationAreaInfo = new CoreOperationAreaInfoVo();
        List<HeadInfoVo> mainCoreHeadInfoList = JsonUtils.json2list(mainCoreHeadInfo, HeadInfoVo.class);
        for (HeadInfoVo headInfo : mainCoreHeadInfoList) {
            if (headInfo.getMainCoreName().equals(name)) {
                coreOperationAreaInfo.setHeadInfo(headInfo);
            }
        }

        List<ContentInfoVo> mainCoreContentInfoList = JsonUtils.json2list(mainCoreContentInfo, ContentInfoVo.class);
        for (ContentInfoVo contentInfo : mainCoreContentInfoList) {
            if (contentInfo.getMainCoreName().equals(name)) {
                coreOperationAreaInfo.setContentInfo(contentInfo);
            }
        }
        List<ActionInfoVo> mainCoreActionInfoList = JsonUtils.json2list(mainCoreActionInfo, ActionInfoVo.class);
        for (ActionInfoVo actionInfo : mainCoreActionInfoList) {
            if (actionInfo.getMainCoreName().equals(name)) {
                coreOperationAreaInfo.setActionInfo(actionInfo);
            }
        }

        List<ExtraInfoVo> mainCoreFloatInfoList = JsonUtils.json2list(mainCoreFloatInfo, ExtraInfoVo.class);
        for (ExtraInfoVo floatInfo : mainCoreFloatInfoList) {
            if (floatInfo.getMainCoreName().equals(name)) {
                coreOperationAreaInfo.setFloatInfo(floatInfo);
            }
        }

        MainPageNewResponse response = new MainPageNewResponse();
        response.setCoreOperationAreaInfo(coreOperationAreaInfo);

        return response;
    }

    /**
     * 配置交付中状态
     *
     * @param response
     * @param orderInfo
     * @param deliveryInfo
     */
    private void setConstricting(MainPageNewResponse response, AppOrderBaseInfoResponseVo orderInfo, DeliverySimpleInfoDto deliveryInfo) {
        // 预留显示预计完工时间
        response.getCoreOperationAreaInfo().getContentInfo().setSubTitle(null);

        // 硬装进度
        // 全品家软只显示软装，不显示硬装
        Boolean deliveryCompleted = false;
        if (DecorationTypeEnum.SOFT.getType().equals(orderInfo.getOrderSaleType())) {
            response.getCoreOperationAreaInfo().getContentInfo().getSubItems().remove(0);
            // 软装进度
            String buttonCopywriting = transferDeliverSoft(orderInfo);
            response.getCoreOperationAreaInfo().getContentInfo().getSubItems().get(0).setButtonCopywriting(buttonCopywriting);
            if (deliverySoftCompleteCopywriting.equals(buttonCopywriting)) {
                deliveryCompleted = true;
            }
        } else {
            response.getCoreOperationAreaInfo().getContentInfo().getSubItems().get(0).setButtonCopywriting(
                    transferDeliverHard(deliveryInfo));
            //软+硬
            String buttonCopywriting = transferDeliverSoft(orderInfo);
            response.getCoreOperationAreaInfo().getContentInfo().getSubItems().get(1).setButtonCopywriting(buttonCopywriting);

            if (deliverySoftCompleteCopywriting.equals(buttonCopywriting) &&
                    deliveryInfo.getProjectStatus() == DmsProjectAppStatusEnums.CLOSED.getCode()) {
                deliveryCompleted = true;
            }
        }

        if (Boolean.TRUE.equals(deliveryCompleted)) {
            response.getCoreOperationAreaInfo().getContentInfo().setTitle(deliveryAllCompleteTitle);
        }

        // 摄像头
        response.getCoreOperationAreaInfo().getContentInfo().setVideoInfo(
                transferVideoInfo(response, orderInfo));
    }

    private ExtraInfoVo transferVideoInfo(MainPageNewResponse response, AppOrderBaseInfoResponseVo orderInfo) {
        // 摄像头 如果没有配置，就不显示
        ExtraInfoVo videoInfo = response.getCoreOperationAreaInfo().getContentInfo().getVideoInfo();
        if (videoInfo == null) {
            return null;
        }

        List<GetDeviceListResultVo> cameraList = hbmsProxy.getDeviceByOrderId(orderInfo.getOrderNum().toString());

        // 摄像头 如果没有摄像头，就不显示
        if (CollectionUtils.isEmpty(cameraList)) {
            return null;
        }
        // 有摩看摄像头优先展示摩看摄像头，没有就取第一个
        GetDeviceListResultVo camera = null;
        for (GetDeviceListResultVo resultVo : cameraList) {
            if (camera == null) {
                camera = resultVo;
            }
            if (resultVo.getBrand() == mokanBrandId) {
                camera = resultVo;
                break;
            }
        }
        if (camera == null) {
            response.getCoreOperationAreaInfo().getContentInfo().setVideoInfo(null);
            return null;
        }
        String openUrl = videoInfo.getOpenUrl();
        openUrl = openUrl.replace(orderIdReplaceStr, orderInfo.getOrderNum().toString());
        openUrl = openUrl.replace(cameraBrandReplaceStr, String.valueOf(camera.getBrand()));
        openUrl = openUrl.replace(cameraSnReplaceStr, camera.getCameraSn() == null ? "" : camera.getCameraSn());
        openUrl = openUrl.replace(mokanTokenReplaceStr, camera.getAccessToken() == null ? "" : camera.getAccessToken());
        videoInfo.setOpenUrl(openUrl);
        return videoInfo;
    }

    /**
     * 组装交付节点核心操作区子模块硬装文案
     *
     * @param deliveryInfo
     * @return
     */
    @Override
    public String transferDeliverHard(DeliverySimpleInfoDto deliveryInfo) {
        //硬装进度
        String hardProgress;
        if (deliveryInfo == null || deliveryInfo.getProjectStatus() == null) {
            hardProgress = DmsProjectAppStatusEnums.INIT.getDescription();
        } else if (deliveryInfo.getProjectStatus() <= DmsProjectAppStatusEnums.START_CHECK.getCode()) {
            hardProgress = DmsProjectAppStatusEnums.START_CHECK.getDescription();
        } else {
            hardProgress = DmsProjectAppStatusEnums.getStatusDescription(deliveryInfo.getProjectStatus());
        }
        if (StringUtil.isNullOrEmpty(hardProgress)) {
            hardProgress = DmsProjectAppStatusEnums.INIT.getDescription();
        }


        return hardProgress;
    }

    /**
     * 组装交付节点核心操作区子模块软装文案
     *
     * @param orderInfo
     * @return
     */
    private String transferDeliverSoft(AppOrderBaseInfoResponseVo orderInfo) {

        SoftListResponse softListResponse = productProgramOrderService.getSkuListByOrderId(orderInfo.getOrderNum());
        //软装到货进度
        Integer softTotal = softListResponse.getSoftTotal();
        Integer softComplete = softListResponse.getSoftFinishNum();
        String softString = "软装备货中";
        if (softComplete != null && softComplete > 0 && softComplete.equals(softTotal)) {
            softString = deliverySoftCompleteCopywriting;
        } else if (softComplete != null && softComplete > 0) {
            softString = "已到货" + softComplete + "件";
        }

        return softString;
    }

    /**
     * 替换通配符&切图
     *
     * @param response
     * @param request
     * @return
     */
    private MainPageNewResponse replaceResponseOpenUrl(MainPageNewResponse response, MainFrameRequest request) {

        if (response.getCoreOperationAreaInfo().getHeadInfo().getCopywritingRight().getOpenUrl() != null) {
            response.getCoreOperationAreaInfo().getHeadInfo().getCopywritingRight().setOpenUrl(
                    response.getCoreOperationAreaInfo().getHeadInfo().getCopywritingRight().getOpenUrl().
                            replace(orderIdReplaceStr, request.getOrderId().toString()).replace(userAccessTokenReplaceStr, request.getAccessToken()));
        }

        if (response.getCoreOperationAreaInfo().getActionInfo() != null &&
                response.getCoreOperationAreaInfo().getActionInfo().getButtonOpenUrl() != null) {
            response.getCoreOperationAreaInfo().getActionInfo().setButtonOpenUrl(
                    response.getCoreOperationAreaInfo().getActionInfo().getButtonOpenUrl().
                            replace(orderIdReplaceStr, request.getOrderId().toString()).replace(userAccessTokenReplaceStr, request.getAccessToken()));
        }

        if (response.getCoreOperationAreaInfo().getActionInfo() != null &&
                response.getCoreOperationAreaInfo().getActionInfo().getSubButtonOpenUrl() != null) {
            response.getCoreOperationAreaInfo().getActionInfo().setSubButtonOpenUrl(
                    response.getCoreOperationAreaInfo().getActionInfo().getSubButtonOpenUrl().
                            replace(orderIdReplaceStr, request.getOrderId().toString()).replace(userAccessTokenReplaceStr, request.getAccessToken()));
        }

        if (response.getCoreOperationAreaInfo().getFloatInfo() != null &&
                response.getCoreOperationAreaInfo().getFloatInfo().getOpenUrl() != null) {
            response.getCoreOperationAreaInfo().getFloatInfo().setOpenUrl(
                    response.getCoreOperationAreaInfo().getFloatInfo().getOpenUrl().
                            replace(orderIdReplaceStr, request.getOrderId().toString()).replace(userAccessTokenReplaceStr, request.getAccessToken()));
        }

        if (CollectionUtils.isNotEmpty(response.getCoreOperationAreaInfo().getContentInfo().getSubItems())) {
            for (int i = 0; i < response.getCoreOperationAreaInfo().getContentInfo().getSubItems().size(); i++) {
                if (response.getCoreOperationAreaInfo().getContentInfo().getSubItems().get(i).getOpenUrl() != null) {
                    response.getCoreOperationAreaInfo().getContentInfo().getSubItems().get(i).setOpenUrl(
                            response.getCoreOperationAreaInfo().getContentInfo().getSubItems().get(i).getOpenUrl().replace(
                                    orderIdReplaceStr, request.getOrderId().toString()).replace(userAccessTokenReplaceStr, request.getAccessToken()));
                }
            }
        }
        return compressResponseImage(response, request);
    }

    /**
     * 当内容区类型为3时，对主图切图并赋值宽高
     *
     * @param response
     * @param request
     * @return
     */
    private MainPageNewResponse compressResponseImage(MainPageNewResponse response, MainFrameRequest request) {
        // 仅大图的时候需要切图
        if (response.getCoreOperationAreaInfo().getContentInfo().getContentType() != 3 || response.getCoreOperationAreaInfo().getContentInfo() == null ||
                response.getCoreOperationAreaInfo().getContentInfo().getPictureUrl() == null) {
            return response;
        }

        response.getCoreOperationAreaInfo().getContentInfo().setPictureUrl(
                AliImageUtil.imageCompress(response.getCoreOperationAreaInfo().getContentInfo().getPictureUrl(),
                        request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));

        if (response.getCoreOperationAreaInfo().getContentInfo().getPictureHeight() == null) {
            ImageInfoDto imageInfo = userScreenProxy.queryImageInfoByUrl(response.getCoreOperationAreaInfo().getContentInfo().getPictureUrl());
            if (imageInfo != null) {
                response.getCoreOperationAreaInfo().getContentInfo().setPictureHeight(imageInfo.getHeight());
                response.getCoreOperationAreaInfo().getContentInfo().setPictureWidth(imageInfo.getWidth());
            } else {
                response.getCoreOperationAreaInfo().getContentInfo().setPictureHeight(mainCoreContentPictureDefaultHeight);
                response.getCoreOperationAreaInfo().getContentInfo().setPictureWidth(mainCoreContentPictureDefaultWidth);
            }
        }
        return response;
    }

    /**
     * 组装方案图片角标
     *
     * @param amount             方案价格
     * @param rightTopCornerName 方案类型
     * @param decorationType     售卖形式
     * @return
     */
    private List<CornerMarkerVo> packageCornerMarker(String amount, String rightTopCornerName, Integer decorationType) {
        List<CornerMarkerVo> cornerMarkers = new ArrayList<>();
        if (amount != null) {
            cornerMarkers.add(new CornerMarkerVo(cornerMarkerRightBottomName, "方案总价：￥" + amount));
        }

        List<CornerMarkerVo> mainCoreCornerRightTopInfoList = JsonUtils.json2list(mainCoreCornerRightTopInfo, CornerMarkerVo.class);
        for (CornerMarkerVo cornerMarker : mainCoreCornerRightTopInfoList) {
            if (cornerMarker.getCornerMarkerName().equals(rightTopCornerName)) {
                cornerMarkers.add(cornerMarker);
            }
        }

        cornerMarkers.add(new CornerMarkerVo(cornerMarkerLeftBottomName, DecorationTypeEnum.getNameByType(decorationType)));
        return cornerMarkers;
    }

    private Map<String, Object> concurrentQueryOrderBaseAndOrderSolution(Integer orderId) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        //查询大订单基础信息
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
        //查询订单已选方案信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return productProgramOrderProxy.querySolutionInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name();
            }
        });
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    private Map<String, Object> concurrentQueryProgramAndDraftAndTask(Map<String, Object> params) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        //查询可选方案列表
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return productProgramProxy.querySolutionList(params);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_LIST.name();
            }
        });
        //查询草稿列表
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                params.put("pageNo", 1);
                params.put("pageSize", 100);
                return homeCardWcmProxy.queryDraftList(params);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DRAFT_LIST.name();
            }
        });
        //查询提交方案列表
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
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }


    private Map<String, Object> concurrentQueryDeliveryInfoAndDeliveryConfirm(Integer orderId) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        //查询交付简要信息
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
        //查询是否可确认开工标记位
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return orderProxy.checkIfCanDeliveryConfirm(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.CHECK_IF_CAN_DELIVER_CONFIRM.name();
            }
        });
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }
}
