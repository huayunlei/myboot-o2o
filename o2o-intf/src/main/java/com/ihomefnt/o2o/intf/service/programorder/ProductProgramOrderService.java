/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年3月15日
 * Description:ProductProgramOrderService.java
 */
package com.ihomefnt.o2o.intf.service.programorder;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.ProgramOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomReplaceHardProductDto;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ContractInfoResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.UserInfoResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.*;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhang
 */
public interface ProductProgramOrderService {

    /**
     * 根据方案id,获取方案下所有的空间Id集合
     *
     * @param solutionId 方案id
     * @return 空间Id集合
     */
    List<Integer> getRoomIdListBySuitId(Integer solutionId);

    /**
     * 创建订单
     *
     * @param request 订单请求对象
     * @param opType  1-下单 2-查询订单价格
     * @return
     */
    AladdinOrderResponseVo createOrder(ProgramOrderCreateRequest request, Integer opType);

    /**
     * 查询订单详情
     *
     * @param request
     * @return 订单详情
     */
    OrderResponse orderDetail(ProgramOrderDetailRequest request);

    /**
     * 获取订单的方案信息,如果是组合,那就获取客厅;没有客厅就随机取一张
     *
     * @param orderId
     * @param width
     * @return
     */
    OrderResponse orderDetailById(Integer orderId, Integer width);

    /**
     * 根据用户id来查询用户信息
     *
     * @param userId
     * @return
     */
    UserInfoResponse getUserInfoResponseByUserId(Integer userId);

    /**
     * 根据订单ID查询支付明细（分页）
     *
     * @param request
     * @return
     */
    List<PaymentRecordListResponse> queryPaymentRecordListByOrderId(ProgramOrderRecordRequest request);

    /**
     * 根据订单ID查询支付结果
     *
     * @param request
     * @return
     */
    PaymentResultResponse queryPaymentResultByOrderId(ProgramOrderDetailRequest request);

    /**
     * 支付失败删除支付流水记录
     *
     * @param request
     * @return
     */
    boolean cancelPaymentRecord(CancelPaymentRecordRequest request);

    /**
     * 根据订单ID查询全品家订单详情
     *
     * @param orderId
     * @param width
     * @return
     */
    AllProductOrderResponse queryAllProductOrderDetailById(Integer orderId, Integer width, String source);

    /**
     * 查询贷款资质
     *
     * @param orderId
     * @return
     */
    boolean queryLoanQualification(Integer orderId);

    /**
     * 新增贷款信息记录
     *
     * @param userId
     * @param orderId
     * @param amount
     * @return
     */
    Long createLoanInfo(Integer userId, Integer orderId, BigDecimal amount);

    /**
     * 根据订单ID查询代客下单订单详情
     *
     * @param orderId
     * @param width
     * @return
     */
    AllProductOrderResponse queryValetOrderDetailById(Integer orderId, Integer width);

    /**
     * 参加促销活动
     *
     * @param orderId
     * @param actCodes
     * @return
     */
    Integer joinPromotion(Integer orderId, List<Integer> actCodes);

    /**
     * 查询订单软硬装清单
     *
     * @param orderId
     * @param width
     * @return
     */
    SoftAndHardListResponse querySoftAndHardListById(Integer orderId, Integer width);

    /**
     * 需求确认
     *
     * @param request
     * @return
     */
    DemandConfirmationResponseVo demandConfirmation(DemandConfirmationRequest request);

    /**
     * 取消订单方案
     *
     * @param orderId
     * @return
     */
    HttpBaseResponse cancelOrderProgram(Integer orderId);

    /**
     * 查询订单电子合同
     *
     * @param orderId
     * @return Author: ZHAO
     * Date: 2018年4月12日
     */
    List<ContractInfoResponse> queryContractListByOrderId(Integer orderId);

    /**
     * 查询电子合同模板
     *
     * @return Author: ZHAO
     * Date: 2018年4月16日
     */
    List<ContractInfoResponse> queryContractTemplateList();

    /**
     * 根据订单号查询用户已选方案的清单
     *
     * @return Author: XIA
     * Date: 2018年7月10日
     */
    SolutionSelected queryOrderSolutionSelectedList(Integer orderId, Integer width);

    /**
     * 根据订单号查询用户已选方案的清单
     *
     * @return Author: XIA
     * Date: 2018年7月10日
     */
    AladdinCreateOrderResponseVo createFamilyOrder(CreateFamilyOrderRequest request);

    /**
     * 查询用户下单价格
     *
     * @return Author:xwf
     * Date:2019-01-21
     */
    AladdinOrderResponseVo queryFamilyOrderPrice(QueryFamilyOrderPriceRequest request);

    /**
     * 查询订单方案详情
     *
     * @param orderId
     * @return
     */
    SolutionOrderInfo querySolutionOrderInfo(Integer orderId, Integer width);

    /**
     * 根据订单ID查询全品家订单详情订单列表
     *
     * @param orderId
     * @param width
     * @return Author: XIA
     */
    AllProductOrderResponse queryProductOrderDetailForAPPById(Integer orderId, Integer width, String source);

    /**
     * 查询收银台所需基础信息
     *
     * @param orderId
     * @return Author: XIA
     */
    FamilyOrderPayResponse queryPayBaseInfo(Integer orderId);

    /**
     * 查询最低定金金额
     *
     * @param userId
     * @param orderId
     * @return
     */
    BigDecimal queryMinDepositByUserAndOrder(Integer userId, Integer orderId);

    /**
     * 预约线下付款
     *
     * @param orderId
     * @return
     */
    Boolean appointOfflinePay(Integer orderId);

    /**
     * 预确认方案
     *
     * @param orderId
     * @return
     */
    Integer preConfirmSolution(Integer orderId);

    /**
     * 查询付款详情
     *
     * @param params
     * @return
     */
    TransactionDetail queryPaymentRecordDetailById(ProgramOrderRecordRequest params);

    /**
     * 根据订单ID查询订单详情
     *
     * @param request
     * @return
     */
    SoftListResponse querySoftListByOrderId(ProgramOrderDetailRequest request);

    /**
     * 根据订单号组装软装清单
     *
     * @param orderId
     * @return
     */
    SoftListResponse getSkuListByOrderId(Integer orderId);

    /**
     * 异步价格校验
     *
     * @param result
     * @param serverSpan
     */
    void CheckPrice(AladdinCreateOrderResponseVo result, CreateFamilyOrderRequest request);

    /**
     * 更新订单
     *
     * @param orderRequest
     * @return
     */
    AladdinCreateOrderResponseVo updateFamilyOrder(CreateFamilyOrderRequest orderRequest);

    /**
     * 查询订单信息
     *
     * @param orderId
     * @return
     */
    AppOrderBaseInfoResponseVo queryAppOrderBaseInfo(Integer orderId);

    /**
     * 5.0选方案接口  opType  1 提交方案 2 查询方案总价（opType已废弃，查询使用orderService.queryFamilyOrderPrice）
     *
     * @param request
     * @return
     */
    AladdinOrderResponseVo createFamilyProgramOrder(CreateFamilyOrderRequest request);

    /**
     * 提交签约弹窗，返回订单预算价格&合同模板
     * @param request
     * @return
     */
    QueryPreSignResponse queryPreSignInfo(QueryPreSignRequest request);


    AladdinOrderResultDto queryAllProductOrderDetailById(Integer masterOrderId,boolean isOld);

    /**
     * 根据大订单id查询自由搭配方案详情
     */
    AladdinSolutionDetailResponseVo querySolutionDetailWithMasterOrderId(Integer masterOrderId);

    /**
     * 全品家订单详情
     * @param request
     * @return
     */
    FamilyOrderDetailResponse queryFamilyOrderDetail(ProgramOrderDetailRequest request);


    List<RoomReplaceHardProductDto> checkAndReplaceHardRoomClassId(List<RoomReplaceHardProductDto> roomHardReplaceList);

    LoanInfoResponse queryLoanPermissionInfo(ProgramOrderDetailRequest request);
}
