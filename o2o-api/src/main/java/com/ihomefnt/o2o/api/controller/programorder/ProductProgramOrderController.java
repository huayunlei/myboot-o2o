/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年3月15日
 * Description:ProductProgramOrderController.java
 */
package com.ihomefnt.o2o.api.controller.programorder;


import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.QueryDraftRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.ProgramOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ContractInfoResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionOrderInfo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionSelected;
import com.ihomefnt.o2o.intf.domain.programorder.dto.TransactionDetail;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderPreConfirmErrorCodeEnum;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.service.home.HomeCardService;
import com.ihomefnt.o2o.intf.service.home.HomeV5PageService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhang
 */
@RestController
@Api(tags = "【产品方案-订单API】")
@RequestMapping("/programOrder")
public class ProductProgramOrderController {

    @Autowired
    private ProductProgramOrderService orderService;

    @Autowired
    UserProxy userProxy;

    @Autowired
    CommentService commentService;

    @Autowired
    HomeV5PageService homeV5PageService;

    @Autowired
    HomeCardService homeCardService;

    @NacosValue(value = "${spacemark.update.version}", autoRefreshed = true)
    private String spacemarkUpdateVersion;

    @ApiOperation(value = "创建订单", notes = "创建订单(套装:9,和空间组合:10)")
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public HttpBaseResponse<AladdinOrderResponseVo> createOrder(@RequestBody ProgramOrderCreateRequest request) {
        // 判断请求参数是否为空
        if (request == null || StringUtils.isEmpty(request.getAccessToken()) || request.getOrderType() == null
                || request.getHouseId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        AladdinOrderResponseVo response = orderService.createOrder(request, 1);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "5.0.0新版本创建订单", notes = "创建订单")
    @RequestMapping(value = "/v5/createFamilyOrder", method = RequestMethod.POST)
    public HttpBaseResponse<AladdinOrderResponseVo> createFamilyOrder(@RequestBody CreateFamilyOrderRequest request) {
        if (request == null || StringUtils.isEmpty(request.getAccessToken())
                || request.getHouseId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        AladdinOrderResponseVo response = orderService.createFamilyProgramOrder(request);
        return HttpBaseResponse.success(response);
    }

    /**      
     *  @deprecated
     * */
    @ApiOperation(value = "5.0.0新版本查询订单价格", notes = "创建订单")
    @RequestMapping(value = "/v5/queryFamilyOrderPrice", method = RequestMethod.POST)
    @Deprecated
    public HttpBaseResponse<AladdinOrderResponseVo> queryFamilyOrderPrice(@RequestBody QueryFamilyOrderPriceRequest request, HttpServletRequest req) {
        if (request == null || StringUtils.isEmpty(request.getAccessToken()) || request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        AladdinOrderResponseVo response = orderService.queryFamilyOrderPrice(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "订单详情", notes = "订单详情(套装:9,和空间组合:10)")
    @RequestMapping(value = "/orderDetail", method = RequestMethod.POST)
    public HttpBaseResponse<OrderResponse> orderDetail(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        OrderResponse response = orderService.orderDetail(request);
        if (response == null) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "支付明细分页查询接口", notes = "支付明细分页查询接口")
    @RequestMapping(value = "/queryPaymentRecordListByOrderId", method = RequestMethod.POST)
    public HttpBaseResponse<List<PaymentRecordListResponse>> queryPaymentRecordListByOrderId(
            @RequestBody ProgramOrderRecordRequest request) {
        if (request == null || StringUtils.isBlank(request.getOrderNum())) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        List<PaymentRecordListResponse> obj = orderService.queryPaymentRecordListByOrderId(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "付款详情", notes = "支付明细分页查询接口 {'id':123}")
    @RequestMapping(value = "/queryPaymentRecordDetailById", method = RequestMethod.POST)
    public HttpBaseResponse<TransactionDetail> queryPaymentRecordDetailById(
            @RequestBody ProgramOrderRecordRequest params) {
        if (params == null || params.getId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        TransactionDetail obj = orderService.queryPaymentRecordDetailById(params);
        if (obj == null) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "支付结果查询接口", notes = "支付结果查询接口")
    @RequestMapping(value = "/queryPaymentResultByOrderId", method = RequestMethod.POST)
    public HttpBaseResponse<PaymentResultResponse> queryPaymentResultByOrderId(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(orderService.queryPaymentResultByOrderId(request));
    }

    @ApiOperation(value = "支付失败删除支付流水记录", notes = "支付失败删除支付流水记录")
    @RequestMapping(value = "/cancelPaymentRecord", method = RequestMethod.POST)
    public HttpBaseResponse<CancelPaymentRecordResponseVo> cancelPaymentRecord(@RequestBody CancelPaymentRecordRequest request) {
        if (request == null || request.getPayType() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        boolean obj = orderService.cancelPaymentRecord(request);
        return HttpBaseResponse.success(new CancelPaymentRecordResponseVo(obj));
    }

    /**      
     *  @deprecated
     * */
    @ApiOperation(value = "全品家订单详情", notes = "全品家订单详情")
    @RequestMapping(value = "/queryAllProductOrderDetailById", method = RequestMethod.POST)
    @Deprecated
    public HttpBaseResponse<AllProductOrderResponse> queryAllProductOrderDetailById(
            @RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        AllProductOrderResponse obj = orderService.queryAllProductOrderDetailById(request.getOrderId(),
                request.getWidth(), "orderDetail");
        if (obj == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.QUERY_EMPTY);
        }
        //添加是否可以点评 字段 -------- 蒋军 2018.04.16
        obj.setCheckFlag(commentService.isCanComment(request.getOrderId()));
        return HttpBaseResponse.success(obj, MessageConstant.QUERY_SUCCESS);
    }

    @ApiOperation(value = "查询订单方案详情信息", notes = "查询订单方案详情信息")
    @RequestMapping(value = "/querySolutionOrderInfo", method = RequestMethod.POST)
    public HttpBaseResponse<SolutionOrderInfo> querySolutionOrderInfo(
            @RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        SolutionOrderInfo obj = orderService.querySolutionOrderInfo(request.getOrderId(), request.getWidth());
        if (obj == null) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "查询贷款资质", notes = "查询贷款资质")
    @RequestMapping(value = "/queryLoanQualification", method = RequestMethod.POST)
    public HttpBaseResponse<Boolean> queryLoanQualification(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null || request.getOrderId() <= 0) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        boolean result = orderService.queryLoanQualification(request.getOrderId());
        return HttpBaseResponse.success(result);
    }

    @ApiOperation(value = "新增贷款信息记录", notes = "新增贷款信息记录")
    @RequestMapping(value = "/createLoanInfo", method = RequestMethod.POST)
    public HttpBaseResponse<Long> createLoanInfo(@RequestBody LoanInfoCreateRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }
        Long result = orderService.createLoanInfo(userDto.getId(), request.getOrderId(), request.getAmount());
        if (result == null || result <= 0l) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }

        return HttpBaseResponse.success(result);
    }

    @ApiOperation(value = "代客下单订单详情", notes = "代客下单订单详情")
    @RequestMapping(value = "/queryValetOrderDetailById", method = RequestMethod.POST)
    public HttpBaseResponse<AllProductOrderResponse> queryValetOrderDetailById(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        AllProductOrderResponse obj = orderService.queryValetOrderDetailById(request.getOrderId(), request.getWidth());
        if (obj == null) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }

        //添加是否可以点评 字段 -------- 蒋军 2018.04.16
        if (obj.getState() != null && obj.getState() > 5) {
            obj.setCheckFlag(commentService.isCanComment(request.getOrderId()));
        } else {
            obj.setCheckFlag(false);
        }

        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "查询订单总价", notes = "查询订单总价")
    @RequestMapping(value = "/queryOrderTotalPrice", method = RequestMethod.POST)
    public HttpBaseResponse<AladdinOrderResponseVo> queryOrderTotalPrice(@RequestBody ProgramOrderCreateRequest request) {
        if (request == null || StringUtils.isEmpty(request.getAccessToken()) || request.getOrderType() == null
                || request.getHouseId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        AladdinOrderResponseVo response = orderService.createOrder(request, 2);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "查询订单软硬装清单", notes = "查询订单软硬装清单")
    @RequestMapping(value = "/querySoftAndHardListById", method = RequestMethod.POST)
    public HttpBaseResponse<SoftAndHardListResponse> querySoftAndHardListById(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }

        SoftAndHardListResponse obj = orderService.querySoftAndHardListById(request.getOrderId(), request.getWidth());
        if (obj == null) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "需求确认", notes = "需求确认（app确认开工）")
    @RequestMapping(value = "/demandConfirmation", method = RequestMethod.POST)
    public HttpBaseResponse<DemandConfirmationResponseVo> demandConfirmation(@RequestBody DemandConfirmationRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        DemandConfirmationResponseVo response = orderService.demandConfirmation(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "取消订单方案", notes = "取消订单方案")
    @RequestMapping(value = "/cancelOrderProgram", method = RequestMethod.POST)
    public HttpBaseResponse cancelOrderProgram(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        // 判断是否登陆
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        return orderService.cancelOrderProgram(request.getOrderId());
    }

    @ApiOperation(value = "查询订单电子合同", notes = "查询订单电子合同")
    @RequestMapping(value = "/queryContractListByOrderId", method = RequestMethod.POST)
    public HttpBaseResponse<ContractInfoListResponseVo> queryContractListByOrderId(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        List<ContractInfoResponse> contractInfoList = orderService.queryContractListByOrderId(request.getOrderId());
        return HttpBaseResponse.success(new ContractInfoListResponseVo(contractInfoList));
    }

    @ApiOperation(value = "查询电子合同模板", notes = "查询电子合同模板")
    @RequestMapping(value = "/queryContractTemplateList", method = RequestMethod.POST)
    public HttpBaseResponse<ContractTemplateListResponseVo> queryContractTemplateList() {
        List<ContractInfoResponse> contractList = orderService.queryContractTemplateList();
        return HttpBaseResponse.success(new ContractTemplateListResponseVo(contractList));
    }

    @ApiOperation(value = "提交选方案草稿", notes = "提交选方案草稿")
    @RequestMapping(value = "/v5/createSolutionCraft", method = RequestMethod.POST)
    public HttpBaseResponse<String> createSolutionCraft(@RequestBody CreateDraftRequest request) {
        if (request == null || request.getOrderId() == null || StringUtils.isBlank(request.getDraftJsonStr())) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }

        String result = homeV5PageService.createSolutionCraft(request);
        if (StringUtils.isBlank(result)) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(result);
    }

    @ApiOperation(value = "保存或更新草稿", notes = "保存或更新草稿")
    @RequestMapping(value = "/v529/addOrUpdateDraft", method = RequestMethod.POST)
    public HttpBaseResponse<DraftSimpleRequest> addOrUpdateDraft(@RequestBody DraftInfoRequest request) {
        if (request == null || request.getOrderId() == null || request.getDraftContent() == null) {
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        request.setUserId(userDto.getId());
        String draftId = homeV5PageService.addOrUpdateDraft(request);
        return HttpBaseResponse.success(new DraftSimpleRequest(draftId));
    }

    @ApiOperation(value = "全品家订单清单", notes = "根据订单号查询全品家清单")
    @RequestMapping(value = "/v5/querySelectedDesign", method = RequestMethod.POST)
    public HttpBaseResponse<SolutionSelected> querySelectedDesign(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        SolutionSelected response = orderService.queryOrderSolutionSelectedList(request.getOrderId(), request.getWidth() == null ? 750 : request.getWidth());
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "删除选方案草稿", notes = "删除选方案草稿")
    @RequestMapping(value = "/v5/deleteSolutionCraft", method = RequestMethod.POST)
    public HttpBaseResponse<Boolean> deleteSolutionCraft(@RequestBody QueryDraftRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        Boolean result = homeV5PageService.deleteSolutionDraft(request);
        if (result == null || !result) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(result);
    }

    @ApiOperation(value = "通过订单id查询收银台所需信息", notes = "查询收银台所需信息")
    @RequestMapping(value = "/v5/queryFamilyOrderPayInfo", method = RequestMethod.POST)
    public HttpBaseResponse<FamilyOrderPayResponse> queryFamilyOrderPayInfo(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误");
        }

        // 判断是否登陆
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }
        FamilyOrderPayResponse response = orderService.queryPayBaseInfo(request.getOrderId());

        response.setMinDeposit(orderService.queryMinDepositByUserAndOrder(userDto.getId(), request.getOrderId()));
        if (response == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "预约线下付款", notes = "预约线下付款")
    @RequestMapping(value = "/v5/appointOfflinePay", method = RequestMethod.POST)
    public HttpBaseResponse<Boolean> appointOfflinePay(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }

        //判断是否登陆
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        Boolean result = orderService.appointOfflinePay(request.getOrderId());
        if (!result) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(result);
    }

    @ApiOperation(value = "预确认方案", notes = "预确认方案")
    @RequestMapping(value = "/v5/preConfirmSolution", method = RequestMethod.POST)
    public HttpBaseResponse<Integer> preConfirmSolution(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }

        //判断是否登陆
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        Integer result = orderService.preConfirmSolution(request.getOrderId());
        if (result != 1) {
            String errorMsg = OrderPreConfirmErrorCodeEnum.getShowMsgByCode(result == null ? 2 : result);
            return HttpBaseResponse.fail(errorMsg == null ? OrderPreConfirmErrorCodeEnum.getShowMsgByCode(2) : errorMsg);
        }

        return HttpBaseResponse.success(result);
    }

    @ApiOperation(value = "通过订单id查询软装清单", notes = "查询软装清单")
    @RequestMapping(value = "/querySoftListByOrderId", method = RequestMethod.POST)
    public HttpBaseResponse<SoftListResponse> querySoftListByOrderId(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        SoftListResponse response = orderService.querySoftListByOrderId(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "预签约", notes = "预签约接口，查询订单预算价格与合同模板")
    @RequestMapping(value = "/v532/queryPreSignInfo", method = RequestMethod.POST)
    public HttpBaseResponse<QueryPreSignResponse> queryPreSignInfo(@RequestBody QueryPreSignRequest request) {
        if (request == null || request.getOpType() == null ||
                (request.getOpType() == 2 && request.getDraftId() == null) ||
                (request.getOpType() == 1 && request.getDraftContent() == null)
        ) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        // 判断是否登陆
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);

        }
        QueryPreSignResponse response = orderService.queryPreSignInfo(request);
        return HttpBaseResponse.success(response);
    }


    @ApiOperation(value = "全品家订单详情", notes = "全品家订单详情")
    @PostMapping("/queryFamilyOrderDetail")
    public HttpBaseResponse<FamilyOrderDetailResponse> queryFamilyOrderDetail(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        FamilyOrderDetailResponse response = orderService.queryFamilyOrderDetail(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "查询是否可进行艾佳贷", notes = "查询是否可进行艾佳贷")
    @PostMapping("/queryLoanPermissionInfo")
    public HttpBaseResponse<LoanInfoResponse> queryLoanPermissionInfo(@RequestBody ProgramOrderDetailRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.success(new LoanInfoResponse(0,0));
        }
        return HttpBaseResponse.success( orderService.queryLoanPermissionInfo(request));
    }

    @ApiOperation(value = "删除草稿", notes = "删除草稿")
    @PostMapping("/deleteDraft")
    public HttpBaseResponse<Boolean> deleteDraft(@RequestBody DeleteDraftRequest request) {
        if (request == null || request.getOrderNum() == null || request.getDraftId() == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        return HttpBaseResponse.success( homeV5PageService.deleteDraft(request));
    }

}
