/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月11日
 * Description:OrderProxy.java
 */
package com.ihomefnt.o2o.service.proxy.order;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.art.dto.OrderBaseInfoDto;
import com.ihomefnt.o2o.intf.domain.art.dto.OrderDto;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.order.dto.*;
import com.ihomefnt.o2o.intf.domain.order.vo.CheckIfCanDeliveryConfirmVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HandleDepositMoneyRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HandleMoneyRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.OrderSimpleInfoRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.UpdateDeliverTimeRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HandleDepositMoneyResponseVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderSimpleInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderDetailDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.TransactionDetail;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderStateEnum;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AccountWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.FgwWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.OmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.oms.trade.order.dto.solution.SolutionOrderDto;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 */
@Service
public class OrderProxyImpl implements OrderProxy {

    @Resource
    private StrongSercviceCaller strongSercviceCaller;

    /**
     * 日志记录
     */
    private static final Logger LOG = LoggerFactory.getLogger(OrderProxyImpl.class);

    @Override
    public Integer createTripOrder(TripOrderCreateDto order) {
        if (order == null) {
            return null;
        }
        ResponseVo<Integer> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("oms-web.order.info.createTripOrder", order, 
                    new TypeReference<ResponseVo<Integer>>() {
            });
        } catch (Exception e) {
            return null;
        }

        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return (Integer) responseVo.getData();
        }
        return null;

    }

    @Override
    public boolean updateOrderState(Integer orderId, OrderStateEnum orderStatus) {

        if (orderId == null || orderStatus == null) {
            return false;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        params.put("state", orderStatus);
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("oms-web.order.info.updateOrderState", params, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }

        if (responseVo == null) {
            return false;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                return true;
            }
        }
        return false;

    }

    @Override
    public PageModel<OrderDtoVo> queryOrderInfo(OrderInfoSearchDto condition) {
        if (condition == null) {
            return null;
        }
        ResponseVo<PageModel<OrderDtoVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_ORDER_INFO, condition,
                    new TypeReference<ResponseVo<PageModel<OrderDtoVo>>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;

    }

    @Override
    public OrderDtoVo queryOrderDetailByOrderNum(String orderNum) {
        if (StringUtils.isBlank(orderNum)) {
            return null;
        }
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_ORDER_DETAIL_BY_ORDER_NUM, orderNum, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                OrderDtoVo order = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), OrderDtoVo.class);
                return order;
            }
        }
        return null;
    }


    @Override
    public ArtOrderVo queryArtOrderDetail(Integer orderId) {
        if (orderId == null) {
            return null;
        }
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("oms-web.order.info.queryArtOrderDetail", orderId, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                ArtOrderVo order = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), ArtOrderVo.class);
                return order;
            }
        }
        return null;
    }


    @Override
    public SoftOrderVo querySoftOrderDetail(Integer orderId) {
        if (orderId == null) {
            return null;
        }
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_SOFT_ORDER_DETAIL, orderId, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                SoftOrderVo order = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), SoftOrderVo.class);
                return order;
            }
        }
        return null;
    }


    @Override
    public FamilyOrderVo queryFamilyOrderDetail(Integer orderId) {
        if (orderId == null) {
            return null;
        }
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_FAMILY_ORDER_DETAIL, orderId, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                FamilyOrderVo order = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), FamilyOrderVo.class);
                return order;
            }
        }
        return null;
    }


    @Override
    public HardOrderVo queryHardOrderDetail(Integer orderId) {
        if (orderId == null) {
            return null;
        }
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_HARD_ORDER_DETAIL, orderId, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                HardOrderVo order = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), HardOrderVo.class);
                return order;
            }
        }
        return null;
    }


    @Override
    public TripOrderVo queryTripOrderDetail(Integer orderId) {
        if (orderId == null) {
            return null;
        }
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_TRIP_ORDER_DETAIL, orderId, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                TripOrderVo order = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), TripOrderVo.class);
                return order;
            }
        }
        return null;
    }

    @Override
    public AppAlipayOutput appAlipay(PayInput payInput) {

        if (payInput == null) {
            return null;
        }
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("fgw-web.pay.alipay.apppay", payInput, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                AppAlipayOutput order = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), AppAlipayOutput.class);
                return order;
            }
        }
        return null;

    }

    @Override
    public OrderPaidInfoDto queryOrderPaidInfo(String orderNum) {
        if (orderNum == null) {
            return null;
        }
        return strongSercviceCaller.post(FgwWebServiceNameConstants.QUERY_ORDER_PAID_INFO, orderNum, OrderPaidInfoDto.class);

    }


    @Override
    public List<PayFinishRecordVo> queryPayFinishedRecordList(String orderNum) {
        if (orderNum == null) {
            return null;
        }
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AccountWebServiceNameConstants.QUERY_PAY_FINISHED_RECORD_LIST, orderNum, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                List<PayFinishRecordVo> order = JsonUtils.json2list(JsonUtils.obj2json(responseVo.getData()), PayFinishRecordVo.class);
                return order;
            }
        }
        return null;

    }

    @Override
    public boolean cancelPay(String orderNum) {
        if (orderNum == null) {
            return false;
        }

        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("account-web.account.api.cancelPay", orderNum, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }
        if (responseVo == null) {
            return false;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {

                if ((Boolean) responseVo.getData()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public OrderDetailDto queryOrderSummaryInfo(Integer orderId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderNum", orderId);
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ORDER_SUMMARY_INFO, param, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }
        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                OrderDetailDto result = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), OrderDetailDto.class);

                //toc老带新用户手填信息回显处理
                if(result!=null&& StringUtil.isNotBlank(result.getBuildingInfo())){//手填信息不为空
                    String buildingInfo = result.getBuildingInfo();
                    JSONObject jasonObject = JSONObject.parseObject(buildingInfo);
                    Map map = (Map)jasonObject;
                    String type=String.valueOf(map.get("type"));
                    if("2".equals(type)) {//type=2表示手填数据
                        result.setCityName(String.valueOf( map.get("city")));
                        result.setHousingNum(String.valueOf( map.get("buildNo")));
                        result.setUnitNum(String.valueOf( map.get("unitNo")));
                        result.setRoomNum(String.valueOf( map.get("roomNo")));
                        result.setBuildingName(String.valueOf( map.get("community")));
                    }
                }
                return result;
            }
        }
        return null;
    }

    @Override
    public Boolean appointOfflinePay(Integer orderId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderNum", orderId);
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.OFFLINE_ORDER_PAYMENT, param, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }
        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                return (Boolean) responseVo.getData();
            }
        }
        return false;
    }

    @Override
    public Integer preConfirmSolution(Integer orderId) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.PRE_CONFIRM_LIST, orderId, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }
        if (responseVo == null) {
            return null;
        }

        return responseVo.getCode();
    }

    @Override
    public TransactionDetail queryPaymentRecordDetailById(Map<String, Long> params) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ORDER_TRANSACTION_DEATIL, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                TransactionDetail model = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), TransactionDetail.class);
                return model;
            }
        }
        return null;
    }

    @Override
    public SolutionOrderDto querySolutionOrderById(Integer orderId) {
        ResponseVo<SolutionOrderDto> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_SOLUTION_ORDER_BY_ID, orderId,
                    new TypeReference<ResponseVo<SolutionOrderDto>>() {
            });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return responseVo.getData();
    }

    @Override
    public OrderDtoVo queryOmsOrderDetail(Integer orderId) {
        ResponseVo<OrderDtoVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_ORDER_DETAIL, orderId,
                    new TypeReference<ResponseVo<OrderDtoVo>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return responseVo.getData();
    }

    @Override
    public OrderResponse queryOmsArtOrderDetail(Integer orderId) {
        ResponseVo<OrderResponse> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_OMS_ORDER_DETAIL, orderId,
                    new TypeReference<ResponseVo<OrderResponse>>() {
                    });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return responseVo.getData();
    }

    @Override
    public OrderDto queryOrderByOrderNum(String orderNum) {
        try {
            ResponseVo<OrderDto> responseVo = strongSercviceCaller.
                    post(OmsWebServiceNameConstants.QUERY_ORDER_BY_ORDER_NUM, orderNum, new TypeReference<ResponseVo<OrderDto>>() {
                    });
            return responseVo.getData();
        } catch (Exception e) {
        }
        return null;
    }


    @Override
    public CheckIfCanDeliveryConfirmVo checkIfCanDeliveryConfirm(Integer orderId) {
        ResponseVo<CheckIfCanDeliveryConfirmVo> response = null;
        try {
            response =  strongSercviceCaller.post
                (AladdinOrderServiceNameConstants.CHECK_IF_CAN_DELIVER_CONFIRM, orderId, new TypeReference<ResponseVo<CheckIfCanDeliveryConfirmVo>>() {
                });
        }catch (Exception e){
            return null;
        }
        if(response == null || !response.isSuccess() || response.getData() == null){
            return null;
        }
        return response.getData();
    }

    @Override
    public HandleDepositMoneyResponseVo handleDepositMoney(HandleDepositMoneyRequestVo request) {
        ResponseVo<HandleDepositMoneyResponseVo> response = null;
        try {
            response =  strongSercviceCaller.post
                    (AladdinOrderServiceNameConstants.HANDLE_DEPOSIT_MONEY, request, new TypeReference<ResponseVo<HandleDepositMoneyResponseVo>>() {
                    });
        }catch (Exception e){
            return null;
        }
        if(response == null || !response.isSuccess() || response.getData() == null){
            return null;
        }
        return response.getData();
    }

    @Override
    public HandleDepositMoneyResponseVo handleMoney(HandleMoneyRequestVo request) {
        ResponseVo<HandleDepositMoneyResponseVo> response = null;
        try {
            response =  strongSercviceCaller.post
                    (AladdinOrderServiceNameConstants.HANDLE_MONEY, request, new TypeReference<ResponseVo<HandleDepositMoneyResponseVo>>() {
                    });
        }catch (Exception e){
            return null;
        }
        if(response == null || !response.isSuccess() || response.getData() == null){
            return null;
        }
        return response.getData();
    }

    @Override
    public String updateDeliverTime(UpdateDeliverTimeRequestVo request) {
        ResponseVo<String> response = null;
        try {
            response =  strongSercviceCaller.post
                    (AladdinOrderServiceNameConstants.UPDATE_DELIVER_TIME, request, new TypeReference<ResponseVo<String>>() {
                    });
        }catch (Exception e){
            return null;
        }
        if(response == null){
            return null;
        }
        return response.getMsg();
    }

    @Override
    public String cancelOrder(Integer orderId) {
        ResponseVo<String> response = null;
        try {
            response =  strongSercviceCaller.post
                    (AladdinOrderServiceNameConstants.LOGICAL_DELETE_DEMO_ORDER, orderId, new TypeReference<ResponseVo<String>>() {
                    });
        }catch (Exception e){
            return null;
        }
        if(response == null){
            return null;
        }
        return response.getMsg();
    }

    @Override
    public List<OrderSimpleInfoResponseVo> querySimpleInfoByOrderNums(OrderSimpleInfoRequestVo request) {

        ResponseVo<List<OrderSimpleInfoResponseVo>> response = null;
        try {
            response =  strongSercviceCaller.post
                    (AladdinOrderServiceNameConstants.QUERY_SIMPLE_INFO_BY_ORDER_NUMS, request, new TypeReference<ResponseVo<List<OrderSimpleInfoResponseVo>>>() {
                    });
        }catch (Exception e){
            return new ArrayList<>();
        }
        if(response == null){
            return new ArrayList<>();
        }
        return response.getData();
    }

    @Override
    public List<OrderBaseInfoDto> queryOrderListByUserId(Integer userId) {
        ResponseVo<List<OrderBaseInfoDto>> response = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_ORDER_LIST_BY_USER_ID, userId,
                    new TypeReference<ResponseVo<List<OrderBaseInfoDto>>>() {
                    });
        if (!response.isSuccess()) {
            throw new BusinessException(MessageConstant.QUERY_USER_ORDER_FAIL);
        }
        return response.getData();
    }
}
