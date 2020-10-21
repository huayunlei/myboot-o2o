package com.ihomefnt.o2o.service.service.demo;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.o2o.constant.FamilyOrderStatusEnum;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;
import com.ihomefnt.o2o.intf.domain.demo.vo.DemoParamsVo;
import com.ihomefnt.o2o.intf.domain.demo.vo.request.CompleteDesignDemandRequest;
import com.ihomefnt.o2o.intf.domain.demo.vo.request.DemoCommonRequest;
import com.ihomefnt.o2o.intf.domain.demo.vo.response.DemoCommonResponse;
import com.ihomefnt.o2o.intf.domain.dms.vo.DemoDeliveryRequestVo;
import com.ihomefnt.o2o.intf.domain.finalkeeper.request.ConfirmFundRequest;
import com.ihomefnt.o2o.intf.domain.main.dto.DeliverySimpleInfoDto;
import com.ihomefnt.o2o.intf.domain.demo.vo.DemoButtonInfoVo;
import com.ihomefnt.o2o.intf.domain.demo.vo.DemoButtonSingleVo;
import com.ihomefnt.o2o.intf.domain.main.vo.vo.ProductStatusRequestVo;
import com.ihomefnt.o2o.intf.domain.main.vo.vo.ProductStatusResponseVo;
import com.ihomefnt.o2o.intf.domain.order.vo.CheckIfCanDeliveryConfirmVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HandleDepositMoneyRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HandleMoneyRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.OrderSimpleInfoRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.UpdateDeliverTimeRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HandleDepositMoneyResponseVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderSimpleInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.AppSolutionDesignResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.BatchSolutionBaseInfoVo;
import com.ihomefnt.o2o.intf.domain.program.enums.DecorationTypeEnum;
import com.ihomefnt.o2o.intf.domain.program.vo.request.BatchQuerySolutionByHouseIdRequestVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DirectCompleteSolutionTaskRequestVo;
import com.ihomefnt.o2o.intf.manager.constant.demo.DemoStatusNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.dms.DmsProductStatusConstants;
import com.ihomefnt.o2o.intf.manager.constant.home.MasterOrderStatusEnum;
import com.ihomefnt.o2o.intf.manager.constant.personalneed.DesignTaskAppEnum;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.proxy.ddc.AladdinDdcProxy;
import com.ihomefnt.o2o.intf.proxy.demo.DemoProxy;
import com.ihomefnt.o2o.intf.proxy.designdemand.PersonalNeedProxy;
import com.ihomefnt.o2o.intf.proxy.finalkeeper.FinalkeeperProxy;
import com.ihomefnt.o2o.intf.proxy.main.DmsProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.service.demo.DemoService;
import com.ihomefnt.o2o.intf.service.designDemand.ProgramPersonalNeedService;
import com.ihomefnt.o2o.service.proxy.dms.enums.DeliveryStatusEnum;
import com.ihomefnt.o2o.service.proxy.dms.enums.DmsProjectAppStatusEnums;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import com.ihomefnt.o2o.service.service.common.CommonService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PersonalNeedProxy personalNeedProxy;

    @Autowired
    private ProgramPersonalNeedService programPersonalNeedService;

    @Autowired
    private DmsProxy dmsProxy;

    @Autowired
    private OrderProxy orderProxy;

    @Autowired
    private AladdinDdcProxy ddcProxy;

    @Autowired
    private FinalkeeperProxy finalkeeperProxy;

    @Autowired
    private DemoProxy demoProxy;

    @Autowired
    private CommentService commentService;

    /**
     * 演示账号按钮
     */
    @NacosValue(value = "${demo.button.list}", autoRefreshed = true)
    private String demoButtonList;
    /**
     * 环境域名
     */
    @NacosValue(value = "${o2o.env.web.name}", autoRefreshed = true)
    private String envWebName;
    @NacosValue(value = "${demo.env.web.name.replace.string}", autoRefreshed = true)
    private String envWebNameReplaceString;

    /**
     * 一些硬编码
     */
    // 定金默认交款金额 10000
    @NacosValue(value = "${demo.earnest.default.amount}", autoRefreshed = true)
    private BigDecimal demoEarnestDefaultAmount;
    // 默认操作人 610
    @NacosValue(value = "${demo.earnest.default.user.id}", autoRefreshed = true)
    private Integer demoEarnestDefaultUserId;
    // 默认备注 测试：演示系统
    @NacosValue(value = "${demo.earnest.default.remark}", autoRefreshed = true)
    private String demoEarnestDefaultRemark;
    // 默认收款方式  51 网划
    @NacosValue(value = "${demo.earnest.default.pay.type}", autoRefreshed = true)
    private Integer demoEarnestDefaultPayType;
    // 默认收款银行 测试银行 19
    @NacosValue(value = "${demo.earnest.default.bank.account}", autoRefreshed = true)
    private Integer demoEarnestDefaultBankAccount;
    // 设置默认交房日期距离今天的天数 90
    @NacosValue(value = "${demo.default.get.house.day.diff}", autoRefreshed = true)
    private Integer demoDefaultGetHouseDayDiff;

    /**
     * 白名单
     */
    @NacosValue(value = "${demo.white.mobile.list}", autoRefreshed = true)
    private String demoWhiteMobiles;
    @NacosValue(value = "${demo.white.building.list}", autoRefreshed = true)
    private String demoWhiteBuildings;
    @NacosValue(value = "${demo.white.building.switch}", autoRefreshed = true)
    private Boolean demoWhiteBuildingSwitch;

    @NacosValue(value = "${loan.program.id}", autoRefreshed = true)
    private Integer loanProgramId;

    private String successString = "success";

    @Override
    public Boolean judgeCanDemo(String mobileNum) {

        // 无订单或者无手机号，都没有演示按钮
        if (mobileNum == null) {
            return false;
        }
        // 非测试账号或者白名单账号，无演示按钮
        List<String> demoWhiteMobileList = JsonUtils.json2list(demoWhiteMobiles, String.class);
        return Boolean.TRUE.equals(commonService.judgeMobileIsTest(mobileNum)) ||
                demoWhiteMobileList.contains(mobileNum);
    }

    @Override
    public Boolean judgeBuildingIsWhite(Integer orderId) {
        List<Integer> demoWhiteBuildingList = JsonUtils.json2list(demoWhiteBuildings, Integer.class);
        AppOrderBaseInfoResponseVo orderInfo = productProgramOrderProxy.queryAppOrderBaseInfo(orderId);
        return orderInfo != null && demoWhiteBuildingList.contains(orderInfo.getBuildingId()) && demoWhiteBuildingSwitch;
    }

    @Override
    public DemoCommonResponse queryDemoButtonInfo(DemoCommonRequest request) {
        DemoCommonResponse response = new DemoCommonResponse();

        List<String> statusNames = new ArrayList<>();
        AppOrderBaseInfoResponseVo orderInfo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());

        if (orderInfo == null || orderInfo.getOrderStatus() == null) {
            return response;
        }

        DemoParamsVo demoParams = new DemoParamsVo();
        demoParams.setOrderId(request.getOrderId());
        if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_CONTACT_STAGE.getStatus())) {
            // 接触阶段
            statusNames.add(DemoStatusNameConstants.NEED_PAY_EARNEST);
        } else if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_INTENTIONAL_PHASE.getStatus()) ||
                orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_DEPOSIT_PHASE.getStatus()) ||
                orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus())) {
            getStatusNamesBeforeDelivery(request, orderInfo, demoParams, statusNames);

        } else if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_PRE_DELIVERY.getStatus()) ||
                orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus())) {
            //交付阶段
            getStatusNamesAfterDelivery(request, statusNames);
        } else if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_COMPLETED.getStatus())){
            getCommentStatusName(request.getOrderId(), statusNames);
        }
        statusNames.add(DemoStatusNameConstants.CANCEL_ORDER);
        return replaceResponseString(getResponseByStatusNames(response, statusNames), demoParams);
    }

    // 获取交付前的节点名称
    public void getStatusNamesBeforeDelivery(DemoCommonRequest request, AppOrderBaseInfoResponseVo orderInfo, DemoParamsVo demoParams, List<String> statusNames) {
        // 已签约
        if(FamilyOrderStatusEnum.ORDER_STATUS_SIGNED.getStatus().equals(orderInfo.getOrderSubstatus())){
            getStatusNameAfterSigned(request, orderInfo, statusNames);
            return;
        }

        // 签约中
        if(FamilyOrderStatusEnum.ORDER_STATUS_SIGNING.getStatus().equals(orderInfo.getOrderSubstatus())){
            getStatusNameAfterSigning(orderInfo, statusNames);
        }

        // 意向、定金、签约阶段
        Map<String, Object> params = new HashMap<>();
        params.put("apartmentId", orderInfo.getLayoutId());
        params.put("orderNum", request.getOrderId());
        params.put("userId", request.getUserInfo() == null ? 0 : request.getUserInfo().getId());
        List<AppSolutionDesignResponseVo> solutionDesigns = personalNeedProxy.queryDesignDemondHistory(params);
        AppSolutionDesignResponseVo designingSolutionDesign = null;
        for (AppSolutionDesignResponseVo solutionDesign : solutionDesigns) {
            // 获取设计中方案
            if (solutionDesign != null && solutionDesign.getTaskStatus() != null &&
                    (DesignTaskAppEnum.COMMITTED.getTaskStatus().equals(programPersonalNeedService.transferDesignStatus(solutionDesign.getTaskStatus())) ||
                            DesignTaskAppEnum.IN_DESIGN.getTaskStatus().equals(programPersonalNeedService.transferDesignStatus(solutionDesign.getTaskStatus())))) {
                designingSolutionDesign = solutionDesign;
                break;
            }
        }
        if (designingSolutionDesign != null) {
            // 有设计中需求且未签约完成
            statusNames.add(DemoStatusNameConstants.NEED_COMPLETE_DESIGN_DEMAND);
            demoParams.setTaskId(designingSolutionDesign.getTaskId());
        }else if(!FamilyOrderStatusEnum.ORDER_STATUS_SIGNING.getStatus().equals(orderInfo.getOrderSubstatus())){
            // 无设计需求且未签约
            statusNames.add(DemoStatusNameConstants.NEED_ADD_DESIGN_DEMAND);
        }
    }

    // 获取已签约的节点名称
    public void getStatusNameAfterSigning(AppOrderBaseInfoResponseVo orderInfo, List<String> statusNames){
        // 待付合同款
        if (orderInfo.getOrderTotalAmount() != null && orderInfo.getFundAmount() != null && orderInfo.getUpItemDeAmount() != null) {
            BigDecimal needPayAmount = orderInfo.getOrderTotalAmount().subtract(orderInfo.getFundAmount()).subtract(orderInfo.getUpItemDeAmount());
            if (BigDecimal.ZERO.compareTo(needPayAmount) < 0) {
                statusNames.add(DemoStatusNameConstants.NEED_PAY_CONTRACT);
            }
        }
    }

    // 获取已签约的节点名称
    public void getStatusNameAfterSigned(DemoCommonRequest request, AppOrderBaseInfoResponseVo orderInfo, List<String> statusNames){
        if((orderInfo.getDeliverDiff() == null || orderInfo.getDeliverDiff() > 100)) {
            statusNames.add(DemoStatusNameConstants.WAIT_DELIVER_HOUSE);
        }
        CheckIfCanDeliveryConfirmVo checkIfCanDeliveryConfirmVo = orderProxy.checkIfCanDeliveryConfirm(request.getOrderId());
        if(Boolean.TRUE.equals(checkIfCanDeliveryConfirmVo.getCheckResult())){
            statusNames.add(DemoStatusNameConstants.NEED_CONFIRM_START);
        }
    }


    // 获取交付中和交付后的节点名称
    public void getStatusNamesAfterDelivery(DemoCommonRequest request, List<String> statusNames) {
        // 待交付、交付中
        DeliverySimpleInfoDto deliveryInfo = dmsProxy.getSimpleDeliveryInfo(request.getOrderId());
        if (deliveryInfo == null || (deliveryInfo.getDeliverStatus() <= DeliveryStatusEnum.DELIVER_READY.getCode() &&
                StringUtils.isBlank(deliveryInfo.getPlanBeginDate()))) {
            // 未施工排期：一键开工(全品家/全品家软)
            AppOrderBaseInfoResponseVo orderInfo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());
            if (DecorationTypeEnum.SOFT.getType().equals(orderInfo.getOrderSaleType())) {
                statusNames.add(DemoStatusNameConstants.NEED_PLAN_SOFT);
            } else {
                statusNames.add(DemoStatusNameConstants.NEED_PLAN);
            }
        } else if (StringUtils.isNotBlank(deliveryInfo.getPlanBeginDate()) &&
                deliveryInfo.getDeliverStatus() <= DeliveryStatusEnum.DELIVER_READY.getCode()) {
            // 等待开工：一键开工交底
            statusNames.add(DemoStatusNameConstants.WAIT_CONSTRICTION);
        } else if (deliveryInfo.getDeliverStatus() >= DeliveryStatusEnum.CONSTRUCTING.getCode() &&
                deliveryInfo.getDeliverStatus() <= DeliveryStatusEnum.INSTALL.getCode()) {
            // 施工中各节点
            getStatusNamesInDelivery(request, statusNames, deliveryInfo);
            statusNames.add(DemoStatusNameConstants.FAST_CHECK);
        } else if(deliveryInfo.getDeliverStatus() >= DeliveryStatusEnum.COMPLETE.getCode()) {
            // 施工完成各节点
            getStatusNamesInDeliveryEnd(deliveryInfo, statusNames);
        }

    }

    public void getStatusNamesInDelivery(DemoCommonRequest request, List<String> statusNames, DeliverySimpleInfoDto deliveryInfo) {
        AppOrderBaseInfoResponseVo orderInfo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());
        // sku状态
        ProductStatusRequestVo requestVo = new ProductStatusRequestVo();
        requestVo.setOrderNum(request.getOrderId());
        List<ProductStatusResponseVo> productStatus = dmsProxy.queryProductStatusByOrderId(requestVo);
        // 判断是否全部采购中
        Boolean isAllBuy = true;
        if(productStatus == null){
            isAllBuy = false;
        }else{
            for (ProductStatusResponseVo productStatusSingle : productStatus) {
                if (DmsProductStatusConstants.CANCEL.equals(productStatusSingle.getStatus()) ||
                        DmsProductStatusConstants.WAITING_PURCHASE.equals(productStatusSingle.getStatus())) {
                    isAllBuy = false;
                }
            }
        }
        // 获取软装按钮名称
        getSoftStatusNamesInDelivery(statusNames, orderInfo, isAllBuy);

        // 全品家软不需要组装硬装内容
        if (DecorationTypeEnum.ALL.getType().equals(orderInfo.getOrderSaleType())) {
            // 获取硬装按钮名称
            getHardStatusNamesInDelivery(statusNames, deliveryInfo, isAllBuy);
        }
    }

    public void getStatusNamesInDeliveryEnd(DeliverySimpleInfoDto deliveryInfo, List<String> statusNames){
        if (DeliveryStatusEnum.COMPLETE.getCode() == deliveryInfo.getDeliverStatus() &&
                (2 != deliveryInfo.getMasterCheckStatus() && Boolean.FALSE.equals(deliveryInfo.getFastCheckApproval()))) {
            // 竣工，但是艾师傅未验收
            statusNames.add(DemoStatusNameConstants.MANAGER_CHECK);
            statusNames.add(DemoStatusNameConstants.FAST_CHECK);
        }else if(DeliveryStatusEnum.COMPLETE.getCode() == deliveryInfo.getDeliverStatus() &&
                (2 == deliveryInfo.getMasterCheckStatus() || Boolean.TRUE.equals(deliveryInfo.getFastCheckApproval())) &&
                2!=deliveryInfo.getOwnerCheckStatus()){
            // 已完工 待验收/验收不通过 (竣工阶段且艾师傅验收通过)
            statusNames.add(DemoStatusNameConstants.NEED_ACCEPT);
        }else if(DeliveryStatusEnum.WARRANT.getCode() <= deliveryInfo.getDeliverStatus()){
            getCommentStatusName(deliveryInfo.getOrderId(), statusNames);
        }
    }

    public void getCommentStatusName(Integer orderId, List<String> statusNames){
        CommentsDto commentsDto = commentService.getComment(orderId);
        // 未点评=>待点评
        if(commentsDto == null || commentsDto.getOrderId() == null){
            statusNames.add(DemoStatusNameConstants.NEED_COMMENT);
        }
    }

    // 获取软装按钮名称
    public void getSoftStatusNamesInDelivery(List<String> statusNames, AppOrderBaseInfoResponseVo orderInfo, Boolean isAllBuy){
        Integer softTotal = orderInfo.getTotalProductCount();
        Integer softComplete = orderInfo.getCompleteDelivery();
        if (Boolean.FALSE.equals(isAllBuy)) {
            // 未全部采购 一键采购 快速验收
            statusNames.add(DemoStatusNameConstants.SOFT_NOT_BUY);
        } else if (softComplete == null || !softComplete.equals(softTotal)) {
            // 未全部到货 一键配送 快速验收
            statusNames.add(DemoStatusNameConstants.SOFT_NOT_RECIEVED);
        } else {
            // 全部到货 一键验收 快速验收
            statusNames.add(DemoStatusNameConstants.SOFT_RECIEVED);
        }
    }

    // 获取硬装按钮名称
    public void getHardStatusNamesInDelivery(List<String> statusNames, DeliverySimpleInfoDto deliveryInfo, Boolean isAllBuy){
        if (deliveryInfo.getProjectStatus() == null ||
                deliveryInfo.getProjectStatus() == DmsProjectAppStatusEnums.START_CHECK.getCode()) {
            // 开工交底  开工交底 快速验收
            statusNames.add(DemoStatusNameConstants.WAIT_CONSTRICTION);
        } else if (deliveryInfo.getProjectStatus() == DmsProjectAppStatusEnums.HYDROPOWER_CHECK.getCode()) {
            // 水电阶段  完成水电 快速验收
            statusNames.add(DemoStatusNameConstants.HARD_HYDROPOWER);
        } else if (deliveryInfo.getProjectStatus() == DmsProjectAppStatusEnums.BUILDING_CHECK.getCode()) {
            // 瓦木阶段  完成瓦木 快速验收
            statusNames.add(DemoStatusNameConstants.HARD_WOODEN);
        } else if (deliveryInfo.getProjectStatus() == DmsProjectAppStatusEnums.FINISH_CHECK.getCode() &&
                Boolean.TRUE.equals(isAllBuy)) {
            // sku都采购中之后的状态，才能执行
            // 竣工验收 完成硬装竣工 快速验收
            statusNames.add(DemoStatusNameConstants.HARD_COMPLETE);
        }
    }


    // 根据节点名称获取按钮列表
    public DemoCommonResponse getResponseByStatusNames(DemoCommonResponse response, List<String> statusNames) {
        List<DemoButtonInfoVo> demoButtons = JsonUtils.json2list(demoButtonList, DemoButtonInfoVo.class);
        response.setMainCoreNames(statusNames);
        for (DemoButtonInfoVo demoButtonVo : demoButtons) {
            for (String statusName : statusNames) {
                if (demoButtonVo.getMainCoreName().equals(statusName)) {
                    if (response.getDemoButtonInfo() == null || response.getDemoButtonInfo().getButtonList() == null) {
                        response.setDemoButtonInfo(demoButtonVo);
                    } else {
                        response.getDemoButtonInfo().setButtonList(
                                uniqueAddButton(response.getDemoButtonInfo().getButtonList(), demoButtonVo.getButtonList()));
                    }
                }
            }
        }
        return response;
    }

    // 对按钮进行去重
    public List<DemoButtonSingleVo> uniqueAddButton(List<DemoButtonSingleVo> responseButtonList,
                                                    List<DemoButtonSingleVo> addButtonList) {
        for (DemoButtonSingleVo buttonSingleVo : addButtonList) {
            Boolean hasExist = false;
            for (DemoButtonSingleVo demoButtonSingleVo : responseButtonList) {
                if (buttonSingleVo.getButtonName().equals(demoButtonSingleVo.getButtonName())) {
                    hasExist = true;
                }
            }
            if (Boolean.FALSE.equals(hasExist)) {
                responseButtonList.add(buttonSingleVo);
            }
        }
        return responseButtonList;
    }

    // 替换按钮通配符
    public DemoCommonResponse replaceResponseString(DemoCommonResponse response, DemoParamsVo demoParams) {
        for (DemoButtonSingleVo demoButtonSingle : response.getDemoButtonInfo().getButtonList()) {
            if(demoButtonSingle.getButtonUrl() == null){
                continue;
            }
            demoButtonSingle.setButtonUrl(demoButtonSingle.getButtonUrl().replace(envWebNameReplaceString, envWebName));
            if (demoParams.getOrderId() != null) {
                demoButtonSingle.setButtonUrl(demoButtonSingle.getButtonUrl().replace("{orderId}", demoParams.getOrderId().toString()));
            }
            if (demoParams.getTaskId() != null) {
                demoButtonSingle.setButtonUrl(demoButtonSingle.getButtonUrl().replace("{taskId}", demoParams.getTaskId().toString()));
            }
        }
        return response;
    }

    @Override
    public String payEarnest(DemoCommonRequest request) {
        // 添加定金收款单
        HandleDepositMoneyRequestVo params = new HandleDepositMoneyRequestVo();
        params.setOrderNum(request.getOrderId());
        params.setTranstionAmount(demoEarnestDefaultAmount);
        params.setOperatorId(demoEarnestDefaultUserId);
        Integer userId = getOrderUserId(request.getOrderId());
        if (0 == userId) {
            return "查询订单用户ID失败";
        }
        params.setUserId(userId);
        params.setRemark(demoEarnestDefaultRemark);
        params.setPayType(demoEarnestDefaultPayType);
        HandleDepositMoneyResponseVo response = orderProxy.handleDepositMoney(params);
        if (response == null || response.getBillId() == null) {
            return "添加定金收款单失败";
        }
        return confirmFund(response.getBillId(), demoEarnestDefaultAmount);
    }

    public Integer getOrderUserId(Integer orderId) {
        OrderSimpleInfoRequestVo params = new OrderSimpleInfoRequestVo();
        List<Integer> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        params.setOrderNums(orderIds);
        List<OrderSimpleInfoResponseVo> response = orderProxy.querySimpleInfoByOrderNums(params);
        if (CollectionUtils.isEmpty(response) || response.get(0).getUserId() == null) {
            return 0;
        } else {
            return response.get(0).getUserId();
        }
    }

    public String confirmFund(Integer billId, BigDecimal amount) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = sdf.format(d);

        // 确认收款单
        ConfirmFundRequest confirmParams = new ConfirmFundRequest();
        confirmParams.setActualAmount(amount);
        confirmParams.setActualPayTime(data);
        confirmParams.setBankAccount(demoEarnestDefaultBankAccount);
        confirmParams.setBillId(billId);
        confirmParams.setPayTimeStr(data);
        confirmParams.setPayType(demoEarnestDefaultPayType);
        confirmParams.setRemark(demoEarnestDefaultRemark);
        confirmParams.setUserId(demoEarnestDefaultUserId);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            return e.getMessage();
        }
        String result = finalkeeperProxy.confirmFund(confirmParams);
        if (successString.equals(result)) {
            return "收款成功";
        }
        // 如果收款失败，间隔1s再来一次
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            return e.getMessage();
        }
        String result2 = finalkeeperProxy.confirmFund(confirmParams);
        if (successString.equals(result2)) {
            return "收款成功";
        }
        return "操作失败";
    }

    @Override
    public String completeDesignDemand(DemoCommonRequest request) {
        AppOrderBaseInfoResponseVo orderInfo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());

        BatchQuerySolutionByHouseIdRequestVo param = new BatchQuerySolutionByHouseIdRequestVo();
        param.setApartmentId(orderInfo.getLayoutId());
        BatchSolutionBaseInfoVo solutions = productProgramProxy.batchQuerySolutionBaseInfo(param);
        if (solutions != null && !solutions.getSolutionBaseInfoList().isEmpty()) {
            int solutionRemoveKey = -1;
            for (int i = 0; i < solutions.getSolutionBaseInfoList().size(); i++) {
                if (loanProgramId.equals(solutions.getSolutionBaseInfoList().get(i).getSolutionId())) {
                    solutionRemoveKey = i;
                    break;
                }
            }
            if (solutionRemoveKey >= 0) {
                solutions.getSolutionBaseInfoList().remove(solutionRemoveKey);
            }
        }

        if (solutions == null || CollectionUtils.isEmpty(solutions.getSolutionBaseInfoList())) {
            return "无可用方案";
        }

        int n = new Random().nextInt(solutions.getSolutionBaseInfoList().size());
        DirectCompleteSolutionTaskRequestVo params = new DirectCompleteSolutionTaskRequestVo();
        params.setTaskId(request.getTaskId());
        params.setSolutionId(solutions.getSolutionBaseInfoList().get(n).getSolutionId());
        return ddcProxy.directCompleteSolutionTask(params);
    }

    @Override
    public String completeDesignDemandBySolutionId(CompleteDesignDemandRequest request) {
        DirectCompleteSolutionTaskRequestVo params = new DirectCompleteSolutionTaskRequestVo();
        params.setTaskId(request.getTaskId());
        params.setSolutionId(request.getSolutionId());
        return ddcProxy.directCompleteSolutionTask(params);
    }

    @Override
    public String payContract(DemoCommonRequest request) {

        AppOrderBaseInfoResponseVo orderInfo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());
        // 待付合同款
        BigDecimal needPayAmount = new BigDecimal(0);
        if (orderInfo.getOrderTotalAmount() != null && orderInfo.getFundAmount() != null && orderInfo.getUpItemDeAmount() != null) {
            needPayAmount = orderInfo.getOrderTotalAmount().subtract(orderInfo.getFundAmount()).subtract(orderInfo.getUpItemDeAmount());
        }
        if (BigDecimal.ZERO.compareTo(needPayAmount) >= 0) {
            return "已付清全款";
        }
        // 添加合同收款单
        HandleMoneyRequestVo params = new HandleMoneyRequestVo();
        params.setOrderNum(request.getOrderId());
        params.setTranstionAmount(needPayAmount);
        params.setOperatorId(demoEarnestDefaultUserId);
        Integer userId = getOrderUserId(request.getOrderId());
        if (0 == userId) {
            return "查询订单用户ID失败";
        }
        params.setUserId(userId);
        params.setRemark(demoEarnestDefaultRemark);
        params.setPayType(demoEarnestDefaultPayType);
        params.setPayNo("1");
        HandleDepositMoneyResponseVo response = orderProxy.handleMoney(params);
        if (response == null || response.getBillId() == null) {
            return "添加合同款收款单失败";
        }
        return confirmFund(response.getBillId(), needPayAmount);
    }

    @Override
    public String updateDeliverTime(DemoCommonRequest request) {
        UpdateDeliverTimeRequestVo params = new UpdateDeliverTimeRequestVo();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,
                request.getDeliverTimeDiff() == null ? demoDefaultGetHouseDayDiff : request.getDeliverTimeDiff());
        Date d = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String data = sdf.format(d);
        params.setDeliverTimeStr(data);
        params.setOperatorId(demoEarnestDefaultUserId);
        params.setOrderId(request.getOrderId());
        return orderProxy.updateDeliverTime(params);
    }

    @Override
    public String scheduleDate(DemoDeliveryRequestVo request) {
        return demoProxy.scheduleDate(request);
    }

    @Override
    public String softScheduleDate(DemoDeliveryRequestVo request) {
        return demoProxy.softScheduleDate(request);
    }

    @Override
    public String startSchedule(DemoDeliveryRequestVo request) {
        return demoProxy.startSchedule(request);
    }

    @Override
    public String finishHydropower(DemoDeliveryRequestVo request) {
        return demoProxy.finishHydropower(request);
    }

    @Override
    public String finishWooden(DemoDeliveryRequestVo request) {
        return demoProxy.finishWooden(request);
    }

    @Override
    public String finishHard(DemoDeliveryRequestVo request) {
        return demoProxy.finishHard(request);
    }

    @Override
    public String finishPurchase(DemoDeliveryRequestVo request) {
        return demoProxy.finishPurchase(request);
    }

    @Override
    public String finishLogistic(DemoDeliveryRequestVo request) {
        // 采购=>送货中；配送=>送货中；安装=>已到货
        // 所以把安装合并到配送按钮
        if (successString.equals(demoProxy.finishInstall(request))) {
            return demoProxy.finishLogistic(request);
        } else {
            return "false";
        }
    }

    @Override
    public String finishInstall(DemoDeliveryRequestVo request) {
        return demoProxy.finishInstall(request);
    }

    @Override
    public String finishSoftCheck(DemoDeliveryRequestVo request) {
        return demoProxy.finishSoftCheck(request);
    }

    @Override
    public String finishCheck(DemoDeliveryRequestVo request) {
        return demoProxy.finishCheck(request);
    }

    @Override
    public String finishFastCheck(DemoDeliveryRequestVo request) {
        return demoProxy.finishFastCheck(request);
    }

    @Override
    public String cancelOrder(DemoDeliveryRequestVo request) {
        return orderProxy.cancelOrder(request.getOrderId());
    }
}
